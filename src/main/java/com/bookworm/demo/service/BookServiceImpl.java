package com.bookworm.demo.service;

import com.bookworm.demo.model.Author;
import com.bookworm.demo.model.Book;
import com.bookworm.demo.model.Category;
import com.bookworm.demo.model.Publisher;
import com.bookworm.demo.repository.BookRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.ejb.EJB;
import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    @EJB
    private BookRepository bookRepository;

    @Override
    public Book getBook(String ISBN) {
        return bookRepository.findBookByISBN(ISBN);
    }

    @Override
    public List<Book> getBooks() {
        return bookRepository.findAll();
    }

    @Override
    @Transactional
    public void saveBook(Book Book) {
        bookRepository.save(Book);
    }

    @Override
    public List<Book> getBestSellers() {
        return bookRepository.findBooksByBestsellerTrue();
    }

    @Override
    public List<Book> getAuthorBooks(Author author) {
        return bookRepository.findBooksByAuthors(author);
    }

    @Override
    public List<Book> getPublisherBooks(Publisher publisher) {
        return bookRepository.findBooksByPublishers(publisher);
    }

    @Override
    public List<Book> getCategoryBooks(Category category) {
        return bookRepository.findBooksByCategories(category);
    }

    @Override
    @Transactional
    public void deleteBook(String ISBN) {
        bookRepository.deleteById(ISBN);
    }

    @Override
    @Transactional
    public List<Book> getEditorsPicks() {
        return bookRepository.findBooksByEditorspicksTrue();

    }

}
