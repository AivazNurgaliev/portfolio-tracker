package com.ourproject.portfoliotracker.data.service;

import com.ourproject.portfoliotracker.data.model.AccountEntity;
import com.ourproject.portfoliotracker.data.model.DealHistoryEntity;
import com.ourproject.portfoliotracker.data.model.PortfolioEntity;
import com.ourproject.portfoliotracker.data.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class AccountService {

    private final AccountRepository accountRepository;
    private final AccountDetailsService accountDetailsService;
    private final DealHistoryService dealHistoryService;
    private final PortfolioService portfolioService;

    // FIXME: 18.05.2022 reg form create service
    @Autowired
    public AccountService(AccountRepository accountRepository,
                          AccountDetailsService accountDetailsService,
                          DealHistoryService dealHistoryService,
                          PortfolioService portfolioService) {
        this.accountRepository = accountRepository;
        this.accountDetailsService = accountDetailsService;
        this.dealHistoryService = dealHistoryService;
        this.portfolioService = portfolioService;
    }

    //Creating an account object
    //Returning persisted object
    public AccountEntity addAccount(AccountEntity account) {
        return accountRepository.save(account);
    }

    //Returning a List of all accounts
    public List<AccountEntity> getAccounts() {
        return StreamSupport
                .stream(accountRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    //Getting an Account by id
    public AccountEntity getAccount(Integer id) {
        return accountRepository.findById(id).orElseThrow(() ->
                new RuntimeException("Error! Account not found"));
    }

    //Deleting an Account by id
    public AccountEntity deleteAccount(Integer id) {
        //Validating, if the object does not exist it will throw an error
        AccountEntity account = getAccount(id);
        accountRepository.delete(account);
        return account;
    }

    //id - account to edit
    //accountObj - account that we use to edit object with certain id
    // FIXME: 18.05.2022 обсудить насчет редактирования отдельных полей
    public AccountEntity editAccount(Integer id, AccountEntity accountObj) {
        AccountEntity account = getAccount(id);
        account.setUserName(accountObj.getUserName());
        account.setPass(accountObj.getPass());
        account.setEmail(accountObj.getEmail());
        account.setRegistrationDate(accountObj.getRegistrationDate());
        return account;
    }

    @Transactional
    public AccountEntity addDealHistory(Integer accountId, Integer dealId) {
        AccountEntity account = getAccount(accountId);
        DealHistoryEntity dealHistory = dealHistoryService.getDealHistory(dealId);
        // FIXME: 18.05.2022 возможно нужна проверка на то что есть уже такая запись
        account.addDealHistory(dealHistory);
        return account;
    }

    @Transactional
    public AccountEntity removeDealHistory(Integer accountId, Integer dealId) {
        AccountEntity account = getAccount(accountId);
        DealHistoryEntity dealHistory = dealHistoryService.getDealHistory(dealId);
        account.removeDealHistory(dealHistory);
        return account;
    }

    @Transactional
    public AccountEntity addPortfolio(Integer accountId, Integer portfolioId) {
        AccountEntity account = getAccount(accountId);
        PortfolioEntity portfolio = portfolioService.getPorfolio(portfolioId);
        // FIXME: 18.05.2022 возможно нужна проверка на то что есть уже такая запись
        account.addPortfolio(portfolio);
        return account;
    }

    @Transactional
    public AccountEntity removePortfolio(Integer accountId, Integer portfolioId) {
        AccountEntity account = getAccount(accountId);
        PortfolioEntity portfolio = portfolioService.getPorfolio(portfolioId);
        account.removePortfolio(portfolio);
        return account;
    }
}
