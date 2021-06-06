package com.roofStacks.api_test;

public class PojoClassForBooks {

    private int id;
    private String author;
    private String title;

    /**
     * for only read we create the only getter method, no setter method.
     * <p>
     * public void setId(int id) {
     * this.id = id;
     * }
     */

    public int getId() {
        return id;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getAuthor() {
        return author;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public String toString() {
        return "PojoClass_ForBooks{" +
                "author='" + author + '\'' +
                ", title='" + title + '\'' +
                ", id=" + id +
                '}';
    }
}
