package com.roofStacks.api_test;

import com.roofStacks.utilities.TestBase;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.*;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.core.IsNull.nullValue;
import static org.testng.Assert.*;
import static org.testng.Assert.assertEquals;

public class RoofStacksApiTests extends TestBase {

    Response response;


    /**
     * 1. Verify that the API starts with an empty store.
     * • At the beginning of a test case, there should be no books stored on the server.
     */
    @Test(priority = 0)
    public void NoBookInStore() {

        /**
         * we can use here the HamcrestMatchers
         */

        given()
                .accept(ContentType.JSON)
                .when()
                .get(APITestCase.API_ROOT)
                .then()
                .statusCode(200)
                .contentType(equalTo("application/json"))
                .and()
                .assertThat()
                .body("id", nullValue());

    }

    /**
     * 2. Verify that title and author are required fields.
     * • PUT on /api/books/ should return an error Field '<field_name>' is required.
     */

    @Test(priority = 1)
    public void RequiredFieldTest() {

        PojoClassForBooks noTitleNoAuthor = new PojoClassForBooks();

        given().log().all()
                .accept(ContentType.JSON)
                .and().contentType(ContentType.JSON)
                .body(noTitleNoAuthor)
                .when()
                .put(APITestCase.API_ROOT)
                .then()
                .statusCode(400)
                .contentType(equalTo("application/json"))
                .and()
                .assertThat()
                .body("error", contains("is required"));

    }

    /**
     * 3. Verify that title and author cannot be empty.
     * • PUT on /api/books/ should return an error Field '<field_name>' cannot be empty.
     */

    @Test
    public void NotEmptyFieldTest() {

        /** Here I have used the map intentionally
         * I preferred here the HashMap for the reason is this :
         * HashMap contains one null key and many null values.
         * And does not maintain any soring order
         */
        Map<String, Object> putMap = new HashMap<>();
        putMap.put("title", "");
        putMap.put("author", "");

        response = given().accept(ContentType.JSON)
                .and().body(putMap)
                .when().put(APITestCase.API_ROOT);

        assertEquals(response.statusCode(), 204);

        String responseBody = response.body().asString();
        assertTrue(responseBody.contains("cannot be empty"));

    }

    /**
     * 4. Verify that the id field is read−only.
     * • You shouldn't be able to send it in the PUT request to /api/books/.
     */

    @Test
    public void ReadOnlyIdTest() {
        Map<String, Object> putMap = new HashMap<>();
        putMap.put("id", "1");
        putMap.put("title", "Here the title of the book");
        putMap.put("author", "Here the author's name");

        // Bad request error code = 400
        given().accept(ContentType.JSON)
                .and().body(putMap)
                .when().put(APITestCase.API_ROOT)
                .then().assertThat().statusCode(400);


    }

    /**
     * 5. Verify that you can create a new book via PUT.
     * • The book should be returned in the response.
     * • GET on /api/books/<book_id>/ should return the same book
     */

    @Test
    public void CreateNewBookTest() {

        PojoClassForBooks newBook = new PojoClassForBooks();

        newBook.setAuthor("Here is the author name");
        newBook.setTitle("Here is the title name");

        given().log().all()
                .accept(ContentType.JSON)
                .and().contentType(ContentType.JSON)
                .body(newBook)
                .when()
                .put("/api/books/{id}")
                .then()
                .statusCode(201)
                .contentType(equalTo("application/json"))
                .and()
                .assertThat()
                .body("author", equalTo("Here is the author name"),
                        "title",equalTo("Here is the title name"));

        // verify the book id with GET request
        // verify the title and the author with Hamcrest Matchers

        given().log().all()
                .accept(ContentType.JSON)
                .and().contentType(ContentType.JSON)
                .body(newBook)
                .when()
                .get("/api/books/{id}")
                .then()
                .statusCode(200)
                .contentType(equalTo("application/json"))
                .and()
                .assertThat()
                .body("author", equalTo("Here is the author name"),
                        "title",equalTo("Here is the title name"));

    }

    /**
     * 6. Verify that you cannot create a duplicate book
     * • First request should response success code
     * • Second request should response error code
     */

    @Test
    public void DuplicateBookTest() {
        PojoClassForBooks duplicatedBook = new PojoClassForBooks();
        duplicatedBook.setAuthor("Here is the author name");
        duplicatedBook.setTitle("Here is the title name");

        response = given().accept(ContentType.JSON)
                .and().body(duplicatedBook)
                .when().put("/api/books/{id}");

        // verify status code 200 in first request
        assertEquals(response.statusCode(), 200);

        given().accept(ContentType.JSON)
                .and().pathParam("id",duplicatedBook.getId())
                .and().body(duplicatedBook)
                .when()
                .put("/api/books/{id}")
                .then().statusCode(404)
                .and()
                .body("Error",is("Another book with similar title and author already exists."));


    }
}
