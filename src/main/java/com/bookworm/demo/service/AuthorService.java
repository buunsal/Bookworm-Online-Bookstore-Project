package com.bookworm.demo.service;

import com.bookworm.demo.exception.ResourceNotFoundException;
import com.bookworm.demo.model.Author;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AuthorService {

    List<Author> getAuthors();

    void saveAuthor(Author author);

    Author getAuthor(int Id) throws ResourceNotFoundException;

    void deleteAuthor(int Id) throws ResourceNotFoundException;

}
