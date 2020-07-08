package com.bookworm.demo.controller;

import com.bookworm.demo.exception.ResourceNotFoundException;
import com.bookworm.demo.model.Book;
import com.bookworm.demo.model.Category;
import com.bookworm.demo.service.BookServiceImpl;
import com.bookworm.demo.service.CategoryService;
import lombok.Data;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.List;

@Data
@ViewScoped
@Component(value = "categoryPageController")
public class CategoryPageController implements Serializable {

    private static final long serialVersionUID = 4L;

    private List<Category> categories;

    private Category category;

    @EJB
    private BookServiceImpl bookService;

    @EJB
    private CategoryService categoryService;

    private List<Book> books;

    @PostConstruct
    public void init() throws ResourceNotFoundException {
        HttpServletRequest httpRequest = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        category = categoryService.getCategory(Integer.parseInt(httpRequest.getParameter("id")));
        books = bookService.getCategoryBooks(category);
    }

}
