package com.bookworm.demo.controller;

import com.bookworm.demo.exception.ResourceNotFoundException;
import com.bookworm.demo.model.Author;
import com.bookworm.demo.model.Book;
import com.bookworm.demo.service.AuthorServiceImpl;
import com.bookworm.demo.service.BookServiceImpl;
import lombok.Data;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.List;

@Data
@ViewScoped
@Component(value = "authorPageController")
public class AuthorPageController implements Serializable {

    private static final long serialVersionUID = 4L;

    private Author author;

    @EJB
    private AuthorServiceImpl authorService;

    @EJB
    private BookServiceImpl bookService;

    private List<Book> authorBooks;

    @PostConstruct
    public void init() throws ResourceNotFoundException {
        HttpServletRequest httpRequest = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        author = authorService.getAuthor(Integer.parseInt(httpRequest.getParameter("id")));
        authorBooks = bookService.getAuthorBooks(author);
    }


}