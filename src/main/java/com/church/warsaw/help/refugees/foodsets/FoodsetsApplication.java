package com.church.warsaw.help.refugees.foodsets;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableWebMvc
@EnableMongoAuditing
@SpringBootApplication
public class FoodsetsApplication {

	public static void main(String[] args) {
		SpringApplication.run(FoodsetsApplication.class, args);
	}

}
