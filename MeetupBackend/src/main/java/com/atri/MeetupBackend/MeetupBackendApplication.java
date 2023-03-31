package com.atri.MeetupBackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan( basePackages = {"com.atri"} )
public class MeetupBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(MeetupBackendApplication.class, args);
	}

}
