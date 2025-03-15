package com.restorationservice.restorationv1.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

  private final JwtAuthenticationFilter jwtAuthenticationFilter;
  private final AuthenticationProvider authProvider;

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    return http
        .csrf(csrf -> csrf.disable()) // Optional: Disable CSRF for simplicity (consider enabling if needed)
        .cors(Customizer.withDefaults())
        .authorizeHttpRequests(authRequest -> authRequest
            // Permit all requests to "/auth/**"
            .requestMatchers("/auth/**").permitAll()

            // Match all API v1 endpoints starting with "/api/v1/**"
            .requestMatchers("/api/v1/demo")
            .hasAuthority("ADMIN")// Restrict access to users with the "USER" role

            // For any other request, require authentication
            .anyRequest().authenticated())
        .formLogin(Customizer.withDefaults())
        .sessionManagement(sessionManager -> sessionManager
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Stateless session approach
        .authenticationProvider(authProvider)
        .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
        .build();
  }
}
