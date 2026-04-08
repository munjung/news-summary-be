package com.news.summary;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = {
		SecurityAutoConfiguration.class
})
public class SummaryApplication {

	public static void main(String[] args) {
		SpringApplication.run(SummaryApplication.class, args);
	}

}
