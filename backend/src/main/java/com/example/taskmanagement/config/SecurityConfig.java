package com.example.taskmanagement.config;

import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import com.example.taskmanagement.filter.BearerTokenAuthFilter;
import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig {

        private final BearerTokenAuthFilter bearerTokenAuthFilter;
        private final AuthenticationProvider authenticationProvider;

        private final static String[] WHILTE_LIST_URL = {
                        "/v2/api-docs",
                        "/swagger-resources",
                        "/swagger-resources/**",
                        "/configuration/ui",
                        "/configuration/security",
                        "/swagger-ui.html",
                        "/webjars/**",
                        "/v3/api-docs/**",
                        "/swagger-ui/**",
                        "/docs/swagger-ui/**",
                        "/api/auth/**",
                        "/api/projects/**",
                        "/api/upload/**",
                        "/api/tasks/**" };

        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
                // http
                // .csrf(csrf -> csrf.disable())
                // .cors(cors -> cors.configurationSource(configurationSource()))
                // .authorizeHttpRequests()
                // .requestMatchers("/api/auth/profile")
                // .authenticated()
                // .requestMatchers(WHILTE_LIST_URL)
                // .permitAll()
                // .anyRequest()
                // .authenticated()
                // .and()
                // .sessionManagement()
                // .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                // .and()
                // .authenticationProvider(authenticationProvider)
                // .addFilterBefore(bearerTokenAuthFilter,
                // UsernamePasswordAuthenticationFilter.class);
                // return http.build();

                return http.csrf(csrf -> csrf.disable())
                                .cors(cors -> cors.configurationSource(configurationSource()))
                                .authorizeHttpRequests(
                                                auth -> auth
                                                                .requestMatchers("/api/auth/profile").authenticated()
                                                                .requestMatchers(WHILTE_LIST_URL).permitAll()
                                                                .anyRequest().authenticated())
                                .sessionManagement(
                                                session -> session
                                                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                                .authenticationProvider(authenticationProvider)
                                .addFilterBefore(bearerTokenAuthFilter, UsernamePasswordAuthenticationFilter.class)
                                .build();

        }

        @Bean
        public CorsConfigurationSource configurationSource() {
                CorsConfiguration configuration = new CorsConfiguration();

                configuration.setAllowedOrigins(List.of("http://localhost:5173"));
                configuration.setAllowedMethods(List.of("POST", "GET", "PUT", "DELETE"));
                configuration.setAllowedHeaders(List.of("Authorization", "Content-Type"));
                configuration.setAllowCredentials(true);

                UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

                source.registerCorsConfiguration("/**", configuration);

                return source;
        }
}
