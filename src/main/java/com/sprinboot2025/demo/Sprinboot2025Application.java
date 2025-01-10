package com.sprinboot2025.demo;

import com.sprinboot2025.demo.utility.World;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class Sprinboot2025Application {

	public static void main(String[] args) {
//		World worldThread = new World(); // Create a new thread object
//		worldThread.start(); // Start the thread, which calls the run() method
//
//		// You can have other code running concurrently here in the main thread
//		for (int i = 0; i < 1000000; i++) {
//			System.out.println("Main");
//		}
		SpringApplication.run(Sprinboot2025Application.class, args);


	}


	@Bean
	public PlatformTransactionManager transactionManager(MongoDatabaseFactory dbFactory) {
		return new MongoTransactionManager(dbFactory);
	}




}