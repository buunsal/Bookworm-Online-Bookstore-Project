package com.bookworm.demo.controller;

import com.bookworm.demo.model.User;
import com.bookworm.demo.service.UserServiceImpl;
import lombok.Data;
import org.primefaces.PrimeFaces;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import java.io.Serializable;

@Data
@RequestScope
@Component(value = "registerController")
public class RegisterController implements Serializable {

    private static final long serialVersionUID = 4L;

    @EJB
    private UserServiceImpl userServiceImpl;

    private User user = new User();

    public String save() {

        if (userServiceImpl.findUserByEmail(user.getEmail()) != null) {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN, "ERROR!", "This e-mail address is already registered. Please try with a different email address.");
            PrimeFaces.current().dialog().showMessageDynamic(message);
            return "registration.xhtml";
        } else {
            userServiceImpl.registration(user);
            return "redirect.xhtml";
        }

    }

}
