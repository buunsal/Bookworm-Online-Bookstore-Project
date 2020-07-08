package com.bookworm.demo.controller;

import com.bookworm.demo.exception.ResourceNotFoundException;
import com.bookworm.demo.model.Category;
import com.bookworm.demo.service.CategoryService;
import lombok.Data;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import java.io.Serializable;
import java.util.List;
import java.util.Locale;

@Data
@ViewScoped
@Component(value = "categoryController")
public class CategoryController implements Serializable {

    private static final long serialVersionUID = 4L;

    private List<Category> categories;

    private Category category = new Category();

    private Category selectedCategory = new Category();

    private List<Category> filteredCategories;

    @EJB
    private CategoryService categoryService;

    @PostConstruct
    public void init() {
        this.categories = categoryService.getCategories();
    }

    public void add() {
        categoryService.saveCategory(category);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Create Successful", "Created Category:" + category.getName()));
        this.categories = categoryService.getCategories();
        this.category = new Category();
    }

    public void delete(Category category) throws ResourceNotFoundException {
        categoryService.deleteCategory(category.getId());
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Delete Successful", "Deleted Category:" + category.getName()));
        categories.remove(category);
    }

    public void update() {
        categoryService.saveCategory(selectedCategory);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Update Successful", "Updated Category:" + selectedCategory.getName()));
        this.selectedCategory = new Category();
    }

    public boolean globalFilterFunction(Object value, Object filter, Locale locale) {
        String filterText = (filter == null) ? null : filter.toString().trim().toLowerCase();
        if (filterText == null || filterText.equals("")) {
            return true;
        }

        Category category = (Category) value;
        return category.getName().toLowerCase().contains(filterText)
                || category.getDescription().toLowerCase().contains(filterText);
    }
}
