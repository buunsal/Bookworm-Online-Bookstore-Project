package com.bookworm.demo.controller;


import com.bookworm.demo.exception.ResourceNotFoundException;
import com.bookworm.demo.model.Publisher;
import com.bookworm.demo.service.PublisherService;
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
@Component(value = "publisherController")
public class PublisherController implements Serializable {

    private static final long serialVersionUID = 4L;

    private List<Publisher> publishers;

    private Publisher publisher = new Publisher();

    private Publisher selectedPublisher = new Publisher();

    private List<Publisher> filteredPublishers;

    @EJB
    private PublisherService publisherService;

    @PostConstruct
    public void init() {

        this.publishers = publisherService.getPublishers();
        this.publisher.setPhoneNumber("None");

    }

    public void add() {
        publisherService.savePublisher(publisher);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Create Successful", "Created Publisher:" + publisher.getName()));
        this.publishers = publisherService.getPublishers();
        this.publisher = new Publisher();
        this.publisher.setPhoneNumber("None");
    }

    public void delete(Publisher publisher) throws ResourceNotFoundException {
        publisherService.deletePublisher(publisher.getId());
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Delete Successful", "Deleted Publisher:" + publisher.getName()));
        publishers.remove(publisher);
    }

    public void update() {
        publisherService.savePublisher(selectedPublisher);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Update Successful", "Updated Publisher:" + selectedPublisher.getName()));
        this.selectedPublisher = new Publisher();
    }

    public boolean globalFilterFunction(Object value, Object filter, Locale locale) {
        String filterText = (filter == null) ? null : filter.toString().trim().toLowerCase();
        if (filterText == null || filterText.equals("")) {
            return true;
        }

        Publisher publisher = (Publisher) value;
        return publisher.getPhoneNumber().startsWith(filterText)
                || publisher.getName().toLowerCase().contains(filterText)
                || publisher.getAddress().toLowerCase().contains(filterText)
                || publisher.getUrl().toLowerCase().contains(filterText);
    }
}
