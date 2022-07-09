package com.ourproject.portfoliotracker.services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.ourproject.portfoliotracker.utils.AuthenticationDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationDetailsService implements UserDetailsService {

    private final AccountService accountService;

    @Autowired
    public AuthenticationDetailsService(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    public AuthenticationDetails loadUserByUsername(String login)
            throws UsernameNotFoundException {

        String username = (login.contains("@")) ?
            accountService.getAccountByEmail(login).getUserName() : login;
        String password = accountService.getPassword(username);

        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));

        // premium account
        LocalDateTime subscriptionEndDate = accountService.getAccount(username)
                .getSubscriptionEndDate();
        if (subscriptionEndDate != null &&
            LocalDateTime.now().isBefore(subscriptionEndDate)) {
            authorities.add(new SimpleGrantedAuthority("ROLE_PREMIUM"));
        }

        AuthenticationDetails authDetails = new AuthenticationDetails(
            username, password, authorities);

        return authDetails;
    }
    
}
