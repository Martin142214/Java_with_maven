package com.example.demo;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

public class Context extends SpringBootServletInitializer {
    protected SpringApplicationBuilder configuration(SpringApplicationBuilder app){
        return app.sources(HomeController.class);
    }
}
