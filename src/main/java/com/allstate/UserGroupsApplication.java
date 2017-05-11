package com.allstate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(SwaggerConfiguration.class)
public class UserGroupsApplication {
	public static void main(String[] args) {
		SpringApplication.run(UserGroupsApplication.class, args);
	}

	}
