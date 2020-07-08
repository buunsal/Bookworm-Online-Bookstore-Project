package com.bookworm.demo.service;

import com.bookworm.demo.exception.ResourceNotFoundException;
import com.bookworm.demo.model.Book;
import com.bookworm.demo.model.Review;
import com.bookworm.demo.repository.ReviewRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.ejb.EJB;
import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {

    @EJB
    private ReviewRepository reviewRepository;

    @Override
    @Transactional
    public List<Review> getReviews() {
        return reviewRepository.findAll();
    }

    @Override
    @Transactional
    public void saveReview(Review review) {
        reviewRepository.save(review);
    }

    @Override
    @Transactional
    public Review getReview(int id) throws ResourceNotFoundException {
        return reviewRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(id));
    }

    @Override
    @Transactional
    public void deleteReview(int id) {
        reviewRepository.deleteById(id);
    }

    @Override
    @Transactional
    public Review getReviewByEmailAndBook(String email, Book book) {
        return reviewRepository.findReviewByReviewerEmailAndBooksEquals(email, book);
    }

}
