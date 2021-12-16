package com.example.kuhne.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.example.kuhne.util.IUtilityService;

@Configuration
@ComponentScan(basePackageClasses = { IUtilityService.class })
public class ServiceConfig {

}
