package com.ourproject.portfoliotracker.controllers;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.ourproject.portfoliotracker.services.AccountService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/analyze")
public class AnalysisController {

    AccountService accountService;

    @Autowired
    public AnalysisController(AccountService accountService) {
        this.accountService = accountService;
    }
    
    // premium only feature
    @GetMapping("/testfeature")
    public String testFeature(Authentication authentication) {
        if (authentication == null || !validatePremium(authentication))
            return null;

        return "test premium feature";
    }

    private boolean validatePremium(Authentication authentication) {

        String username = authentication.getName();
        LocalDateTime subscriptionEndDate = accountService.getAccount(username)
                .getSubscriptionEndDate();
        
        if (subscriptionEndDate == null ||
            LocalDateTime.now().isAfter(subscriptionEndDate)) {

            List<GrantedAuthority> authorities = new ArrayList<>();
            for (GrantedAuthority authority : authentication.getAuthorities()) {
                if (!authority.getAuthority().equals("ROLE_PREMIUM"))
                    authorities.add(authority);
            }

            authentication = new UsernamePasswordAuthenticationToken(
                username, authentication.getCredentials(), authorities);
            return false;
        }

        return true;
    }

}
