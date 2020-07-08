package com.bookworm.demo.controller;

import com.bookworm.demo.model.User;
import com.bookworm.demo.service.UserServiceImpl;
import lombok.Data;
import org.primefaces.PrimeFaces;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import java.io.Serializable;

@Data
@ViewScoped
@Component(value = "passwordController")
public class PasswordController implements Serializable {

    private static final long serialVersionUID = 4L;

    @EJB
    private UserServiceImpl userService;

    @EJB
    private SessionController sessionController;

    @EJB
    private BCryptPasswordEncoder encoder;

    private String oldPassword;

    private String newPassword;

    public void passwordRecovery() {

        FacesMessage message;

        boolean matches = encoder.matches(oldPassword, sessionController.getUser().getPassword());

        if (matches) {
            sessionController.getUser().setPassword(newPassword);
            userService.updatePassword(sessionController.getUser());
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "SUCCESSFUL", "You have successfully changed your password.");
        } else {
            message = new FacesMessage(FacesMessage.SEVERITY_WARN, "ERROR!", "Your current password didn't match. Please try again!");
        }

        PrimeFaces.current().dialog().showMessageDynamic(message);
    }

}
