package it.unicam.cs.agrotrace.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    @Profile("dev")
    public SecurityFilterChain devFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable))
                .authorizeHttpRequests(authorizeRequests -> {
                    authorizeRequests.requestMatchers(
                            "/h2-console/**",
                            "/swagger-ui.html",
                            "/swagger-ui/**",
                            "/v3/api-docs/**",
                            "/api/v1/**"
                    ).permitAll();
                    authorizeRequests.requestMatchers("/api/content/").authenticated();
                    authorizeRequests.requestMatchers("/api/content/{contentId}/validate").hasRole("CURATOR");
                    authorizeRequests.anyRequest().authenticated();
                })
                .httpBasic(Customizer.withDefaults())
                .build();
    }

    @Bean
    @Profile("dev")
    @SuppressWarnings("deprecation") // NoOpPasswordEncoder is deprecated but used here for simplicity in development
    public AuthenticationManager devAuthManager(HttpSecurity http, UserDetailsService userDetailsService) throws Exception {
        AuthenticationManagerBuilder autheManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);

        autheManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(NoOpPasswordEncoder.getInstance());

        return autheManagerBuilder.build();
    }


}
