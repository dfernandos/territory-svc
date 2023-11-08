package com.mapeando.territory.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Component
public class FirebaseConfig {

    @Value("${firebase.type}")
    private String type;

    @Value("${firebase.project_id}")
    private String project_id;

    @Value("${firebase.private_key_id}")
    private String private_key_id;

    @Value("${firebase.private_key}")
    private String private_key;

    @Value("${firebase.client_email}")
    private String client_email;

    @Value("${firebase.client_id}")
    private String client_id;

    @Value("${firebase.auth_uri}")
    private String auth_uri;

    @Value("${firebase.token_uri}")
    private String token_uri;

    @Value("${firebase.auth_provider_x509_cert_url}")
    private String auth_provider_x509_cert_url;

    @Value("${firebase.client_x509_cert_url}")
    private String client_x509_cert_url;

    @Value("${firebase.universe_domain}")
    private String universe_domain;

    public String getPrivate_key(){
        return private_key.replace("\\n", "\n");
    }

    @Override
    public String toString() {
        return "FirebaseConfig{" +
                "type:'" + type + '\'' +
                ", project_id:'" + project_id + '\'' +
                ", private_key_id:'" + private_key_id + '\'' +
                ", private_key:'" + getPrivate_key() + '\'' +
                ", client_email:'" + client_email + '\'' +
                ", client_Id:'" + client_id + '\'' +
                ", auth_uri:'" + auth_uri + '\'' +
                ", token_uri:'" + token_uri + '\'' +
                ", auth_provider_x509_cert_url:'" + auth_provider_x509_cert_url + '\'' +
                ", client_x509_cert_url:'" + client_x509_cert_url + '\'' +
                ", universe_domain:'" + universe_domain + '\'' +
                '}';
    }
}

