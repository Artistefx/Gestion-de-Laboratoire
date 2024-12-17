package org.ensa.serviceutilisateurs;

import org.ensa.serviceutilisateurs.jwt.JwtFilter;
import org.ensa.serviceutilisateurs.jwt.JwtUtil;
import org.ensa.serviceutilisateurs.services.UtilisateursService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    private final JwtUtil jwtUtil;
    private final UtilisateursService utilisateursService;


    public SecurityConfig(JwtUtil jwtUtil, UtilisateursService utilisateursService) {
        this.jwtUtil = jwtUtil;
        this.utilisateursService = utilisateursService;
        SecurityContextHolder.setStrategyName(SecurityContextHolder.MODE_INHERITABLETHREADLOCAL);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authz -> authz
                        .anyRequest().permitAll()  // Secure all other endpoints
                )
                .csrf(csrf -> csrf.disable()); // Disable CSRF for JWT


        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder =
                http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(utilisateursService);
        return authenticationManagerBuilder.build();
    }


}
