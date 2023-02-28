package corespringsecurity.corespringsecurity.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.expression.WebExpressionAuthorizationManager;

@Slf4j
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public WebSecurityCustomizer webSecurity(){
        WebSecurityCustomizer webSecurityCustomizer = new WebSecurityCustomizer() {
            @Override
            public void customize(WebSecurity web) {
                web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
            }
        };

        return webSecurityCustomizer;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.authorizeHttpRequests()
                .requestMatchers("/messages/**").access(new WebExpressionAuthorizationManager("hasRole('MANAGER')"))
                .requestMatchers("/mypage/**").access(new WebExpressionAuthorizationManager("hasRole('USER')"))
                .requestMatchers("/config/**").access(new WebExpressionAuthorizationManager("hasRole('ADMIN')"))
                .anyRequest().authenticated()
            .and()
                .formLogin();
        return http.build();
    }



}
