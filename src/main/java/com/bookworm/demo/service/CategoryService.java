package com.bookworm.demo.service;

import com.bookworm.demo.exception.ResourceNotFoundException;
import com.bookworm.demo.model.Category;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CategoryService {

    List<Category> getCategories();

    void saveCategory(Category category);

    Category getCategory(int Id) throws ResourceNotFoundException;

    void deleteCategory(int Id) throws ResourceNotFoundException;

}
