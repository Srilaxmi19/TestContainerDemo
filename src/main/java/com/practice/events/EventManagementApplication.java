package com.practice.events;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = {"com.practice.events"})
//@EnableJpaRepositories("com.practice.events.repository")
@EntityScan("com.practice.events.model")
public class EventManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(EventManagementApplication.class, args);
	}

}
