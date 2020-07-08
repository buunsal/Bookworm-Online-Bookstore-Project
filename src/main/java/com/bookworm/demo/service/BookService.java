package com.bookworm.demo.service;

import com.bookworm.demo.exception.ResourceNotFoundException;
import com.bookworm.demo.model.Author;
import com.bookworm.demo.model.Book;
import com.bookworm.demo.model.Category;
import com.bookworm.demo.model.Publisher;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BookService {

    List<Book> getBooks();

    List<Book> getBestSellers();

    List<Book> getAuthorBooks(Author author);

    List<Book> getPublisherBooks(Publisher publisher);

    void saveBook(Book book);

    Book getBook(String ISBN) throws ResourceNotFoundException;

    void deleteBook(String ISBN) throws ResourceNotFoundException;

    List<Book> getEditorsPicks();

    List<Book> getCategoryBooks(Category category);


}
