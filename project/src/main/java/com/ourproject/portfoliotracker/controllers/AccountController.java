package com.ourproject.portfoliotracker.controllers;

import com.ourproject.portfoliotracker.dtos.AccountDRO;
import com.ourproject.portfoliotracker.dtos.AccountDSO;
import com.ourproject.portfoliotracker.entities.AccountEntity;
import com.ourproject.portfoliotracker.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

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
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }

        return null;
    }

    @DeleteMapping
    public AccountEntity deleteAccount(Authentication authentication) {

        if (authentication == null) {
            return null;
        }

        String userName = authentication.getName();
        try {
            return accountService.deleteAccount(userName);
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }

        return null;
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
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }

        return null;
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
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }

        return null;
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
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }

        return null;
    }

}
