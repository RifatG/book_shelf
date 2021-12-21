package org.example.app.services;

import org.apache.log4j.Logger;
import org.example.web.dto.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    private final ProjectRepository<Book> bookRepo;
    private final Logger logger = Logger.getLogger(BookService.class);

    @Autowired
    public BookService(BookRepository<Book> bookRepo) {
        this.bookRepo = bookRepo;
    }

    public boolean saveBook(Book book) {
        if(!book.getAuthor().isEmpty()&&!book.getTitle().isEmpty()&&!book.getSize().isEmpty()) {
            return bookRepo.store(book);
        }
        logger.info("failed to store new book due to empty fields");
        return false;
    }

    public List<Book> getAllBooks() {
        return bookRepo.retrieveAll();
    }

    public boolean removeBookById(String bookIdToRemove) {
        return bookRepo.removeItemById(bookIdToRemove);
    }

    public boolean removeBookByRegex(String regexToRemove) {
        return bookRepo.removeItemByRegex(regexToRemove);
    }
}
