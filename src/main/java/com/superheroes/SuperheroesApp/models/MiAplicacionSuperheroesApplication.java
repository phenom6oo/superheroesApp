package com.superheroes.SuperheroesApp.models;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableCaching
@ComponentScan(basePackages = "com.superheroes.SuperheroesApp")
public class MiAplicacionSuperheroesApplication {

	public static void main(String[] args) {
		SpringApplication.run(MiAplicacionSuperheroesApplication.class, args);
	}

}
