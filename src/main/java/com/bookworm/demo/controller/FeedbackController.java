package com.bookworm.demo.controller;

import com.bookworm.demo.model.Feedback;
import com.bookworm.demo.service.FeedbackService;
import lombok.Data;
import org.springframework.stereotype.Component;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import java.io.Serializable;

@Data
@ViewScoped
@Component(value = "feedbackController")
public class FeedbackController implements Serializable {

    private static final long serialVersionUID = 4L;

    private Feedback feedback = new Feedback();

    @EJB
    private FeedbackService feedbackService;

    public void newFeedback() {
        feedbackService.saveFeedback(feedback);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Your feedback has been received!", "Thank you..."));
        this.feedback = new Feedback();
    }

}
