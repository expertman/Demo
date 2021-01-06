package com.example.biz;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.example"})
public class SpringBootBbsDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootBbsDemoApplication.class, args);
	}

}
