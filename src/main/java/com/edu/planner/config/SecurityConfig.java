package com.edu.planner.config;

import com.edu.planner.middlewares.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity()
public class SecurityConfig {

    private static final String[] PUBLIC_ROUTES = {
            "/api/auth/login",
            "/api/user/register",
            "/v3/api-docs/**",
            "/api/swagger-ui/**",
            "/api/swagger-ui.html",
            "/api/docs/**",
            "/actuator/**"
    };

    private final JwtAuthenticationFilter jwtAuthenticationFilter;


    // Lazy load the JwtAuthenticationFilter to prevent circular dependency
    public SecurityConfig(@Lazy JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }


    // Security filter chain configuration
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        //csrf - Cross-Site Request Forgery - Disable CSRF protection - To be made
        return http.csrf(AbstractHttpConfigurer::disable)
                //Cross-Origin Resource Sharing - Config CORS
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                //Authorize HTTP requests - Set public routes, admin routes, and protect other endpoints - uses requestMatchers
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(PUBLIC_ROUTES).permitAll() // Public routes
                        .requestMatchers("/api/admin/**").hasRole("ADMIN") // Admin routes
                        .anyRequest().authenticated() // Protect other endpoints
                )
                //Session management - Stateless session - No session management
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                //Add JWT filter before UsernamePasswordAuthenticationFilter == Use our filter before the default filter
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }


    // CORS configuration - Allow frontend to access the API - CORS == Cross-Origin Resource Sharing - Allows requests from different origins
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:5173", "http://127.0.0.1:5173")); // Allow frontend - development
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH"));
        configuration.setAllowedHeaders(List.of("Authorization", "Content-Type"));
        configuration.setExposedHeaders(List.of("Authorization"));
        configuration.setAllowCredentials(true); // Allow cookies

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration); // Apply to all routes
        return source;
    }


    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    public static String[] getPublicRoutes() {
        return PUBLIC_ROUTES;
    }
}
