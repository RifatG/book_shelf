package org.example.web.controllers;

import javax.validation.Valid;
import org.apache.log4j.Logger;
import org.example.app.exceptions.BookShelfNullUploadFileException;
import org.example.app.services.BookService;
import org.example.web.dto.Book;
import org.example.web.dto.BookIdToRemove;
import org.example.web.dto.BookRegexToRemove;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

@Controller
@RequestMapping(value = "books")
@Scope("singleton")
public class BookShelfController {

    private final Logger logger = Logger.getLogger(BookShelfController.class);
    private final BookService bookService;
    private static final String BOOK_SHELF_PAGE = "book_shelf";
    private static final String REDIRECT_BOOK_SHELF_PAGE = "redirect:/books/shelf";
    private static final String BOOK_LIST = "bookList";
    private static final String BOOK_ID_TO_REMOVE = "bookIdToRemove";
    private static final String BOOK_REGEX_TO_REMOVE = "bookRegexToRemove";

    @Autowired
    public BookShelfController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/shelf")
    public String books(Model model) {
        logger.info(this.toString());
        model.addAttribute("book", new Book());
        model.addAttribute(BOOK_ID_TO_REMOVE, new BookIdToRemove());
        model.addAttribute(BOOK_REGEX_TO_REMOVE, new BookRegexToRemove());
        model.addAttribute(BOOK_LIST, bookService.getAllBooks());
        return BOOK_SHELF_PAGE;
    }

    @PostMapping("/save")
    public String saveBook(@Valid Book book, BindingResult bindingResult, Model model) {
        if(bindingResult.hasErrors()) {
            model.addAttribute("book", book);
            model.addAttribute(BOOK_ID_TO_REMOVE, new BookIdToRemove());
            model.addAttribute(BOOK_REGEX_TO_REMOVE, new BookRegexToRemove());
            model.addAttribute(BOOK_LIST, bookService.getAllBooks());
            return BOOK_SHELF_PAGE;
        } else {
            bookService.saveBook(book);
            logger.info("current reposiroty contents: " + bookService.getAllBooks().size());
            return REDIRECT_BOOK_SHELF_PAGE;
        }
    }

    @PostMapping("/remove")
    public String removeBook(@Valid BookIdToRemove bookIdToRemove, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("book", new Book());
            model.addAttribute(BOOK_REGEX_TO_REMOVE, new BookRegexToRemove());
            model.addAttribute(BOOK_LIST, bookService.getAllBooks());
            logger.info("remove book failed due to field validation");
            return BOOK_SHELF_PAGE;
        } else {
            bookService.removeBookById(bookIdToRemove.getId());
            return REDIRECT_BOOK_SHELF_PAGE;
        }
    }

    @PostMapping("/removeByRegex")
    public String removeBookByRegex(@Valid BookRegexToRemove regex, BindingResult bindingResult, Model model) {
        model.addAttribute("book", new Book());
        model.addAttribute(BOOK_ID_TO_REMOVE, new BookIdToRemove());
        model.addAttribute(BOOK_LIST, bookService.getAllBooks());
        if (bindingResult.hasErrors()) {
            logger.info("remove book failed due to field validation");
            return BOOK_SHELF_PAGE;
        } else {
            boolean regexIsMatched = bookService.removeBookByRegex(regex.getRegex());
            if(regexIsMatched) return REDIRECT_BOOK_SHELF_PAGE;
            else {
                model.addAttribute("regexIsMatched", true);
                return BOOK_SHELF_PAGE;
            }
        }
    }

    @PostMapping("/uploadFile")
    public String uploadFile(@RequestParam("file") MultipartFile file) throws IOException, BookShelfNullUploadFileException{
        if(file.isEmpty()) throw new BookShelfNullUploadFileException("No chosen file");
        String name = file.getOriginalFilename();
        byte[] bytes = file.getBytes();

        //create directory
        String rootPath = System.getProperty("catalina.home");
        File dir = new File(rootPath + File.separator + "external_uploads");
        if (!dir.exists()) dir.mkdirs();

        //create file
        File serverFile = new File(dir.getAbsolutePath() + File.separator + name);
        try (BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile))) {
            stream.write(bytes);
        }

        logger.info("new file saved at: " + serverFile.getAbsolutePath());

        return REDIRECT_BOOK_SHELF_PAGE;
    }
}
