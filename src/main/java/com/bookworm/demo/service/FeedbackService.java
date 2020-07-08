package com.bookworm.demo.service;

import com.bookworm.demo.exception.ResourceNotFoundException;
import com.bookworm.demo.model.Feedback;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface FeedbackService {

    List<Feedback> getFeedbacks();

    void saveFeedback(Feedback feedback);

    Feedback getFeedback(int id) throws ResourceNotFoundException;

    void deleteFeedback(int id) throws ResourceNotFoundException;

}
