package com.trheecodes.gtvolcanos.config;

import com.trheecodes.gtvolcanos.config.properties.CorsProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableConfigurationProperties(CorsProperties.class)
public class CorsConfig {

    private final CorsProperties corsProperties;

    public CorsConfig(CorsProperties corsProperties) {
        this.corsProperties = corsProperties;
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();

        config.setAllowedOrigins(Arrays.asList(corsProperties.allowedOrigins().split(",")));
        config.setAllowedMethods(Arrays.asList(corsProperties.allowedMethods().split(",")));

        if ("*".equals(corsProperties.allowedHeaders())) {
            config.setAllowedHeaders(List.of("*"));
        } else {
            config.setAllowedHeaders(Arrays.asList(corsProperties.allowedHeaders().split(",")));
        }

        config.setAllowCredentials(corsProperties.allowCredentials());
        config.setMaxAge(corsProperties.maxAge());

        // Exponer el header Authorization para que el cliente pueda leer el JWT
        config.setExposedHeaders(List.of("Authorization"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }
}
