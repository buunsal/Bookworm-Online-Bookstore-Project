package com.bookworm.demo.controller;

import lombok.Data;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@ViewScoped
@Component(value = "imagesController")
public class ImagesController implements Serializable {

    private static final long serialVersionUID = 4L;

    private List<String> images;

    @PostConstruct
    public void init() {
        images = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            images.add("slide" + i + ".png");
        }
    }

}
