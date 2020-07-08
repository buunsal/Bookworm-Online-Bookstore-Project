package com.bookworm.demo.controller;

import com.bookworm.demo.model.*;
import com.bookworm.demo.service.*;
import lombok.Data;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;

@Data
@ViewScoped
@Component(value = "bookController")
public class BookController implements Serializable {

    private static final long serialVersionUID = 4L;

    private List<Book> books;
    private List<Category> categories;
    private List<Author> authors;
    private List<Publisher> publishers;
    private List<Campaign> campaigns;

    private Book book = new Book();

    private Book selectedBook = new Book();
    private Campaign selectedCampaign = new Campaign();

    private List<Book> filteredBooks;

    private List<Book> selectedBooks;

    @EJB
    private BookServiceImpl bookService;
    @EJB
    private CategoryServiceImpl categoryService;
    @EJB
    private AuthorServiceImpl authorService;
    @EJB
    private PublisherServiceImpl publisherService;
    @EJB
    private CampaignServiceImpl campaignService;

    @PostConstruct
    public void init() {

        this.books = bookService.getBooks();
        this.categories = categoryService.getCategories();
        this.authors = authorService.getAuthors();
        this.publishers = publisherService.getPublishers();
        this.campaigns = campaignService.getCampaigns();

    }

    public void add() {
        bookService.saveBook(book);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Create Successful", "Created Book:" + book.getTitle()));
        this.books = bookService.getBooks();
        this.book = new Book();
    }

    public void delete(Book book) {
        bookService.deleteBook(book.getISBN());
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Delete Successful", "Deleted Book:" + book.getTitle()));
        books.remove(book);
    }

    public void update() {
        bookService.saveBook(selectedBook);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Update Successful", "Updated Book:" + selectedBook.getTitle()));
        this.selectedBook = new Book();
    }

    public boolean globalFilterFunction(Object value, Object filter, Locale locale) {
        String filterText = (filter == null) ? null : filter.toString().trim().toLowerCase();
        if (filterText == null || filterText.equals("")) {
            return true;
        }

        Book book = (Book) value;
        return book.getISBN().toLowerCase().contains(filterText) || book.getTitle().toLowerCase().contains(filterText);
    }

    public void newCampaign() {
        for (Book book : selectedBooks) {
            book.setCampaigns(new HashSet<>());
            if (selectedCampaign != null) book.getCampaigns().add(selectedCampaign);
            bookService.saveBook(book);
        }
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "New Campaign Successful", "New Campaign:" + selectedCampaign.getName()));
        this.selectedCampaign = new Campaign();
    }

    public void endCampaign() {
        for (Book book : selectedBooks) {
            book.setCampaigns(new HashSet<>());
            bookService.saveBook(book);
        }

        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "End Campaign Successful", "Campaigns Ended!"));
    }

}
