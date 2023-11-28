package com.javahunter.emailnotification;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class EmailNotificationApplication {

	public static void main(String[] args) {
		SpringApplication.run(EmailNotificationApplication.class, args);
	}

}
