package com.example.test_server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaAuditing //BasicEntity를 사용하기 위함.
@EnableJpaRepositories(basePackages = "com.example.test_server.repository")
public class TestServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(TestServerApplication.class, args);
		System.out.println("http://localhost:8080/test-server");
	}

}
