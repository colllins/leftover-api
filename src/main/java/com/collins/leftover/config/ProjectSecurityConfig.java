package com.collins.leftover.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.password.CompromisedPasswordChecker;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.password.HaveIBeenPwnedRestApiPasswordChecker;

@Configuration
public class ProjectSecurityConfig {

    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception{
        http.csrf(csrfConfig->csrfConfig.disable())
                .authorizeHttpRequests((requests)->requests
                        .requestMatchers("/api/users/pay-periods/**", "/api/users/recurring-expenses/**", "/api/users/transactions/**").authenticated()
                        .requestMatchers("/api/users/register","/api/users/login", "/api/users/getUser","/api/users/logout", "/error").permitAll()
                );
//        http.formLogin(flc->flc.loginPage("/api/users/login").usernameParameter("email").passwordParameter("pwd"));
//        http.logout(loc->loc.logoutSuccessUrl("/api/users/logout")
//                .invalidateHttpSession(true)
//                .clearAuthentication(true)
//                .deleteCookies("JSESSIONID"));
//        http.httpBasic(withDefaults());
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public CompromisedPasswordChecker compromisedPasswordChecker(){
        return new HaveIBeenPwnedRestApiPasswordChecker();
    }
}
