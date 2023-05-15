package com.nhnacademy.springsecurity.config;

import com.nhnacademy.springsecurity.service.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
public class SecurityCofig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests()
                    .requestMatchers("/admin/**").hasAuthority("ROLE_ADMIN")
                    .requestMatchers("/private-project/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_MEMBER")
                    .requestMatchers("/project/**").authenticated()
                    .requestMatchers("/redirect-index").authenticated()
                    .anyRequest().permitAll()
                    .and()
                    .requiresChannel()
                    .requestMatchers("/admin/**").requiresSecure()
                    .requestMatchers("/private-project/**").requiresSecure()
                    .requestMatchers("/project/**").requiresSecure()
                    .anyRequest().requiresInsecure()
                    .and()
                .formLogin()
                    .usernameParameter("id")
                    .passwordParameter("pwd")
                    .loginPage("/auth/login")
                    .loginProcessingUrl("/login")
                    .and()
                .logout()
                    .and()
                .csrf()
                    .and()
                .sessionManagement()
                    .sessionFixation()
                    .none()
                    .and()
                .headers()
                    .defaultsDisabled()
                    .frameOptions().sameOrigin()
                    .and()
                    .exceptionHandling()
                    .accessDeniedPage("/error/403")
                    .and()
                .build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider(CustomUserDetailsService customUserDetailsService){
        DaoAuthenticationProvider  authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(customUserDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder());

        return authenticationProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
