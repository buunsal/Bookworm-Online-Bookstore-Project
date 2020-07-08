package com.bookworm.demo.controller;

import com.bookworm.demo.model.Author;
import com.bookworm.demo.model.Book;
import com.bookworm.demo.model.Category;
import com.bookworm.demo.service.AuthorService;
import com.bookworm.demo.service.BookService;
import com.bookworm.demo.service.CategoryService;
import lombok.Data;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.ApplicationScope;

import javax.ejb.EJB;
import java.io.Serializable;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Data
@ApplicationScope
@Component(value = "databaseController")
public class DatabaseController implements Serializable {

    private static final long serialVersionUID = 4L;

    private List<Book> books;
    private List<Book> bestSellingBooks;
    private List<Book> editorPicks;
    private List<Book> newReleases;
    private List<Category> categories;
    private List<Category> topCategories;
    private List<Category> moreCategories1;
    private List<Category> moreCategories2;
    private List<Author> topAuthors;

    @EJB
    private BookService bookService;
    @EJB
    private CategoryService categoryService;
    @EJB
    private AuthorService authorService;

    public void init() {
        //Initialize category menu
        categories = categoryService.getCategories();
        categories.sort(Comparator.comparing(Category::getName));
        topCategories = categories.stream().filter(Category::isTopCategory).collect(Collectors.toList());
        categories.removeAll(topCategories);
        moreCategories1 = categories.subList(0, 12);
        moreCategories2 = categories.subList(12, categories.size());
        topAuthors = authorService.getAuthors().stream().filter(Author::isTopAuthor).collect(Collectors.toList());
        topAuthors.sort(Comparator.comparing(Author::getName));

        //Initialize books
        books = bookService.getBooks();
        bestSellingBooks = books.stream().filter(Book::isBestseller).collect(Collectors.toList());
        editorPicks = books.stream().filter(Book::isEditorspicks).collect(Collectors.toList());
        newReleases = books;
        newReleases.sort(Comparator.comparing(Book::getYear).reversed());

    }

}
