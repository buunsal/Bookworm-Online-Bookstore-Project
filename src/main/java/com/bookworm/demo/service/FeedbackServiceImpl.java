package com.bookworm.demo.service;

import com.bookworm.demo.exception.ResourceNotFoundException;
import com.bookworm.demo.model.Feedback;
import com.bookworm.demo.repository.FeedbackRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.ejb.EJB;
import java.util.List;

@Service
public class FeedbackServiceImpl implements FeedbackService {

    @EJB
    private FeedbackRepository feedbackRepository;

    @Override
    @Transactional
    public List<Feedback> getFeedbacks() {
        return feedbackRepository.findAll();
    }

    @Override
    @Transactional
    public void saveFeedback(Feedback feedback) {
        feedbackRepository.save(feedback);
    }

    @Override
    @Transactional
    public Feedback getFeedback(int id) throws ResourceNotFoundException {
        return feedbackRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(id));
    }

    @Override
    @Transactional
    public void deleteFeedback(int id) {
        feedbackRepository.deleteById(id);
    }
}
