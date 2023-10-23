package com.mapeando.territory.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;

@Configuration
public class MongoConfig {

    private final DatabaseConfigProperties properties;

    @Autowired
    public MongoConfig(DatabaseConfigProperties properties) {
        this.properties = properties;
        System.out.println("MongoConfig constructor called");
    }

    @Bean
    public MongoTemplate mongoTemplate() {
        System.out.println("Creating MongoTemplate");
        return new MongoTemplate(new SimpleMongoClientDatabaseFactory(properties.getUri()));
    }
}