package com.idc.plantgrowth.infrastructure.config;

import com.idc.plantgrowth.infrastructure.logging.TraceIdFilter;
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
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2TokenValidatorResult;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoders;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationProvider;
import org.springframework.security.oauth2.server.resource.authentication.OpaqueTokenAuthenticationProvider;
import org.springframework.security.oauth2.server.resource.introspection.NimbusOpaqueTokenIntrospector;
import org.springframework.security.oauth2.server.resource.introspection.OpaqueTokenIntrospector;
import org.springframework.security.web.SecurityFilterChain;

import java.util.List;

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
    public SecurityFilterChain securityFilterChain(HttpSecurity http, TraceIdFilter traceIdFilter) throws Exception {
        http
                .addFilterBefore(traceIdFilter, org.springframework.security.web.context.SecurityContextHolderFilter.class)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/api/public/**",
                                "/swagger-ui/**",
                                "/swagger-ui.html",
                                "/v3/api-docs",
                                "/v3/api-docs/**",
                                "/v3/api-docs.yaml",
                                "/v3/api-docs.yml",
                                "/swagger-resources/**",
                                "/webjars/**"
                        ).permitAll()
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
        NimbusJwtDecoder jwtDecoder = JwtDecoders.fromIssuerLocation(issuerUri);

        // Check audience
        jwtDecoder.setJwtValidator(audienceValidator(clientId));

        AuthenticationManager jwtAuthManager = new ProviderManager(new JwtAuthenticationProvider(jwtDecoder));
        OpaqueTokenIntrospector introspector = new NimbusOpaqueTokenIntrospector(introspectionUri, clientId, clientSecret);
        AuthenticationManager opaqueAuthManager = new ProviderManager(new OpaqueTokenAuthenticationProvider(introspector));

        return (HttpServletRequest request) -> {
            String token = extractToken(request);
            if (token != null && token.split("\\.").length == 3) {
                // JWT
                return jwtAuthManager;
            }
            // Opaque
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

    private OAuth2TokenValidator<Jwt> audienceValidator(String expectedAudience) {
        return token -> {
            List<String> audiences = token.getAudience();
            if (audiences != null && audiences.contains(expectedAudience)) {
                // Hợp lệ
                return OAuth2TokenValidatorResult.success();
            }
            // Sai audience
            OAuth2Error error = new OAuth2Error("invalid_token", "The required audience is missing", null);
            return OAuth2TokenValidatorResult.failure(error);
        };
    }
}
