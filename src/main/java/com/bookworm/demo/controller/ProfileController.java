package com.bookworm.demo.controller;

import com.bookworm.demo.model.User;
import com.bookworm.demo.service.UserServiceImpl;
import lombok.Data;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import javax.ejb.EJB;
import java.io.Serializable;

@Data
@RequestScope
@Component(value = "profileController")
public class ProfileController implements Serializable {

    private static final long serialVersionUID = 4L;

    @EJB
    private UserServiceImpl userServiceImpl;

    public void updateProfile(User user) {
        userServiceImpl.updateUser(user);
    }

}
