package com.bookworm.demo.service;

import com.bookworm.demo.exception.ResourceNotFoundException;
import com.bookworm.demo.model.Category;
import com.bookworm.demo.repository.CategoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.ejb.EJB;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @EJB
    private CategoryRepository categoryRepository;

    @Override
    @Transactional
    public List<Category> getCategories() {
        return categoryRepository.findAll();
    }

    @Override
    @Transactional
    public void saveCategory(Category category) {
        categoryRepository.save(category);
    }

    @Override
    @Transactional
    public Category getCategory(int Id) throws ResourceNotFoundException {
        return categoryRepository.findById(Id).orElseThrow(
                () -> new ResourceNotFoundException(Id));
    }

    @Override
    @Transactional
    public void deleteCategory(int Id) {
        categoryRepository.deleteById(Id);
    }

}
