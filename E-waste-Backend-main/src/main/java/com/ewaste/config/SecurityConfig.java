//package com.ewaste.config;
//
//import com.ewaste.security.JwtFilter;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.web.SecurityFilterChain;
//
//@Configuration
//public class SecurityConfig {
//
//    private JwtFilter jwtFilter;
//
//    public SecurityConfig(JwtFilter jwtFilter) {
//        this.jwtFilter = jwtFilter;
//    }
//
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//
//        http
//                .csrf(csrf -> csrf.disable())
//                .httpBasic(httpBasic -> httpBasic.disable())
//                .authorizeHttpRequests(auth -> auth
//                        .requestMatchers("/api/auth/**").permitAll()
//                        .requestMatchers("/api/admin/**").authenticated()
//                        .anyRequest().authenticated()
//                )
//                .addFilterBefore(jwtFilter,
//                        org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter.class);
//
//        return http.build();
//    }
//}


package com.ewaste.config;

import com.ewaste.security.JwtFilter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.http.SessionCreationPolicy; // Import SessionCreationPolicy
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter; // Import
import org.springframework.web.cors.CorsConfiguration; // Import
import org.springframework.web.cors.CorsConfigurationSource; // Import
import org.springframework.web.cors.UrlBasedCorsConfigurationSource; // Import
import org.springframework.http.HttpMethod; // Import HttpMethod

import java.util.Arrays; // Import
import java.util.List; // Import

@Configuration
public class SecurityConfig {

    private final JwtFilter jwtFilter; // Make it final and use constructor injection

    public SecurityConfig(JwtFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // Disable CSRF for API endpoints
            .httpBasic(httpBasic -> httpBasic.disable()) // Disable HTTP Basic Auth
            .cors(cors -> cors.configurationSource(corsConfigurationSource())) // **Enable CORS with custom configuration**
            .authorizeHttpRequests(auth -> auth
                // **IMPORTANT: Allow all OPTIONS requests to bypass authentication**
                .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                .requestMatchers("/api/auth/**").permitAll() // Allow auth endpoints
                .requestMatchers("/api/admin/**").authenticated() // Secure admin endpoints
                .anyRequest().authenticated() // All other requests require authentication
            )
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Use STATELESS sessions for JWT
            // Add your custom JWT filter before Spring Security's UsernamePasswordAuthenticationFilter
            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    // **CORS Configuration Bean**
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        // Allow requests from your Angular frontend origin
        configuration.setAllowedOrigins(List.of("http://localhost:4200"));
        // Allow the HTTP methods your API supports, including OPTIONS for preflight
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS", "HEAD"));
        // Allow the headers your Angular app sends, crucial for 'Authorization'
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type"));
        // If your frontend sends cookies or custom Authorization headers (like JWTs)
        configuration.setAllowCredentials(true);
        // How long the browser can cache the preflight response (in seconds)
        configuration.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration); // Apply this CORS configuration to all paths
        return source;
    }
}
