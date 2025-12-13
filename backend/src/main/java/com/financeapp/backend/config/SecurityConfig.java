package com.financeapp.backend.config;

import com.financeapp.backend.security.JwtAuthFilter;
import com.financeapp.backend.repository.UserRepository;
import com.financeapp.backend.model.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;



@Configuration
public class SecurityConfig {


private final UserRepository userRepository;
private final JwtAuthFilter jwtFilter;


  public SecurityConfig(UserRepository userRepository, JwtAuthFilter jwtFilter) {
  this.userRepository = userRepository;
  this.jwtFilter = jwtFilter;
  }


  @Bean
  public UserDetailsService userDetailsService() {
  return username -> {
  User user = userRepository.findByEmail(username)
  .orElseThrow(() -> new RuntimeException("User not found"));


  return org.springframework.security.core.userdetails.User
  .withUsername(user.getEmail())
  .password(user.getPasswordHash())
  .authorities(new SimpleGrantedAuthority(user.getRole()))
  .build();
  };
  }


@Bean
public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

    http
        .csrf(csrf -> csrf.disable()) // ✅ Lambda DSL
        .authorizeHttpRequests(auth -> auth
            .requestMatchers("/api/auth/**", "/actuator/**").permitAll()
            .anyRequest().authenticated()
        )
        .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

    return http.build();
}


  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
  return config.getAuthenticationManager();
  }

}
