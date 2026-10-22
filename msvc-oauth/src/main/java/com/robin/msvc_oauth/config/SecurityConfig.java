package com.robin.msvc_oauth.config;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.UUID;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
// Spring Security 7: OAuth2AuthorizationServerConfiguration vive en spring-security-config.
// Si tu IDE lo marca en rojo, borra este import y deja que lo reimporte (Ctrl+Shift+O / Alt+Enter).
import org.springframework.security.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.oidc.OidcScopes;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.server.authorization.client.InMemoryRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.util.matcher.MediaTypeRequestMatcher;


/**
 * Authorization Server (OAuth 2.1 + OpenID Connect) para Spring Boot 4 / Spring Security 7.
 *
 * CAMBIO CLAVE respecto al código antiguo (Spring Authorization Server 1.x):
 *   ANTES:  OAuth2AuthorizationServerConfigurer.authorizationServer() + http.with(...)
 *   AHORA:  http.oauth2AuthorizationServer(authorizationServer -> { ... })
 */
@Configuration
public class SecurityConfig {

    // =========================================================================
    // (1) Cadena de filtros para los ENDPOINTS del Authorization Server
    //     /oauth2/authorize, /oauth2/token, /oauth2/jwks, /.well-known/..., etc.
    // =========================================================================
    @Bean
    @Order(1)
    public SecurityFilterChain authorizationServerSecurityFilterChain(HttpSecurity http) throws Exception {
        http
                .oauth2AuthorizationServer(authorizationServer -> {
                    // Esta cadena solo atiende las URLs propias del Authorization Server
                    http.securityMatcher(authorizationServer.getEndpointsMatcher());
                    authorizationServer
                            .oidc(Customizer.withDefaults());   // Habilita OpenID Connect 1.0
                })
                .authorizeHttpRequests(authorize -> authorize
                        .anyRequest().authenticated()
                )
                // Si el navegador llega sin sesión a /oauth2/authorize → redirige a /login
                .exceptionHandling(exceptions -> exceptions
                        .defaultAuthenticationEntryPointFor(
                                new LoginUrlAuthenticationEntryPoint("/login"),
                                new MediaTypeRequestMatcher(MediaType.TEXT_HTML)
                        )
                );

        return http.build();
    }

    // =========================================================================
    // (2) Cadena de filtros por defecto: login de usuarios (formulario)
    // =========================================================================
    @Bean
    @Order(2)
    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/actuator/health").permitAll()   // útil para Eureka / Docker
                        .anyRequest().authenticated()
                )
                .csrf(csrf -> csrf.disable())
                .formLogin(Customizer.withDefaults());

        return http.build();
    }

    // =========================================================================
    // (3) PasswordEncoder: soporta {bcrypt}, {noop}, etc.
    // =========================================================================
    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    // =========================================================================
    // (4) Usuarios finales que inician sesión (en memoria para pruebas).
    //     En producción: implementa UserDetailsService contra tu BD (JPA/Oracle).
    // =========================================================================
    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
        UserDetails user = User.withUsername("robin")
                //.password(passwordEncoder.encode("password"))
                .password("{noop}12345")  // para pruebas rápidas, sin encriptar
                .roles("USER")
                .build();

        UserDetails admin = User.withUsername("admin")
                //.password(passwordEncoder.encode("admin123"))
                .password("{noop}admin123")  // para pruebas rápidas, sin encriptar
                .roles("USER", "ADMIN")
                .build();

        return new InMemoryUserDetailsManager(user, admin);
    }

    // =========================================================================
    // (5) Clientes OAuth2 registrados (las apps que piden tokens)
    // =========================================================================
    @Bean
    public RegisteredClientRepository registeredClientRepository(PasswordEncoder passwordEncoder) {

        // Cliente web (Angular vía BFF/gateway) con Authorization Code + Refresh Token
        RegisteredClient webClient = RegisteredClient.withId(UUID.randomUUID().toString())
                .clientId("gateway-app")
                //.clientSecret(passwordEncoder.encode("secret"))
                .clientSecret("{noop}12345")  // para pruebas rápidas, sin encriptar
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
                .redirectUri("http://127.0.0.1:9090/login/oauth2/code/client-app")
                .redirectUri("http://127.0.0.1:9090/authorized")  // para pruebas con Postman
                .postLogoutRedirectUri("http://127.0.0.1:9090/logout")
                .scope(OidcScopes.OPENID)
                .scope(OidcScopes.PROFILE)
                .scope("read")
                .scope("write")
               /* .clientSettings(ClientSettings.builder()
                        .requireAuthorizationConsent(true)   // muestra pantalla de consentimiento
                        .requireProofKey(true)               // PKCE obligatorio
                        .build())
                .tokenSettings(TokenSettings.builder()
                        .accessTokenTimeToLive(Duration.ofMinutes(30))
                        .refreshTokenTimeToLive(Duration.ofDays(1))
                        .build()) */
                .clientSettings(ClientSettings.builder().requireAuthorizationConsent(false).build())
                .build();

        // Cliente máquina-a-máquina (microservicio ↔ microservicio)
       /* RegisteredClient serviceClient = RegisteredClient.withId(UUID.randomUUID().toString())
                .clientId("service-client")
                .clientSecret(passwordEncoder.encode("service-secret"))
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
                .scope("read")
                .scope("write")
                .build();

        return new InMemoryRegisteredClientRepository(webClient, serviceClient); */
        return new InMemoryRegisteredClientRepository(webClient);
    }

    // =========================================================================
    // (6) Llaves RSA para firmar los JWT (se generan al arrancar).
    //     En producción: cárgalas desde un keystore para que no cambien
    //     en cada reinicio ni entre réplicas.
    // =========================================================================
    @Bean
    public JWKSource<SecurityContext> jwkSource() {
        KeyPair keyPair = generateRsaKey();
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        RSAKey rsaKey = new RSAKey.Builder(publicKey)
                .privateKey(privateKey)
                .keyID(UUID.randomUUID().toString())
                .build();
        return new ImmutableJWKSet<>(new JWKSet(rsaKey));
    }

    private static KeyPair generateRsaKey() {
        try {
            KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
            generator.initialize(2048);
            return generator.generateKeyPair();
        } catch (Exception ex) {
            throw new IllegalStateException(ex);
        }
    }

    // =========================================================================
    // (7) Decoder de JWT (necesario para el endpoint /userinfo de OIDC)
    // =========================================================================
    @Bean
    public JwtDecoder jwtDecoder(JWKSource<SecurityContext> jwkSource) {
        return OAuth2AuthorizationServerConfiguration.jwtDecoder(jwkSource);
    }

    // =========================================================================
    // (8) Ajustes del Authorization Server (issuer = URL pública del servidor)
    // =========================================================================
    @Bean
    public AuthorizationServerSettings authorizationServerSettings() {
        return AuthorizationServerSettings.builder()
                .issuer("http://msvc-oauth:9100")
                .build();
    }
}