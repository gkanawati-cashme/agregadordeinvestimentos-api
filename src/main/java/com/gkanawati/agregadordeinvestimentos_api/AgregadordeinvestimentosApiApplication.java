package com.gkanawati.agregadordeinvestimentos_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class AgregadordeinvestimentosApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(AgregadordeinvestimentosApiApplication.class, args);
	}

}
