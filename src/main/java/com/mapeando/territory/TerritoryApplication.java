package com.mapeando.territory;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.gson.Gson;
import com.mapeando.territory.config.FirebaseConfig;
import com.mapeando.territory.config.FirebaseInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

// ...

@SpringBootApplication
@ComponentScan(basePackages = {"com.mapeando.territory", "com.mapeando.territory.config"})
public class TerritoryApplication implements WebMvcConfigurer {

    public static void main(String[] args) {
        SpringApplication.run(TerritoryApplication.class, args);

    }

    @Autowired
    private FirebaseConfig firebaseConfig;

    @Override
    public void addInterceptors(InterceptorRegistry registry){
        registry.addInterceptor(new FirebaseInterceptor()).addPathPatterns("territory/create");
        registry.addInterceptor(new FirebaseInterceptor()).addPathPatterns("territory/update/{territoryId}");
        registry.addInterceptor(new FirebaseInterceptor()).addPathPatterns("territory/delete/{id}");

    }

    @Bean
    public FirebaseOptions firebaseOptions() throws IOException {

        byte[] serviceAccountBytes = convertFirebaseConfigToJson(firebaseConfig).getBytes();

        ByteArrayInputStream serviceAccountStream = new ByteArrayInputStream(serviceAccountBytes);

        return new FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccountStream))
                .build();
    }

    @Bean
    public FirebaseApp firebaseApp(FirebaseOptions firebaseOptions) {
        if (FirebaseApp.getApps().isEmpty()) {
            return FirebaseApp.initializeApp(firebaseOptions);
        }

        return FirebaseApp.getInstance();
    }

    private String convertFirebaseConfigToJson(FirebaseConfig config) {
        Gson gson = new Gson();
        config.setPrivate_key(config.getPrivate_key());
        return gson.toJson(config);
    }
}
