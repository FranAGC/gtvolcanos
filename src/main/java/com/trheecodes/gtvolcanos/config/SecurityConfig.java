package com.trheecodes.gtvolcanos.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.trheecodes.gtvolcanos.auth.jwt.JwtAuthenticationFilter;
import com.trheecodes.gtvolcanos.shared.exception.ApiError;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfigurationSource;

import java.io.IOException;
import java.time.Instant;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

        private final JwtAuthenticationFilter jwtAuthenticationFilter;
        private final UserDetailsService userDetailsService;
        private final PasswordEncoder passwordEncoder;
        private final CorsConfigurationSource corsConfigurationSource;
        private final ObjectMapper objectMapper;

        private static final String[] PUBLIC_URLS = {
                        "/auth/**",
                        "/swagger-ui/**",
                        "/swagger-ui.html",
                        "/api-docs/**",
                        "/api-docs"
        };

        @Bean
        public DaoAuthenticationProvider authenticationProvider() {
                DaoAuthenticationProvider provider = new DaoAuthenticationProvider(userDetailsService);
                provider.setPasswordEncoder(passwordEncoder);
                return provider;
        }

        @Bean
        public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
                return config.getAuthenticationManager();
        }

        @Bean
        public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
                http
                                .cors(c -> c.configurationSource(corsConfigurationSource))
                                .csrf(AbstractHttpConfigurer::disable)
                                .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                                .authenticationProvider(authenticationProvider())
                                .exceptionHandling(ex -> ex
                                                .authenticationEntryPoint((req, res, e) -> writeError(res, 401,
                                                                "No autenticado",
                                                                "Token requerido o inválido"))
                                                .accessDeniedHandler((req, res, e) -> writeError(res, 403,
                                                                "Acceso denegado",
                                                                "No tienes permisos para este recurso")))
                                .authorizeHttpRequests(auth -> auth
                                                // ── Volcanoes ──────────────────────────────────────
                                                // .requestMatchers(HttpMethod.GET, "/volcanoes",
                                                // "/volcanoes/**").permitAll()
                                                .requestMatchers(HttpMethod.POST, "/volcanoes")
                                                .hasAnyRole("ADMIN", "EDITOR")
                                                .requestMatchers(HttpMethod.PATCH, "/volcanoes/**")
                                                .hasAnyRole("ADMIN", "EDITOR")
                                                .requestMatchers(HttpMethod.DELETE, "/volcanoes/**")
                                                .hasAnyRole("ADMIN", "EDITOR")
                                                // ── Volcano Tourism ────────────────────────────────
                                                // .requestMatchers(HttpMethod.GET, "/volcano-tourism",
                                                // "/volcano-tourism/**").permitAll()
                                                .requestMatchers(HttpMethod.POST, "/volcano-tourism")
                                                .hasAnyRole("ADMIN", "EDITOR")
                                                .requestMatchers(HttpMethod.PATCH, "/volcano-tourism/**")
                                                .hasAnyRole("ADMIN", "EDITOR")
                                                .requestMatchers(HttpMethod.DELETE, "/volcano-tourism/**")
                                                .hasAnyRole("ADMIN", "EDITOR")
                                                // ── Guides ─────────────────────────────────────────
                                                // .requestMatchers(HttpMethod.GET, "/guides", "/guides/**").permitAll()
                                                .requestMatchers(HttpMethod.POST, "/guides")
                                                .hasAnyRole("ADMIN", "EDITOR")
                                                .requestMatchers(HttpMethod.PATCH, "/guides/**")
                                                .hasAnyRole("ADMIN", "EDITOR")
                                                .requestMatchers(HttpMethod.DELETE, "/guides/**")
                                                .hasAnyRole("ADMIN", "EDITOR")
                                                // ── Guide-Volcano ──────────────────────────────────
                                                // .requestMatchers(HttpMethod.GET, "/guide-volcano/**").permitAll()
                                                .requestMatchers(HttpMethod.POST, "/guide-volcano")
                                                .hasAnyRole("ADMIN", "EDITOR")
                                                .requestMatchers(HttpMethod.PATCH, "/guide-volcano/**")
                                                .hasAnyRole("ADMIN", "EDITOR")
                                                .requestMatchers(HttpMethod.DELETE, "/guide-volcano/**")
                                                .hasAnyRole("ADMIN", "EDITOR")
                                                // ── Users ──────────────────────────────────────────
                                                .requestMatchers(HttpMethod.GET, "/**").permitAll()
                                                .requestMatchers(PUBLIC_URLS).permitAll()
                                                .requestMatchers(HttpMethod.POST, "/users").hasRole("ADMIN")
                                                .anyRequest().authenticated())
                                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

                return http.build();
        }

        private void writeError(jakarta.servlet.http.HttpServletResponse response,
                        int status, String error, String message) throws IOException {
                ApiError apiError = ApiError.builder()
                                .timestamp(Instant.now())
                                .status(status)
                                .error(error)
                                .message(message)
                                .path(null)
                                .build();
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                response.setStatus(status);
                objectMapper.writeValue(response.getWriter(), apiError);
        }
}