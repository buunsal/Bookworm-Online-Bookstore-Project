package com.bookworm.demo.controller;

import com.bookworm.demo.model.Book;
import com.bookworm.demo.model.Review;
import com.bookworm.demo.service.BookServiceImpl;
import com.bookworm.demo.service.ReviewServiceImpl;
import lombok.Data;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;

@Data
@ViewScoped
@Component(value = "reviewController")
public class ReviewController implements Serializable {

    private static final long serialVersionUID = 4L;

    private Book book;

    private Review review;

    private float ratingBook;

    private int roundedRating;

    @EJB
    private BookServiceImpl bookService;

    @EJB
    private ReviewServiceImpl reviewService;

    @EJB
    private SessionController sessionController;

    @PostConstruct
    public void init() {
        HttpServletRequest httpRequest = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        book = bookService.getBook(httpRequest.getParameter("isbn"));
        float total = 0;
        for (Review r : book.getReviews()) {
            total += r.getRating();
        }

        ratingBook = total / book.getReviews().size();
        roundedRating = (int) ratingBook;

        if (sessionController.getIsLogged()) {
            if (reviewService.getReviewByEmailAndBook(sessionController.getUser().getEmail(), book) != null) {
                review = reviewService.getReviewByEmailAndBook(sessionController.getUser().getEmail(), book);
            } else {
                review = new Review();
            }
        }
    }

    public void saveReview() {
        review.setReviewerName(sessionController.getUser().getName() + " " + sessionController.getUser().getLastName());
        review.setReviewerEmail(sessionController.getCurrentUser());
        reviewService.saveReview(review);
        book.getReviews().add(review);
        bookService.saveBook(book);
    }

}
