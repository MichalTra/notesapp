package com.notesapp.notesapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class ProjectSecurityConfig {

    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {

        http.csrf().ignoringRequestMatchers("/submitLoginInformation")
                .and()
                .authorizeHttpRequests()
                .requestMatchers("/notes").authenticated()
                .requestMatchers("/calendar").authenticated()
                .requestMatchers("/note").authenticated()
                .requestMatchers("/edit").authenticated()
                // login page and all it contains must be publicly available
                .requestMatchers("/login").permitAll()
                .requestMatchers("/assets/**").permitAll()
                .requestMatchers("/header").permitAll()
                .requestMatchers("/submitLoginInformation").permitAll()
                .anyRequest().permitAll()
                .and()
                .formLogin().loginPage("/login").defaultSuccessUrl("/notes").failureUrl("/login?error=true").permitAll()
                .and()
                .logout().logoutSuccessUrl("/login?logout=true").invalidateHttpSession(true).permitAll()
                .and()
                .httpBasic();

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
