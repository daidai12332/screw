package com.example.screw;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class ScrewApplication {

	public static void main(String[] args) {
		SpringApplication.run(ScrewApplication.class, args);
	}

}
