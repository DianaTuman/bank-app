package com.dianatuman.practicum.bank.configuration;

import com.dianatuman.practicum.bank.dto.UserDTO;
import com.dianatuman.practicum.bank.service.AccountsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationConverter;


@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
    @Autowired
    private AccountsService accountsService;

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, AuthenticationManager authenticationManager) throws Exception {

        AuthenticationFilter authFilter = new AuthenticationFilter(authenticationManager,
                new BasicAuthenticationConverter());
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/signup").permitAll()
                        .anyRequest().authenticated()
                )
                .addFilterAt(authFilter, AuthenticationFilter.class)
                .anonymous(anonymous -> anonymous.principal("guestUser"))
                .formLogin(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .logout(Customizer.withDefaults());

        return http.build();
    }

    @Bean
    protected AuthenticationManager authenticationManager() {
        return authentication -> {
            final String username = authentication.getPrincipal().toString();
            final CharSequence rawPassword = authentication.getCredentials().toString();
            UserDTO user = accountsService.loadUserByUsername(username);
            if (passwordEncoder().matches(rawPassword, user.getPassword())) {
                return new UsernamePasswordAuthenticationToken(username, user.getPassword(), user.getAuthorities());
            } else {
                return new UsernamePasswordAuthenticationToken(username, authentication.getCredentials());
            }
        };
    }

}
