package com.bookworm.demo.controller;

import com.bookworm.demo.model.Book;
import com.bookworm.demo.model.Feedback;
import com.bookworm.demo.service.BookService;
import com.bookworm.demo.service.FeedbackService;
import lombok.Data;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import java.io.Serializable;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Data
@ViewScoped
@Component(value = "adminController")
public class AdminController implements Serializable {

    private static final long serialVersionUID = 4L;

    private List<Feedback> feedbacks;
    private List<Book> books;

    @EJB
    private FeedbackService feedbackService;
    @EJB
    private BookService bookService;

    @PostConstruct
    public void init() {
        this.feedbacks = feedbackService.getFeedbacks();
        feedbacks.sort(Comparator.comparing(Feedback::getDate).reversed());
        this.books = bookService.getBooks().stream().filter(b -> b.getStock() <= 5).collect(Collectors.toList());
    }

}
