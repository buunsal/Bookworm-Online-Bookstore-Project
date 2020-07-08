package com.bookworm.demo.service;

import com.bookworm.demo.exception.ResourceNotFoundException;
import com.bookworm.demo.model.Book;
import com.bookworm.demo.model.Review;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ReviewService {

    List<Review> getReviews();

    void saveReview(Review review);

    Review getReview(int id) throws ResourceNotFoundException;

    void deleteReview(int id) throws ResourceNotFoundException;

    Review getReviewByEmailAndBook(String email, Book book) throws ResourceNotFoundException;

}
