package com.robin.api_gateway.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class SecurityConfig {

    @Bean
    SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) throws Exception {
        return http.authorizeExchange( authz -> {
            authz.pathMatchers("/authorized","logout").permitAll()
                    .pathMatchers(HttpMethod.GET, "/api/cliente","/api/historial/socio", "/api/socio").permitAll()
                    .pathMatchers(HttpMethod.GET, "/api/socio/dni/{dni}").hasAnyRole("ADMIN", "USER")
                    .pathMatchers("/api/cliente/**","/api/historial/socio/**", "/api/socio/**").hasRole("ADMIN")
                    .anyExchange().authenticated();
        }).cors(csrf -> csrf.disable())
                .oauth2Login(withDefaults())
                .oauth2Client(withDefaults())
                .oauth2ResourceServer( oauth2 -> oauth2.jwt(withDefaults()))
                .build();
    }
}
