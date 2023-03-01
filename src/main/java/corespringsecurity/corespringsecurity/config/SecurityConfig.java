package corespringsecurity.corespringsecurity.config;

import corespringsecurity.corespringsecurity.authentication.provider.CustomAuthenticationProvider;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.access.expression.WebExpressionAuthorizationManager;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

@Slf4j
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private AuthenticationDetailsSource<HttpServletRequest, WebAuthenticationDetails> authenticationDetailsSource;
    private AuthenticationSuccessHandler successHandler;
    private AuthenticationFailureHandler failureHandler;
    private AccessDeniedHandler accessDeniedHandler;

    @Autowired
    public SecurityConfig(AuthenticationDetailsSource<HttpServletRequest, WebAuthenticationDetails> authenticationDetailsSource,
                          AuthenticationSuccessHandler successHandler,
                          AuthenticationFailureHandler failureHandler,
                          AccessDeniedHandler accessDeniedHandler) {
        this.authenticationDetailsSource = authenticationDetailsSource;
        this.successHandler = successHandler;
        this.failureHandler = failureHandler;
        this.accessDeniedHandler = accessDeniedHandler;
    }

    @Bean
    public WebSecurityCustomizer webSecurity(){
        return new WebSecurityCustomizer() {
            @Override
            public void customize(WebSecurity web) {
                web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
                web.ignoring().requestMatchers("/error");
            }
        };

    }

    @Bean
    public AuthenticationManager authManager(HttpSecurity http,AuthenticationProvider authenticationProvider) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder =
                http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.authenticationProvider(authenticationProvider);
        return authenticationManagerBuilder.build();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.authorizeHttpRequests()
                .requestMatchers("/","/users","/login/**","/login*").permitAll()
                .requestMatchers("/messages/**").access(new WebExpressionAuthorizationManager("hasRole('MANAGER')"))
                .requestMatchers("/mypage/**").access(new WebExpressionAuthorizationManager("hasRole('USER')"))
                .requestMatchers("/config/**").access(new WebExpressionAuthorizationManager("hasRole('ADMIN')"))
                .anyRequest().authenticated()
            .and()
                .formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/login_proc")
                .successHandler(successHandler)
                .defaultSuccessUrl("/")
                .failureHandler(failureHandler)
                .authenticationDetailsSource(authenticationDetailsSource)
            .and()
                .exceptionHandling()
                .accessDeniedHandler(accessDeniedHandler);
        return http.build();
    }



}
