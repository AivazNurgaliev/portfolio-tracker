package com.ourproject.portfoliotracker.controllers;

import com.ourproject.portfoliotracker.entities.AccountDetailsEntity;
import com.ourproject.portfoliotracker.services.AccountDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/account")
public class AccountDetailsController {

    private final AccountDetailsService accountDetailsService;

    @Autowired
    public AccountDetailsController(AccountDetailsService accountDetailsService) {
        this.accountDetailsService = accountDetailsService;
    }

    @PutMapping("/currency1")
    public AccountDetailsEntity putCurrency1(Authentication authentication,
                                             @RequestBody String currency1) {

        if (authentication == null) {
            return null;
        }

        String userName = authentication.getName();
        try {
            return accountDetailsService.editShowCurrency1(userName, currency1);
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }

        return null;
    }

    @PutMapping("/currency2")
    public AccountDetailsEntity putCurrency2(Authentication authentication,
                                             @RequestBody String currency2) {

        if (authentication == null) {
            return null;
        }

        String userName = authentication.getName();
        try {
            return accountDetailsService.editShowCurrency1(userName, currency2);
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }

        return null;
    }

    @PutMapping("/subscriptionDate")
    public AccountDetailsEntity putSubscriptionDate(Authentication authentication,
                                                    @RequestBody LocalDateTime startDate,
                                                    @RequestBody LocalDateTime endDate) {

        if (authentication == null) {
            return null;
        }

        String userName = authentication.getName();
        try {
            return accountDetailsService.editSubscriptionDate(userName, startDate, endDate);
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }

        return null;
    }
}