package com.ourproject.portfoliotracker.data.controller;

import com.ourproject.portfoliotracker.data.model.AccountEntity;
import com.ourproject.portfoliotracker.data.model.dto.AccountDTO;
import com.ourproject.portfoliotracker.data.service.AccountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/account")
public class AccountController {
    // FIXME: 18.05.2022 если что поменять на json
    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }
    
    @PostMapping
    public ResponseEntity<AccountDTO> addAccount(@RequestBody final AccountDTO accountDTO) {
        AccountEntity account = accountService.addAccount(AccountEntity.from(accountDTO));
        return new ResponseEntity<>(AccountDTO.from(account), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<AccountDTO>> getAllAccounts() {
        List<AccountEntity> accounts = accountService.getAccounts();
        List<AccountDTO> accountsDTO = accounts.stream()
                .map(AccountDTO::from).collect(Collectors.toList());
        return new ResponseEntity<>(accountsDTO, HttpStatus.OK);
    }

    @GetMapping(value = "{id}")
    public ResponseEntity<AccountDTO> getAccount(@PathVariable final Integer id) {
        AccountEntity account = accountService.getAccount(id);
        return new ResponseEntity<>(AccountDTO.from(account), HttpStatus.OK);
    }

    @DeleteMapping(value = "{id}")
    public ResponseEntity<AccountDTO> deleteAccount(@PathVariable final Integer id) {
        AccountEntity account = accountService.deleteAccount(id);
        return new ResponseEntity<>(AccountDTO.from(account), HttpStatus.OK);
    }

    @PutMapping(value = "{id}")
    public ResponseEntity<AccountDTO> editAccount(@PathVariable final Integer id,
                                                  @RequestBody final AccountDTO accountDTO) {
        AccountEntity account = accountService.editAccount(id, AccountEntity.from(accountDTO));
        return new ResponseEntity<>(AccountDTO.from(account), HttpStatus.OK);
    }

    @PostMapping(value = "{accountId}/dealHistory/{dealId}/add")
    public ResponseEntity<AccountDTO> addDealToAccount(@PathVariable final Integer accountId,
                                                       @PathVariable final Integer dealId) {
        AccountEntity account = accountService.addDealHistory(accountId, dealId);
        return new ResponseEntity<>(AccountDTO.from(account), HttpStatus.OK);
    }

    @DeleteMapping(value = "{accountId}/dealHistory/{dealId}/add")
    public ResponseEntity<AccountDTO> removeDealToAccount(@PathVariable final Integer accountId,
                                                       @PathVariable final Integer dealId) {
        AccountEntity account = accountService.removeDealHistory(accountId, dealId);
        return new ResponseEntity<>(AccountDTO.from(account), HttpStatus.OK);
    }

    @PostMapping(value = "{accountId}/dealHistory/{portfolioId}/add")
    public ResponseEntity<AccountDTO> addPortfolioToAccount(@PathVariable final Integer accountId,
                                                       @PathVariable final Integer portfolioId) {
        AccountEntity account = accountService.addPortfolio(accountId, portfolioId);
        return new ResponseEntity<>(AccountDTO.from(account), HttpStatus.OK);
    }

    @DeleteMapping(value = "{accountId}/dealHistory/{portfolioId}/add")
    public ResponseEntity<AccountDTO> removePortfolioToAccount(@PathVariable final Integer accountId,
                                                          @PathVariable final Integer portfolioId) {
        AccountEntity account = accountService.removePortfolio(accountId, portfolioId);
        return new ResponseEntity<>(AccountDTO.from(account), HttpStatus.OK);
    }
}
