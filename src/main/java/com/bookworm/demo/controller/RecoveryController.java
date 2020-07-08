package com.bookworm.demo.controller;

import com.bookworm.demo.model.User;
import com.bookworm.demo.service.UserServiceImpl;
import lombok.Data;
import org.primefaces.PrimeFaces;
import org.springframework.stereotype.Component;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import java.io.Serializable;

@Data
@ViewScoped
@Component(value = "recoveryController")
public class RecoveryController implements Serializable {

    private static final long serialVersionUID = 4L;

    @EJB
    private UserServiceImpl userService;

    private User user = new User();

    public void passwordRecovery() {
        FacesMessage message;

        if (userService.findUserByEmail(user.getEmail()) != null) {
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "SUCCESSFUL", "We sent you an e-mail with password reset instructions.");

        } else {
            message = new FacesMessage(FacesMessage.SEVERITY_WARN, "ERROR!", "This e-mail address is not registered.");
        }

        PrimeFaces.current().dialog().showMessageDynamic(message);
    }

}

