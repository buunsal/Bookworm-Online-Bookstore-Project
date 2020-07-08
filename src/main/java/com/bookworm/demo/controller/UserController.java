package com.bookworm.demo.controller;

import com.bookworm.demo.model.User;
import com.bookworm.demo.service.UserServiceImpl;
import lombok.Data;
import org.primefaces.PrimeFaces;
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
@Component(value = "userController")
public class UserController implements Serializable {

    private static final long serialVersionUID = 4L;

    private List<User> users;

    private User user = new User();

    private User selectedUser = new User();

    private List<User> filteredUsers;

    @EJB
    private SessionController sessionController;
    @EJB
    private UserServiceImpl userService;

    @PostConstruct
    public void init() {
        this.users = userService.getUsers();
    }

    public void add() {
        if (userService.findUserByEmail(user.getEmail()) != null) {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN, "ERROR!", "This e-mail address is already registered. Please try with a different email address.");
            PrimeFaces.current().dialog().showMessageDynamic(message);
        } else {
            userService.saveAdmin(user);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Create Successful", "Created User:" + user.getEmail()));
            users.add(user);
            this.user = new User();
        }
    }

    public void delete(User user) {
        userService.deleteUser(user.getId());
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Delete Successful", "Deleted User:" + user.getEmail()));
        users.remove(user);
    }

    public void update() {
        userService.updateUser(selectedUser);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Update Successful", "Updated User:" + selectedUser.getEmail()));
        this.selectedUser = new User();
    }

    public boolean globalFilterFunction(Object value, Object filter, Locale locale) {
        String filterText = (filter == null) ? null : filter.toString().trim().toLowerCase();
        if (filterText == null || filterText.equals("")) {
            return true;
        }

        User user = (User) value;
        return user.getPhoneNumber().startsWith(filterText)
                || user.getName().toLowerCase().contains(filterText)
                || user.getEmail().toLowerCase().contains(filterText)
                || user.getAddress().toLowerCase().contains(filterText)
                || user.getLastName().toLowerCase().contains(filterText);
    }
}