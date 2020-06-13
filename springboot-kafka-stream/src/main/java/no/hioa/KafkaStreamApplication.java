package no.hioa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class KafkaStreamApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(KafkaStreamApplication.class, args);
		System.out.println("************** KafkaStreamApplication APP STARTED *****************");
	}
}
