package com.ourproject.portfoliotracker.controllers;

import com.ourproject.portfoliotracker.dtos.AccountDRO;
import com.ourproject.portfoliotracker.dtos.AccountDSO;
import com.ourproject.portfoliotracker.entities.AccountEntity;
import com.ourproject.portfoliotracker.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/account")
public class AccountController {

    private final AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }
    
    @PostMapping
    public String addAccount(@RequestBody final AccountDRO accountToAdd) {
        accountService.addAccount(accountToAdd);
        return "redirect:/login";
    }

    @GetMapping
    public AccountDSO getAccount(Authentication authentication) {

        if (authentication == null) {
            return null;
        }

        String userName = authentication.getName();
        try {
            return accountService.getAccount(userName);
        } catch (UsernameNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @DeleteMapping
    public AccountEntity deleteAccount(Authentication authentication) {

        if (authentication == null) {
            return null;
        }

        String userName = authentication.getName();
        try {
            return accountService.deleteAccount(userName);
        } catch (UsernameNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PutMapping("/password")
    public AccountEntity putPassword(Authentication authentication,
                                     @RequestBody String newPassword) {

        if (authentication == null) {
            return null;
        }

        String userName = authentication.getName();
        try {
            return accountService.editPassword(userName, newPassword);
        } catch (UsernameNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PutMapping("/username")
    public AccountEntity putUserName(Authentication authentication,
                                     @RequestBody String newUserName) {

        if (authentication == null) {
            return null;
        }

        String userName = authentication.getName();
        try {
            return accountService.editUserName(userName, newUserName);
        } catch (UsernameNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PutMapping("/email")
    public AccountEntity putEmail(Authentication authentication,
                                  @RequestBody String newEmail) {

        if (authentication == null) {
            return null;
        }

        String userName = authentication.getName();
        try {
            return accountService.editEmail(userName, newEmail);
        } catch (UsernameNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

}
