package com.ourproject.portfoliotracker.data.controller;

import com.ourproject.portfoliotracker.data.model.AccountDetailsEntity;
import com.ourproject.portfoliotracker.data.model.dto.AccountDetailsDTO;
import com.ourproject.portfoliotracker.data.service.AccountDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/account/details")
public class AccountDetailsController {

    private final AccountDetailsService accountDetailsService;

    @Autowired
    public AccountDetailsController(AccountDetailsService accountDetailsService) {
        this.accountDetailsService = accountDetailsService;
    }

    @PostMapping
    public ResponseEntity<AccountDetailsDTO> addAccountDetails(
            @RequestBody final AccountDetailsDTO accountDetailsDTO) {
        AccountDetailsEntity account = accountDetailsService
                .addAccountDetails(AccountDetailsEntity.from(accountDetailsDTO));
        return new ResponseEntity<>(AccountDetailsDTO.from(account), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<AccountDetailsDTO>> getAllAccountDetails() {
        List<AccountDetailsEntity> accounts = accountDetailsService.getAllAccountsDetails();
        List<AccountDetailsDTO> accountsDTO = accounts.stream()
                .map(AccountDetailsDTO::from).collect(Collectors.toList());
        return new ResponseEntity<>(accountsDTO, HttpStatus.OK);
    }

    @GetMapping(value = "{id}")
    public ResponseEntity<AccountDetailsDTO> getAccountDetails(@PathVariable final Integer id) {
        AccountDetailsEntity account = accountDetailsService.getAccountDetail(id);
        return new ResponseEntity<>(AccountDetailsDTO.from(account), HttpStatus.OK);
    }

    @DeleteMapping(value = "{id}")
    public ResponseEntity<AccountDetailsDTO> deleteAccountDetails(@PathVariable final Integer id) {
        AccountDetailsEntity account = accountDetailsService.deleteAccountDetails(id);
        return new ResponseEntity<>(AccountDetailsDTO.from(account), HttpStatus.OK);
    }

    @PutMapping(value = "{id}")
    public ResponseEntity<AccountDetailsDTO> editAccountDetails(@PathVariable final Integer id,
                                                                @RequestBody final AccountDetailsDTO accountDetailsDTO) {
        AccountDetailsEntity editedAccount = accountDetailsService
                .editAccountDetails(id, AccountDetailsEntity.from(accountDetailsDTO));
        return new ResponseEntity<>(AccountDetailsDTO.from(editedAccount), HttpStatus.OK);
    }
}
