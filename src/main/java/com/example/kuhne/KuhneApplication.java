package com.example.kuhne;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;

import com.example.kuhne.config.RestConfig;
import com.example.kuhne.config.ServiceConfig;

@SpringBootApplication
@Import({ ServiceConfig.class, RestConfig.class })
@Profile("!test")
public class KuhneApplication {

	public static void main(String[] args) {
		SpringApplication.run(KuhneApplication.class, args);
	}

}
