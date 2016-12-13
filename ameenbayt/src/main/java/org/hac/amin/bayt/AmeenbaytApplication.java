package org.hac.amin.bayt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("org.hac") 
public class AmeenbaytApplication {

	public static void main(String[] args) {
		SpringApplication.run(AmeenbaytApplication.class, args);
	}
}
