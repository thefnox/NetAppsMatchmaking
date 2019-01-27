package com.networkapps.project.matchmaker;

import java.util.HashMap;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class MatchmakerApplication {
	public static void main(String[] args) {
		SpringApplication.run(MatchmakerApplication.class, args);
	}

}

