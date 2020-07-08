package com.bookworm.demo.service;

import com.bookworm.demo.exception.ResourceNotFoundException;
import com.bookworm.demo.model.Publisher;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PublisherService {

    List<Publisher> getPublishers();

    void savePublisher(Publisher publisher);

    Publisher getPublisher(int Id) throws ResourceNotFoundException;

    void deletePublisher(int Id) throws ResourceNotFoundException;

}
