package com.bookworm.demo.controller;

import com.bookworm.demo.exception.ResourceNotFoundException;
import com.bookworm.demo.model.Book;
import com.bookworm.demo.model.Publisher;
import com.bookworm.demo.service.BookServiceImpl;
import com.bookworm.demo.service.PublisherServiceImpl;
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
@Component(value = "publisherPageController")
public class PublisherPageController implements Serializable {

    private static final long serialVersionUID = 4L;

    private Publisher publisher;

    @EJB
    private PublisherServiceImpl publisherService;

    @EJB
    private BookServiceImpl bookService;

    private List<Book> books;

    @PostConstruct
    public void init() throws ResourceNotFoundException {
        HttpServletRequest httpRequest = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        publisher = publisherService.getPublisher(Integer.parseInt(httpRequest.getParameter("id")));
        books = bookService.getPublisherBooks(publisher);
    }

}