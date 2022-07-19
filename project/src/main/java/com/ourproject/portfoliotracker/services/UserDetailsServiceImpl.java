package com.ourproject.portfoliotracker.services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.ourproject.portfoliotracker.utils.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final AccountService accountService;

    @Autowired
    public UserDetailsServiceImpl(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {

        String username = (login.contains("@")) ? accountService.getAccountByEmail(login).getUserName() : login;
        String password = accountService.getPassword(username);

        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        if (premiumIsValid(username)) {
            authorities.add(new SimpleGrantedAuthority("ROLE_PREMIUM"));
        }

        UserDetails userDetails = new UserDetailsImpl(username, password, authorities);

        return userDetails;
    }

    private boolean premiumIsValid(String username) {

        LocalDateTime subscriptionEndDate = accountService.getAccount(username).getSubscriptionEndDate();

        return subscriptionEndDate != null && LocalDateTime.now().isBefore(subscriptionEndDate);
    }

}
