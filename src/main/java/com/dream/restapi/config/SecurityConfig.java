package com.dream.restapi.config;

import com.dream.restapi.middleware.JwtAuthorizationFilter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Autowired
    private JwtAuthorizationFilter jwtAuthorizationFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
         http
               .csrf(csrf -> csrf.disable())
               .cors(Customizer.withDefaults())
               .authorizeHttpRequests(auth -> auth
                    .requestMatchers("/api/auth/**").permitAll()
                    .requestMatchers("/api/**").authenticated()
                    .anyRequest().permitAll()
             ).addFilterBefore(this.jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class);
         return http.build();
    }

    @Bean
     public CorsFilter corsFilter() {
          CorsConfiguration config = new CorsConfiguration();
          config.addAllowedOriginPattern("*"); // Permite todas las orígenes, ajusta según sea necesario
          config.addAllowedMethod("*"); // Permite todos los métodos HTTP
          config.addAllowedHeader("*"); // Permite todos los encabezados
          config.setAllowCredentials(true); // Permite credenciales (cookies, headers de autenticación, etc.)

          UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
          source.registerCorsConfiguration("/**", config);

     return new CorsFilter(source);
}
}
