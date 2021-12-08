package org.example.app.services;

import org.apache.log4j.Logger;
import org.example.web.dto.Book;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

@Repository
public class BookRepository<T> implements ProjectRepository<Book> {

    private final Logger logger = Logger.getLogger(BookRepository.class);
    private final List<Book> repo = new ArrayList<>();

    @Override
    public List<Book> retrieveAll() {
        return new ArrayList<>(repo);
    }

    @Override
    public boolean store(Book book) {
        if(!book.getAuthor().isEmpty()&&!book.getTitle().isEmpty()&&!book.getSize().isEmpty()) {
            book.setId(book.hashCode());
            logger.info("store new book: " + book);
            return repo.add(book);
        }
        logger.info("failed to store new book due to empty fields");
        return false;
    }

    @Override
    public boolean removeItemById(Integer bookIdToRemove) {
        for (Book book :
                retrieveAll()) {
            if (book.getId().equals(bookIdToRemove)) {
                logger.info("remove book completed: " + book);
                return repo.remove(book);
            }
        }
        logger.info("remove book failed. There is no such id");
        return false;
    }

    @Override
    public boolean removeItemByRegex(String regexToRemove) {
        boolean result = false;
        Pattern pattern = Pattern.compile(regexToRemove);
        for (Book book :
                retrieveAll()) {
            if(pattern.matcher(book.getAuthor()).matches()||pattern.matcher(book.getTitle()).matches()||pattern.matcher(book.getSize()).matches()) {
                repo.remove(book);
                logger.info("remove book completed: " + book);
                result = true;
            }
        }
        if(!result) logger.info("remove book failed. There is no book correspond such regular expression");
        return result;
    }

}
