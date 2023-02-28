package corespringsecurity.corespringsecurity.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;

@Configuration
public class UserDetailManagerConfig {


    @Bean
    public UserDetailsManager userDetailsManager(BCryptPasswordEncoder passwordEncoder) {
        String password = passwordEncoder.encode("1234");

        UserDetails user = User.withUsername("user")
                .password(password)
                .roles("USER").build();

        UserDetails manager = User.withUsername("manager")
                .password(password)
                .roles("MANAGER").build();

        UserDetails admin = User.withUsername("admin")
                .password(password)
                .roles("ADMIN").build();

        InMemoryUserDetailsManager inMemoryUserDetailsManager = new InMemoryUserDetailsManager();
        inMemoryUserDetailsManager.createUser(user);
        inMemoryUserDetailsManager.createUser(manager);
        inMemoryUserDetailsManager.createUser(admin);

        return inMemoryUserDetailsManager;
    }

}
