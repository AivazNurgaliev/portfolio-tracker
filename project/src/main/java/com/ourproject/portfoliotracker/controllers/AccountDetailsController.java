package com.ourproject.portfoliotracker.controllers;

import com.ourproject.portfoliotracker.dtos.AccountDRO;
import com.ourproject.portfoliotracker.entities.AccountDetailsEntity;
import com.ourproject.portfoliotracker.entities.AccountEntity;
import com.ourproject.portfoliotracker.services.AccountDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.sql.Date;

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
        } catch (UsernameNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PutMapping("/currency2")
    public AccountDetailsEntity putCurrency2(Authentication authentication,
                                             @RequestBody String currency2) {

        if (authentication == null) {
            return null;
        }

        String userName = authentication.getName();
        try {
            return accountDetailsService.editShowCurrency2(userName, currency2);
        } catch (UsernameNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
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
        } catch (UsernameNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PutMapping("/loginDate")
    public AccountDetailsEntity putLastLoginDate(Authentication authentication,
                                                 @RequestBody Date date) {

        if (authentication == null) {
            return null;
        }

        String userName = authentication.getName();
        try {
            return accountDetailsService.editLastLoginDate(userName, date);
        } catch (UsernameNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PostMapping("/addDetails")
    public AccountDetailsEntity addAccountDetails(Authentication authentication,
                                                  @RequestBody AccountDRO accountDRO) {

        return accountDetailsService.addAccountDetails(accountDRO);
    }
}