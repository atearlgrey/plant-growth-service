package com.example.plant_growth_service.config;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationManagerResolver;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtDecoders;
import org.springframework.security.oauth2.server.resource.authentication.*;
import org.springframework.security.oauth2.server.resource.introspection.NimbusOpaqueTokenIntrospector;
import org.springframework.security.oauth2.server.resource.introspection.OpaqueTokenIntrospector;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Value("${spring.security.oauth2.resourceserver.jwt.issuer-uri}")
    private String issuerUri;

    @Value("${spring.security.oauth2.resourceserver.opaque-token.introspection-uri}")
    private String introspectionUri;

    @Value("${spring.security.oauth2.resourceserver.opaque-token.client-id}")
    private String clientId;

    @Value("${spring.security.oauth2.resourceserver.opaque-token.client-secret}")
    private String clientSecret;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/public/**", "/swagger-ui/**", "/v3/api-docs/**").permitAll()
                        .requestMatchers("/api/admin/**").hasRole("admin")
                        .anyRequest().authenticated()
                )
                .csrf(AbstractHttpConfigurer::disable)
                // Custom resolver JWT & Opaque
                .oauth2ResourceServer(oauth2 -> oauth2.authenticationManagerResolver(authenticationManagerResolver()));

        return http.build();
    }

    /**
     * Custom resolver: nếu token có 3 phần (JWT) → decode local.
     * Nếu không → introspect (opaque).
     */
    @Bean
    public AuthenticationManagerResolver<HttpServletRequest> authenticationManagerResolver() {
        JwtDecoder jwtDecoder = JwtDecoders.fromIssuerLocation(issuerUri);
        AuthenticationManager jwtAuthManager = new ProviderManager(new JwtAuthenticationProvider(jwtDecoder));

        OpaqueTokenIntrospector introspector = new NimbusOpaqueTokenIntrospector(introspectionUri, clientId, clientSecret);
        AuthenticationManager opaqueAuthManager = new ProviderManager(new OpaqueTokenAuthenticationProvider(introspector));

        return (HttpServletRequest request) -> {
            String token = extractToken(request);
            if (token != null && token.split("\\.").length == 3) {
                // Có 3 phần: JWT
                return jwtAuthManager;
            }
            // Còn lại: Opaque
            return opaqueAuthManager;
        };
    }

    private String extractToken(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")) {
            return header.substring(7);
        }
        return null;
    }
}
