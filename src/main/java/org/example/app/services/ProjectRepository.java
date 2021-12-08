package org.example.app.services;

import org.example.web.dto.Book;

import java.util.List;

public interface ProjectRepository<T> {
    List<T> retrieveAll();

    boolean store(T book);

    boolean removeItemById(Integer bookIdToRemove);

    boolean removeItemByRegex(String regexToRemove);
}
