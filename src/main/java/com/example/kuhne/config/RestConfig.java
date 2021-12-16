package com.example.kuhne.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.example.kuhne.controller.RestApiController;

@Configuration
@ComponentScan(basePackageClasses = { RestApiController.class })
public class RestConfig {

}
