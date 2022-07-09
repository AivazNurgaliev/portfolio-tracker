package com.ourproject.portfoliotracker.services;

import com.ourproject.portfoliotracker.utils.AuthenticationDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationProviderService implements AuthenticationProvider {

    private final AuthenticationDetailsService authDetailsService;

    private final PasswordService passwordService;

    @Autowired
    public AuthenticationProviderService(
            AuthenticationDetailsService authDetailsService,
            PasswordService passwordService) {

        this.authDetailsService = authDetailsService;
        this.passwordService = passwordService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        String login = authentication.getName();
        String password = authentication.getCredentials().toString();
        AuthenticationDetails authDetails = authDetailsService.loadUserByUsername(login);

        if (passwordService.validatePassword(password, authDetails.getPassword())) {

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
