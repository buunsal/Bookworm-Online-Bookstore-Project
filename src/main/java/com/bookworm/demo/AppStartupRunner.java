package com.bookworm.demo;


import com.bookworm.demo.controller.DatabaseController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import javax.ejb.EJB;

@Component
public class AppStartupRunner implements ApplicationRunner {

    private static final Logger logger = LoggerFactory.getLogger(AppStartupRunner.class);

    @EJB
    private DatabaseController databaseController;

    @Override
    public void run(ApplicationArguments args) {
        databaseController.init();
        logger.warn("Application Started");
        logger.warn("Database Loaded");
    }

}
