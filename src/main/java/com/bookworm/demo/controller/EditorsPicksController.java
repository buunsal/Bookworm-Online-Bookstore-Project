package com.bookworm.demo.controller;

import com.bookworm.demo.model.Book;
import com.bookworm.demo.service.BookService;
import lombok.Data;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import java.io.Serializable;
import java.util.List;

@Data
@ViewScoped
@Component(value = "editorsPicksController")
public class EditorsPicksController implements Serializable {

    private static final long serialVersionUID = 4L;

    private List<Book> books;

    private Book selectedBook;

    @EJB
    private BookService bookService;

    @PostConstruct
    public void init() {

        books = bookService.getEditorsPicks();

    }

}