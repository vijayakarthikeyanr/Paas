package com.paas.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.paas.app"})
public class PaasPlatformApplication {

	public static void main(String[] args) {
		SpringApplication.run(PaasPlatformApplication.class, args);
	}

}
