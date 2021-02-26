package com.example.demo;

import com.example.demo.service.CityService;
import com.example.demo.service.CountryService;
import com.example.demo.service.impl.CityServiceImpl;
import com.example.demo.service.impl.CountryServiceImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ThuchanhModule4Application {

	public static void main(String[] args) {
		SpringApplication.run(ThuchanhModule4Application.class, args);
	}

	@Bean
	public CityService cityService(){
		return new CityServiceImpl();
	}

	@Bean
	public CountryService countryService(){
		return new CountryServiceImpl();
	}
}
