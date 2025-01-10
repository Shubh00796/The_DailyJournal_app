package com.sprinboot2025.demo.MongoConfigs;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(basePackages = "com.sprinboot2025.demo") // Scan the entire demo package and its subpackages
public class MongoConfig {
    // No need to manually configure MongoClient or MongoTemplate if using application.properties
}
