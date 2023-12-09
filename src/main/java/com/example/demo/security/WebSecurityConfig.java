package com.example.demo.security;

import com.example.demo.config.PasswordEncoderConfiguration;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Service;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.Arrays;

@EnableWebSecurity
@EnableMethodSecurity(
        securedEnabled = true)
@Service
@Configuration
public class WebSecurityConfig {

    private UserService userService;
    private JwtRequestFilter jwtRequestFilter;

    private final PasswordEncoderConfiguration passwordEncoder;

    @Autowired
    public WebSecurityConfig(UserService userService, JwtRequestFilter jwtRequestFilter,
                             PasswordEncoderConfiguration passwordEncoder) {
        this.userService = userService;
        this.jwtRequestFilter = jwtRequestFilter;
        this.passwordEncoder = passwordEncoder;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setJwtRequestFilter(JwtRequestFilter jwtRequestFilter) {
        this.jwtRequestFilter = jwtRequestFilter;
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf(AbstractHttpConfigurer::disable
                )
                .cors(cors -> {
                    CorsConfigurationSource source = request -> {
                        CorsConfiguration config = new CorsConfiguration();
                        config.setAllowedOrigins(Arrays.asList("http://localhost:5173"));
                        config.setAllowedHeaders(Arrays.asList("*"));
                        config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
                        return config;
                    };
                    cors.configurationSource(source);
                })
                .authorizeHttpRequests(requests -> requests
                        .requestMatchers("/auth/register").permitAll()
                        .requestMatchers("/unsecured").permitAll()
                        .requestMatchers("/done/secured").authenticated()
                        .requestMatchers("/year/**").authenticated()
                        .requestMatchers("/subjects/**").authenticated()
                        .requestMatchers("/works/**").authenticated()
                        .requestMatchers("/posts/get/all").hasRole("ADMIN")
                        .requestMatchers("/comments/get/all").hasRole("ADMIN")
                        .requestMatchers("/posts/delete/**").hasRole("ADMIN")
                        .requestMatchers("/comments/delete/**").hasRole("ADMIN")
                        .requestMatchers("/users/ban/**").hasRole("ADMIN")
                        .requestMatchers("/users/unban/**").hasRole("ADMIN")
                        .requestMatchers("/users/get/all").hasRole("ADMIN")
                        .requestMatchers("/posts/**").authenticated()
                        .requestMatchers("/comments/**").authenticated()
                        .requestMatchers("/users/give_moder/**").hasRole("ADMIN")
                        .anyRequest().permitAll()
                )
                .formLogin(AbstractHttpConfigurer::disable
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/")
                        .invalidateHttpSession(true))
                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
        return httpSecurity.build();
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder.passwordEncoder());
        System.out.println(passwordEncoder.passwordEncoder());
        daoAuthenticationProvider.setUserDetailsService(userService);
        return daoAuthenticationProvider;
    }


    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration
                                                               authenticationConfiguration)
            throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
