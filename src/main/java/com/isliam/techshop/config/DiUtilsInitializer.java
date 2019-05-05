package com.isliam.techshop.config;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
public class DiUtilsInitializer {
    private final ApplicationContext applicationContext;

    public DiUtilsInitializer(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @PostConstruct
    private void init(){
        DIUtils.setApplicationContext(applicationContext);
    }
}
