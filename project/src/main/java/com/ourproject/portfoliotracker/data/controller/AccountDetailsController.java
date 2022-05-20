package com.ourproject.portfoliotracker.data.controller;

import com.ourproject.portfoliotracker.data.model.AccountDetailsEntity;
import com.ourproject.portfoliotracker.data.service.AccountDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/account/details")
public class AccountDetailsController {

    private final AccountDetailsService accountDetailsService;

    @Autowired
    public AccountDetailsController(AccountDetailsService accountDetailsService) {
        this.accountDetailsService = accountDetailsService;
    }

    @PostMapping
    public AccountDetailsEntity addAccountDetails(
            @RequestBody final AccountDetailsEntity accountDetailsToAdd) {
        /*AccountDetailsEntity account = accountDetailsService
                .addAccountDetails(accountDetailsToAdd);*/
        return accountDetailsService.addAccountDetails(accountDetailsToAdd);
    }

    @GetMapping
    public List<AccountDetailsEntity> getAllAccountDetails() {
        //List<AccountDetailsEntity> accounts = accountDetailsService.getAllAccountsDetails();
        return accountDetailsService.getAllAccountsDetails();
    }

    @GetMapping(value = "{id}")
    public AccountDetailsEntity getAccountDetails(@PathVariable final Integer id) {
        //AccountDetailsEntity account = accountDetailsService.getAccountDetail(id);
        return accountDetailsService.getAccountDetail(id);
    }

    @DeleteMapping(value = "{id}")
    public AccountDetailsEntity deleteAccountDetails(@PathVariable final Integer id) {
        //AccountDetailsEntity account = accountDetailsService.deleteAccountDetails(id);
        return accountDetailsService.deleteAccountDetails(id);
    }

    @PutMapping(value = "{id}")
    public AccountDetailsEntity editAccountDetails(@PathVariable final Integer id,
                                                                @RequestBody final AccountDetailsEntity accountDetails) {
        /*AccountDetailsEntity editedAccount = accountDetailsService
                .editAccountDetails(id, accountDetails);*/
        return accountDetailsService.editAccountDetails(id, accountDetails);
    }
}
