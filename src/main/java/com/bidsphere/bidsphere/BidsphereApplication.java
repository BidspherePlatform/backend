package com.bidsphere.bidsphere;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class BidsphereApplication {

	public static void main(String[] args) {
		SpringApplication.run(BidsphereApplication.class, args);
	}

}
