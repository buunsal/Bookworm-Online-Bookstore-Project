package com.bookworm.demo.service;

import com.bookworm.demo.exception.ResourceNotFoundException;
import com.bookworm.demo.model.Author;
import com.bookworm.demo.repository.AuthorRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.ejb.EJB;
import java.util.List;

@Service
public class AuthorServiceImpl implements AuthorService {

    @EJB
    private AuthorRepository authorRepository;

    @Override
    @Transactional
    public List<Author> getAuthors() {
        return authorRepository.findAll();
    }

    @Override
    @Transactional
    public void saveAuthor(Author Author) {
        authorRepository.save(Author);
    }

    @Override
    @Transactional
    public Author getAuthor(int Id) throws ResourceNotFoundException {
        return authorRepository.findById(Id).orElseThrow(
                () -> new ResourceNotFoundException(Id));
    }

    @Override
    @Transactional
    public void deleteAuthor(int Id) {
        authorRepository.deleteById(Id);
    }
}
