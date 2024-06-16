package com.example.test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class TestApplication {

	@GetMapping("/message")
	private String sendMessage(){
		return "You have successfully deploy your service!";
	}

	public static void main(String[] args) {
		SpringApplication.run(TestApplication.class, args);
	}

}
