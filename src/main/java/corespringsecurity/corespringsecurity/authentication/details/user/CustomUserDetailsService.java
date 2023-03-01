package corespringsecurity.corespringsecurity.authentication.details.user;

import corespringsecurity.corespringsecurity.domain.Account;
import corespringsecurity.corespringsecurity.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service("userDetailsService")
public class CustomUserDetailsService implements UserDetailsService {
    private final AccountRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account userByUsername = userRepository.findAccountByUsername(username);

        if(userByUsername == null) throw new UsernameNotFoundException("Username Not Found Exception");

        List<GrantedAuthority> roles = new ArrayList<>();
        roles.add(new SimpleGrantedAuthority(userByUsername.getRole()));
        log.info("ROLE = {}" , userByUsername.getRole());
        return new AccountContext(userByUsername,roles);
    }
}
