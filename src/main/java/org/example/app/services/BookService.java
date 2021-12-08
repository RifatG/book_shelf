package org.example.app.services;

import org.example.web.dto.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    private final ProjectRepository<Book> bookRepo;

    @Autowired
    public BookService(BookRepository<Book> bookRepo) {
        this.bookRepo = bookRepo;
    }

    public boolean saveBook(Book book) {
        return bookRepo.store(book);
    }

    public List<Book> getAllBooks() {
        return bookRepo.retrieveAll();
    }

    public boolean removeBookById(Integer bookIdToRemove) {
        return bookRepo.removeItemById(bookIdToRemove);
    }

    public boolean removeBookByRegex(String regexToRemove) {
        return bookRepo.removeItemByRegex(regexToRemove);
    }
}
