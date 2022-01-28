package org.example.web.controllers;

import javax.validation.Valid;
import org.apache.log4j.Logger;
import org.example.app.services.BookService;
import org.example.web.dto.Book;
import org.example.web.dto.BookIdToRemove;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(value = "books")
@Scope("singleton")
public class BookShelfController {

    private final Logger logger = Logger.getLogger(BookShelfController.class);
    private final BookService bookService;
    private static final String BOOK_SHELF_PAGE = "book_shelf";
    private static final String REDIRECT_BOOK_SHELF_PAGE = "redirect:/books/shelf";
    private static final String BOOK_LIST = "bookList";

    @Autowired
    public BookShelfController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/shelf")
    public String books(Model model) {
        logger.info(this.toString());
        model.addAttribute("book", new Book());
        model.addAttribute("bookIdToRemove", new BookIdToRemove());
        model.addAttribute(BOOK_LIST, bookService.getAllBooks());
        return BOOK_SHELF_PAGE;
    }

    @PostMapping("/save")
    public String saveBook(@Valid Book book, BindingResult bindingResult, Model model) {
        if(bindingResult.hasErrors()) {
            model.addAttribute("book", book);
            model.addAttribute("bookIdToRemove", new BookIdToRemove());
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
            model.addAttribute(BOOK_LIST, bookService.getAllBooks());
            return BOOK_SHELF_PAGE;
        } else {
            bookService.removeBookById(bookIdToRemove.getId());
            return REDIRECT_BOOK_SHELF_PAGE;
        }
    }

    @PostMapping("/removeByRegex")
    public String removeBookByRegex(@RequestParam(value="queryRegex") String regex) {
        bookService.removeBookByRegex(regex);
        return REDIRECT_BOOK_SHELF_PAGE;
    }
}
