package org.example.web.controllers;

import org.example.app.exceptions.BookShelfLoginException;
import org.example.app.exceptions.BookShelfNullUploadFileException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;

@ControllerAdvice
public class ErrorController {
    private static final String ERRORS_404 = "errors/404";

    @GetMapping("/404")
    public String notFoundError() {
        return ERRORS_404;
    }

    @ExceptionHandler(BookShelfLoginException.class)
    public String handleError(Model model, BookShelfLoginException exception) {
        model.addAttribute("errorMessage", exception.getMessage());
        return ERRORS_404;
    }

    @ExceptionHandler(BookShelfNullUploadFileException.class)
    public String handleError(Model model, BookShelfNullUploadFileException exception) {
        model.addAttribute("errorMessage", exception.getMessage());
        return ERRORS_404;
    }
}
