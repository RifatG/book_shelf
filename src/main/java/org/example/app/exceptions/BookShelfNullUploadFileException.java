package org.example.app.exceptions;

public class BookShelfNullUploadFileException extends Exception {

    private final String message;

    public BookShelfNullUploadFileException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
