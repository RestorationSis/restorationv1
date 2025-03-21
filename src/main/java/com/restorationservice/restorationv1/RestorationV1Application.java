package com.restorationservice.restorationv1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class RestorationV1Application {

	public static void main(String[] args) {
		SpringApplication.run(RestorationV1Application.class, args);
	}

}
