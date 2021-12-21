package org.example.web.dto;

public class Book {

    private String id;
    private String author;
    private String title;
    private String size;

    public Book(String id, String author, String title, String size) {
        this.id = id;
        this.author = author;
        this.title = title;
        this.size = size;
    }

    public Book() {
    }

    public String getAuthor() {
        return author;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", author='" + author + '\'' +
                ", title='" + title + '\'' +
                ", size='" + size + '\'' +
                '}';
    }
}
