package com.ourproject.portfoliotracker.security.authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationProviderService implements AuthenticationProvider {

    private AuthenticationDetailsService authDetailsService;
    
    private BCryptPasswordEncoder passwordEncoder;
    
    @Autowired
    public AuthenticationProviderService(AuthenticationDetailsService authDetailsService,
                                         BCryptPasswordEncoder passwordEncoder) {
        this.authDetailsService = authDetailsService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String login = authentication.getName();
        String password = authentication.getCredentials().toString();
        AuthenticationDetails authDetails = authDetailsService.loadUserByUsername(login);

        if (passwordEncoder.matches(password, authDetails.getPassword())) {
            return new UsernamePasswordAuthenticationToken(
                authDetails.getUsername(),
                authDetails.getPassword(),
                authDetails.getAuthorities());
        } else {
            throw new BadCredentialsException("Bad credentials");
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class
            .isAssignableFrom(authentication);
    }
    
}
