package com.ourproject.portfoliotracker.data.controller;

import com.ourproject.portfoliotracker.data.model.AccountEntity;
import com.ourproject.portfoliotracker.data.service.AccountService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/account")
public class AccountController {
    // FIXME: 18.05.2022 если что поменять на json
    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }
    
    @PostMapping
    public AccountEntity addAccount(@RequestBody final AccountEntity accountToAdd) {
        //AccountEntity account = accountService.addAccount(accountToAdd);
        return accountService.addAccount(accountToAdd);
    }

    @GetMapping
    public List<AccountEntity> getAllAccounts() {
        //List<AccountEntity> accounts = accountService.getAccounts();
        return accountService.getAccounts();
    }

    @GetMapping(value = "{id}")
    public AccountEntity getAccount(@PathVariable final Integer id) {
        //AccountEntity account = accountService.getAccount(id);
        return accountService.getAccount(id);
    }

    @DeleteMapping(value = "{id}")
    public AccountEntity deleteAccount(@PathVariable final Integer id) {
        //AccountEntity account = accountService.deleteAccount(id);
        return accountService.deleteAccount(id);
    }

    @PutMapping(value = "{id}")
    public AccountEntity editAccount(@PathVariable final Integer id,
                                                  @RequestBody final AccountEntity accountRetrieve) {
        //AccountEntity account = accountService.editAccount(id, accountRetrieve);
        return accountService.editAccount(id, accountRetrieve);
    }

    @PostMapping(value = "{accountId}/dealHistory/{dealId}/add")
    public AccountEntity addDealToAccount(@PathVariable final Integer accountId,
                                                       @PathVariable final Integer dealId) {
        //AccountEntity account = accountService.addDealHistory(accountId, dealId);
        return accountService.addDealHistory(accountId, dealId);
    }

    @DeleteMapping(value = "{accountId}/dealHistory/{dealId}/add")
    public AccountEntity removeDealToAccount(@PathVariable final Integer accountId,
                                                       @PathVariable final Integer dealId) {
        //AccountEntity account = accountService.removeDealHistory(accountId, dealId);
        return accountService.removeDealHistory(accountId, dealId);
    }

    @PostMapping(value = "{accountId}/dealHistory/{portfolioId}/add")
    public AccountEntity addPortfolioToAccount(@PathVariable final Integer accountId,
                                                       @PathVariable final Integer portfolioId) {
        //AccountEntity account = accountService.addPortfolio(accountId, portfolioId);
        return accountService.addPortfolio(accountId, portfolioId);
    }

    @DeleteMapping(value = "{accountId}/dealHistory/{portfolioId}/add")
    public AccountEntity removePortfolioToAccount(@PathVariable final Integer accountId,
                                                          @PathVariable final Integer portfolioId) {
        //AccountEntity account = accountService.removePortfolio(accountId, portfolioId);
        return accountService.removePortfolio(accountId, portfolioId);
    }
}
