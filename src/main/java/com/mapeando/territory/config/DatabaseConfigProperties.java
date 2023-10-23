package com.mapeando.territory.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "spring.data.mongodb")
public class DatabaseConfigProperties {

    private String uri;

    // Outras propriedades do MongoDB, se houver...

    // Getters e setters
    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }
}