package com.robin.client_keycloak.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.Customizer;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.authorizeHttpRequests( authorizeRequest ->
                authorizeRequest.requestMatchers("/public").permitAll()
                        .anyRequest().authenticated()
        ).oauth2Login(Customizer.withDefaults()).build();
    }

}
