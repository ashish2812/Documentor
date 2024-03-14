package org.example.userservice.config;


import lombok.extern.log4j.Log4j2;
import org.example.userservice.filter.JwtAuthFilter;
import org.example.userservice.serviceImp.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.Collections;
import java.util.List;

@Configuration
@Log4j2
@EnableWebSecurity
@EnableMethodSecurity
public class UserServiceSecurityConfig {
    private CorsConfigurationSource courseConfigurationSource() {
        // Creating a CorsConfiguration and setting various CORS-related properties.
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        // Allows credentials in cross-origin requests.
        corsConfiguration.setAllowCredentials(Boolean.TRUE);
        // Allows all headers in cross-origin requests.
        corsConfiguration.setAllowedHeaders(Collections.singletonList(CorsConfiguration.ALL));
        // Allows requests from any origin.
        corsConfiguration.setAllowedOrigins(Collections.singletonList(CorsConfiguration.ALL));
        // Allows all HTTP methods in cross-origin requests.
        corsConfiguration.setAllowedMethods(Collections.singletonList(CorsConfiguration.ALL));
        // Exposes the "Authorization" header to the client.
        corsConfiguration.setExposedHeaders(List.of("Authorization"));
        // Returning a CorsConfigurationSource that uses the configured CorsConfiguration.
        return request -> corsConfiguration;
    }


    @Autowired
    JwtAuthFilter jwtAuthFilter;

    @Bean
    public UserDetailsService userDetailsService() {
        return new UserDetailsServiceImpl();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable).sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .cors(cors -> cors.configurationSource(courseConfigurationSource()))
                .authorizeHttpRequests(authorize -> authorize.requestMatchers("/api/v1/user/login","api/v1/user/create").permitAll())
                .authorizeHttpRequests(authorize->authorize.requestMatchers("api/v1/user/get").authenticated())
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService());
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;

    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}

