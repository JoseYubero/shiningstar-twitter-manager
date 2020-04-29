package com.shiningstar.twitter.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@ComponentScan(basePackages = "com.shiningstar.twitter")
@EntityScan(basePackages = { "com.shiningstar.twitter.domain.entity" })
@EnableJpaRepositories(basePackages = { "com.shiningstar.twitter.domain.repository" })
@EnableScheduling
public class ShiningstarTwitterManagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShiningstarTwitterManagerApplication.class, args);
	}

}
