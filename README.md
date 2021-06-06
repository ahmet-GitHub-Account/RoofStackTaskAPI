RoofStacks API Automation Testing & Instructions
Prepared by:
Ahmet VURDEM

QA Test Automation Engineer

vurdemahmet@gmail.com

https://www.linkedin.com/in/ahmet-vurdem

- Build Tool: Maven

- Test Framework: TestNG

API Automation Testing

QA:
Generating automation framework for the given task.

System Requirements:

√ Java 1.8 + SDK

√ Maven

√ Rest Assured Library

√ Gson

****************************************************************

1) Clone the project

2) Reload maven dependencies from POM

****************************************************************

TASK (API)

1. Verify that the API starts with an empty store. At the beginning of a test case, there should be no books stored on the server.
2. Verify that title and author are required fields. PUT on /api/books/ should return an error Field '' is required.
3. Verify that title and author cannot be empty. PUT on /api/books/ should return an error Field '' cannot be empty.
4. Verify that the id field is read−only. You shouldn't be able to send it in the PUT request to /api/books/.
5. Verify that you can create a new book via PUT.The book should be returned in the response.GET on /api/books// should return the same book.
6. Verify that you cannot create a duplicate book. PUT on /api/books/ should return an error: Another book with similar title and author already exists.

Sonuç :
Sıralı test adımları TestNG, RestAssuredLibrary ile yazıldı, uygulandı. 
GET, POST, PUT, PATCH, DELETE http request type ile gerekli Java kodları yazıldı.
runner.xml ile ilgili sınıflar, package, method test suitleri oluşturuldu.  


2021 June®
