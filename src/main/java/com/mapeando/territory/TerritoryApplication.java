package com.mapeando.territory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.mongo.MongoClientSettingsBuilderCustomizer;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class TerritoryApplication {


    public static void main(String[] args) {
        SpringApplication.run(TerritoryApplication.class, args);
    }

}
