package com.gencura.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.gencura.security.JwtAuthenticationEntryPoint;
import com.gencura.security.JwtAuthenticationFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    private final AuthenticationProvider authenticationProvider;
    
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;

    private final JwtAuthenticationEntryPoint authenticationEntryPoint;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)
            throws Exception {

        http.csrf(csrf -> csrf.disable())
	
	        .cors(Customizer.withDefaults())
	
	        .sessionManagement(session ->
	                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
	        )
	
	        .exceptionHandling(exception ->
	                exception.authenticationEntryPoint(authenticationEntryPoint)
	                .accessDeniedHandler(jwtAccessDeniedHandler)
	        )
	
	        .authenticationProvider(authenticationProvider)
	
	        .authorizeHttpRequests(auth -> auth
	
				// Authentication APIs
				.requestMatchers(
					"/api/v1/auth/login",
					"/api/v1/auth/register",
					"/api/v1/auth/forgot-password",
					"/api/v1/auth/reset-password"
				).permitAll()
				
				// Swagger
				.requestMatchers(
				    "/swagger-ui/**",
					"/swagger-ui.html",
					"/v3/api-docs/**"
				).permitAll()
				
				// OPTIONS Request
				.requestMatchers(HttpMethod.OPTIONS, "/**")
				.permitAll()
				
				// All Other APIs
				.anyRequest()
				.authenticated()
	        )
	
	        .addFilterBefore(
	            jwtAuthenticationFilter,
	            UsernamePasswordAuthenticationFilter.class
	        );

        return http.build();

    }

}