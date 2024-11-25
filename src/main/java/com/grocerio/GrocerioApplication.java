package com.grocerio;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
//@EnableScheduling
public class GrocerioApplication {

	public static void main(String[] args) {
		SpringApplication.run(GrocerioApplication.class, args);
	}

}
