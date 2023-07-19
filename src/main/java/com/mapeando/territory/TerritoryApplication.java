package com.mapeando.territory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories("com.mapeando.territory.repository")
@ComponentScan("com.mapeando.territory.*")
public class TerritoryApplication {

    public static void main(String[] args) {
        SpringApplication.run(TerritoryApplication.class, args);
    }

}
