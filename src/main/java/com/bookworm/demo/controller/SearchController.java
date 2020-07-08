package com.bookworm.demo.controller;

import com.bookworm.demo.model.Book;
import lombok.Data;
import org.primefaces.event.SelectEvent;
import org.springframework.stereotype.Component;

import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Data
@ViewScoped
@Component(value = "searchController")
public class SearchController implements Serializable {

    private static final long serialVersionUID = 4L;

    private Book book;

    @EJB
    private DatabaseController databaseController;

    public void onItemSelect(SelectEvent<Book> event) throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().redirect("/viewbook.xhtml?isbn=" + event.getObject().getISBN());
    }

    public List<Book> completeBook(String query) {
        String queryLowerCase = query.toLowerCase();
        List<Book> allBooks = databaseController.getBooks();
        List<Book> titleList = allBooks.stream().filter(t -> t.getTitle().toLowerCase().contains(queryLowerCase)).collect(Collectors.toList());
        List<Book> isbnList = allBooks.stream().filter(t -> t.getISBN().toLowerCase().startsWith(queryLowerCase)).collect(Collectors.toList());
        List<Book> concatlist = Stream.concat(titleList.stream(), isbnList.stream()).collect(Collectors.toList());
        List<Book> authorList = allBooks.stream().filter(t -> t.getAuthors().toString().toLowerCase().contains(queryLowerCase)).collect(Collectors.toList());
        return Stream.concat(concatlist.stream(), authorList.stream()).collect(Collectors.toList());
    }
}
