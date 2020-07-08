package com.bookworm.demo.controller;

import com.bookworm.demo.model.User;
import com.bookworm.demo.service.UserServiceImpl;
import lombok.Data;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import java.io.IOException;
import java.io.Serializable;

@Data
@SessionScope
@Component(value = "sessionController")
public class SessionController implements Serializable {

    private static final long serialVersionUID = 4L;

    private String currentUser;
    private Boolean isAdmin = false;
    private Boolean isUser = false;
    private Boolean isLogged = false;
    private User user;

    @EJB
    private UserServiceImpl userService;
    @EJB
    private ShoppingBasketController shoppingBasketController;

    public void init() throws IOException {
        currentUser = SecurityContextHolder.getContext().getAuthentication().getName();

        if (SecurityContextHolder.getContext().getAuthentication().getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
            isAdmin = true;
            isLogged = true;
            user = userService.findUserByEmail(currentUser);
            FacesContext.getCurrentInstance().getExternalContext().redirect("/admin/adminpage.xhtml");
        } else if (SecurityContextHolder.getContext().getAuthentication().getAuthorities().contains(new SimpleGrantedAuthority("ROLE_USER"))) {
            isUser = true;
            isLogged = true;
            user = userService.findUserByEmail(currentUser);
            shoppingBasketController.initCart();
            FacesContext.getCurrentInstance().getExternalContext().redirect("/index.xhtml");
        }

    }

}
