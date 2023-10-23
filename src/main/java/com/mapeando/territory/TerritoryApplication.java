package com.mapeando.territory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;

@SpringBootApplication(exclude = {MongoAutoConfiguration.class})
public class TerritoryApplication {


    public static void main(String[] args) {
        SpringApplication.run(TerritoryApplication.class, args);
    }

}
