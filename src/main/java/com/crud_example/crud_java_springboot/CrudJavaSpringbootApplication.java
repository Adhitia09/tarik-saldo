package com.crud_example.crud_java_springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.crud_example.crud_java_springboot")
public class CrudJavaSpringbootApplication {

	public static void main(String[] args) {
		SpringApplication.run(CrudJavaSpringbootApplication.class, args);
	}

}
