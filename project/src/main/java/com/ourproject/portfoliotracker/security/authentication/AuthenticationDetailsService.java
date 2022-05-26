package com.ourproject.portfoliotracker.security.authentication;

import com.ourproject.portfoliotracker.data.account.AccountService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationDetailsService implements UserDetailsService {

    private AccountService accountService;

    @Autowired
    public AuthenticationDetailsService(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    public AuthenticationDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        AuthenticationDetails authDetails;

        if (login.contains("@")) {
            String username = accountService.getAccountByEmail(login).getUserName();
            String password = accountService.getPassword(username);
            authDetails = new AuthenticationDetails(username, password);
        } else {
            String password = accountService.getPassword(login);
            authDetails = new AuthenticationDetails(login, password);
        }

        return authDetails;
    }
    
}
