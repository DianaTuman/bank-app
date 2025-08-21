package com.dianatuman.practicum.bank.configuration;

import com.dianatuman.practicum.bank.dto.UserPasswordDTO;
import com.dianatuman.practicum.bank.service.AccountsService;
import io.micrometer.core.instrument.MeterRegistry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Slf4j
@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    private final MeterRegistry meterRegistry;

    @Autowired
    private AccountsService accountsService;

    public SecurityConfiguration(MeterRegistry meterRegistry) {
        this.meterRegistry = meterRegistry;
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        log.info("Initializing SecurityFilterChain");
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/signup", "/api/notification", "/actuator/**").permitAll()
                        .anyRequest().authenticated()
                )
                .httpBasic(Customizer.withDefaults()) // Enables built-in Basic Auth
                .formLogin(Customizer.withDefaults())
                .anonymous(anon -> anon.principal("guestUser"))
                .csrf(AbstractHttpConfigurer::disable)
                .logout(Customizer.withDefaults());

        return http.build();
    }

    @Bean
    protected AuthenticationManager authenticationManager() {
        return authentication -> {
            final String username = authentication.getPrincipal().toString();
            final CharSequence rawPassword = authentication.getCredentials().toString();

            UserPasswordDTO user = accountsService.loadUserByUsername(username);

            if (passwordEncoder().matches(rawPassword, user.getPassword())) {
                return new UsernamePasswordAuthenticationToken(
                        username,
                        user.getPassword(),
                        user.getAuthorities()
                );
            } else {
                meterRegistry.counter("failed_login_attempt",
                                "login", username)
                        .increment();
                throw new BadCredentialsException("Invalid username or password");
            }
        };
    }
}
