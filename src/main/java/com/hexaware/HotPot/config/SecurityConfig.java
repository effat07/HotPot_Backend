package com.hexaware.HotPot.config;

import com.hexaware.HotPot.security.JwtAuthFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    @Autowired
    private JwtAuthFilter jwtAuthFilter;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }



    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .cors(Customizer.withDefaults())
            .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth -> auth
                // Public endpoints
                .requestMatchers(
                    "/api/auth/**",
                    "/api/restaurants",
                    "/api/restaurants/search/**",
                    "/api/menus/available",
                    "/api/menus/search/**",
                    "/api/menus/by-restaurant/**",  
                    "/api/menus/{id}" 
                ).permitAll()
                
                // Allow specific roles to access user-related endpoints
                .requestMatchers("/api/users/**").hasAnyRole("CUSTOMER", "RESTAURANT", "ADMIN")

                // Restaurant owner and Customer can view menus
                .requestMatchers("/api/menus/**").hasAnyRole("RESTAURANT", "CUSTOMER")
                
                // Restaurant owner and Customer can manage orders
                .requestMatchers("/api/orders/**").hasAnyRole("CUSTOMER", "RESTAURANT")
                .requestMatchers("/api/orders/by-restaurant/**").hasRole("RESTAURANT")
                .requestMatchers("/api/restaurants/by-owner/**").hasRole("RESTAURANT")

                // Customer only
                .requestMatchers("/api/carts/**").hasRole("CUSTOMER")
                .requestMatchers("/api/cart-items/**").hasRole("CUSTOMER")
                .requestMatchers("/api/addresses/**").hasRole("CUSTOMER")
                .requestMatchers("/api/payments/**").hasRole("CUSTOMER")
                
                .anyRequest().authenticated()
            )
            .exceptionHandling(ex -> ex
                .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
            )
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}