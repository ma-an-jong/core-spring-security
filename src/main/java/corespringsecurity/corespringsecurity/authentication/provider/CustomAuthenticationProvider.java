package corespringsecurity.corespringsecurity.authentication.provider;

import corespringsecurity.corespringsecurity.authentication.details.auth.FormWebAuthenticationDetails;
import corespringsecurity.corespringsecurity.authentication.details.user.AccountContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        log.info("CustomAuthenticationProvider = {}" , authentication);
        String username = authentication.getName();
        String password = (String)authentication.getCredentials();
        FormWebAuthenticationDetails authenticationDetails = (FormWebAuthenticationDetails) authentication.getDetails();
        String secretKey = authenticationDetails.getSecretKey();

        AccountContext userDetails = (AccountContext)userDetailsService.loadUserByUsername(username);
        if(!passwordEncoder.matches(password,userDetails.getPassword())) throw new BadCredentialsException("password doesn't matches");
        if(secretKey == null || !secretKey.equals("secret") ) throw new InsufficientAuthenticationException("secretKey does not exist or match");

        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
        return token;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
