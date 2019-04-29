package com.efo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties
public class EfoApplication {

	public static void main(String[] args) {
		SpringApplication.run(EfoApplication.class, args);
	}
}
