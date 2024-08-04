package com.BookStore.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.BookStore.Util.JwtRequestFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf
                .ignoringRequestMatchers("/api/auth/register", "/api/auth/verify-otp", "/api/auth/login", "/h2-console/**") // Exclude specific endpoints from CSRF protection
            )
            .authorizeHttpRequests(authorize -> authorize
                .requestMatchers("/api/auth/register", "/api/auth/verify-otp", "/api/auth/login" , "/h2-console/**").permitAll() // Allow unauthenticated access
                .anyRequest().authenticated() // All other requests require authentication
            )
            .headers(headers -> headers
                .frameOptions().sameOrigin() // Allow frames from the same origin, needed for H2 console
            )
            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // Stateless session management
            )
            .addFilterBefore(jwtRequestFilter(), UsernamePasswordAuthenticationFilter.class); // Add JWT filter

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JwtRequestFilter jwtRequestFilter() {
        return new JwtRequestFilter();
    }
}