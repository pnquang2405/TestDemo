package com.test.spring.security.jwt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class AppTestDemo {

	public static void main(String[] args) {
		SpringApplication.run(AppTestDemo.class, args);
	}

}
