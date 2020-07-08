package com.bookworm.demo.controller;

import com.bookworm.demo.exception.ResourceNotFoundException;
import com.bookworm.demo.model.Author;
import com.bookworm.demo.service.AuthorService;
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
@Component(value = "authorController")
public class AuthorController implements Serializable {

    private static final long serialVersionUID = 4L;

    private List<Author> authors;

    private Author author = new Author();

    private Author selectedAuthor = new Author();

    private List<Author> filteredAuthors;

    private int id = 0;

    @EJB
    private AuthorService authorService;

    @PostConstruct
    public void init() {
        this.authors = authorService.getAuthors();
        for (Author a :
                authors) {
            if (a.getId() > id) {
                id = a.getId();
            }
        }
        id++;
    }


    public void add() {
        author.setId(id);
        authorService.saveAuthor(author);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Create Successful", "Created Author:" + author.getName()));
        this.authors = authorService.getAuthors();
        this.author = new Author();
    }

    public void delete(Author author) throws ResourceNotFoundException {
        authorService.deleteAuthor(author.getId());
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Delete Successful", "Deleted Author:" + author.getName()));
        authors.remove(author);
    }

    public void update() {
        authorService.saveAuthor(selectedAuthor);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Update Successful", "Updated Author:" + selectedAuthor.getName()));
        this.selectedAuthor = new Author();
    }

    public boolean globalFilterFunction(Object value, Object filter, Locale locale) {
        String filterText = (filter == null) ? null : filter.toString().trim().toLowerCase();
        if (filterText == null || filterText.equals("")) {
            return true;
        }

        Author author = (Author) value;
        return author.getEmail().toLowerCase().contains(filterText)
                || author.getName().toLowerCase().contains(filterText)
                || author.getUrl().toLowerCase().contains(filterText);
    }
}
