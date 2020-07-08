package com.bookworm.demo.service;

import com.bookworm.demo.exception.ResourceNotFoundException;
import com.bookworm.demo.model.Publisher;
import com.bookworm.demo.repository.PublisherRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.ejb.EJB;
import java.util.List;

@Service
public class PublisherServiceImpl implements PublisherService {

    @EJB
    private PublisherRepository publisherRepository;

    @Override
    @Transactional
    public List<Publisher> getPublishers() {
        return publisherRepository.findAll();
    }

    @Override
    @Transactional
    public void savePublisher(Publisher publisher) {
        publisherRepository.save(publisher);
    }

    @Override
    @Transactional
    public Publisher getPublisher(int Id) throws ResourceNotFoundException {
        return publisherRepository.findById(Id).orElseThrow(
                () -> new ResourceNotFoundException(Id));
    }

    @Override
    @Transactional
    public void deletePublisher(int Id) {
        publisherRepository.deleteById(Id);
    }
}
