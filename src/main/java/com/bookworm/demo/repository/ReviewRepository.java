package com.bookworm.demo.repository;

import com.bookworm.demo.model.Book;
import com.bookworm.demo.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ReviewRepository extends JpaRepository<Review, Integer> {


    Review findReviewByReviewerEmailAndBooksEquals(String email, Book book);

}
