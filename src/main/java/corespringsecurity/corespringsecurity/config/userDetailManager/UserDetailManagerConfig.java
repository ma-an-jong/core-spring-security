package corespringsecurity.corespringsecurity.config.userDetailManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class UserDetailManagerConfig {

    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserDetailManagerConfig(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }
}
