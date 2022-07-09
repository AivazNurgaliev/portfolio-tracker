package com.ourproject.portfoliotracker.services;

import com.ourproject.portfoliotracker.dtos.AccountDRO;
import com.ourproject.portfoliotracker.dtos.AccountDSO;
import com.ourproject.portfoliotracker.entities.AccountEntity;
import com.ourproject.portfoliotracker.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Date;
import java.time.LocalDate;

@Service
public class AccountService {

    private final AccountRepository accountRepository;
    private final PasswordService passwordService;

    @Autowired
    public AccountService(AccountRepository accountRepository,
            PasswordService passwordService) {
        this.accountRepository = accountRepository;
        this.passwordService = passwordService;
    }

    public Integer getUserId(String userName) {

        AccountEntity account = accountRepository.findByUserName(userName);
        if (account == null) {
            throw new UsernameNotFoundException("User " + userName + " not found");
        }

        return account.getAccountId();
    }

    public AccountEntity addAccount(AccountDRO accountDRO) {

        AccountEntity account = new AccountEntity();
        account.setUserName(accountDRO.getUserName());
        account.setEmail(accountDRO.getEmail());
        account.setPass(accountDRO.getPass());
        account.setRegistrationDate(Date.valueOf(LocalDate.now()));
        return accountRepository.save(account);
    }

    public AccountDSO getAccount(String userName) {

        AccountEntity account = accountRepository.findByUserName(userName);
        if (account == null) {
            throw new UsernameNotFoundException("User " + userName + " not found");
        }

        AccountDSO accountDSO = new AccountDSO();
        accountDSO.setEmail(account.getEmail());
        accountDSO.setRegistrationDate(account.getRegistrationDate());
        accountDSO.setUserName(account.getUserName());
        accountDSO.setLastLoginDate(account.getAccountDetailsByAccountId().getLastLoginDate());
        accountDSO.setShowCurrency1(account.getAccountDetailsByAccountId().getShowCurrency1());
        accountDSO.setShowCurrency2(account.getAccountDetailsByAccountId().getShowCurrency2());
        accountDSO.setSubscriptionStartDate(account.getAccountDetailsByAccountId().getSubscriptionStartDate());
        accountDSO.setSubscriptionEndDate(account.getAccountDetailsByAccountId().getSubscriptionEndDate());

        return accountDSO;
    }

    public AccountDSO getAccountByEmail(String email) {

        AccountEntity account = accountRepository.findByEmail(email);
        if (account == null) {
            throw new UsernameNotFoundException("User with email " + email + " not found");
        }

        AccountDSO accountDSO = new AccountDSO();
        accountDSO.setEmail(account.getEmail());
        accountDSO.setRegistrationDate(account.getRegistrationDate());
        accountDSO.setUserName(account.getUserName());
        accountDSO.setLastLoginDate(account.getAccountDetailsByAccountId().getLastLoginDate());
        accountDSO.setShowCurrency1(account.getAccountDetailsByAccountId().getShowCurrency1());
        accountDSO.setShowCurrency2(account.getAccountDetailsByAccountId().getShowCurrency2());
        accountDSO.setSubscriptionStartDate(account.getAccountDetailsByAccountId().getSubscriptionStartDate());
        accountDSO.setSubscriptionEndDate(account.getAccountDetailsByAccountId().getSubscriptionEndDate());

        return accountDSO;
    }

    public AccountEntity deleteAccount(String userName) {

        AccountEntity account = accountRepository.findByUserName(userName);
        if (account == null) {
            throw new UsernameNotFoundException("User " + userName + " not found");
        }

        accountRepository.delete(account);

        return account;
    }

    @Transactional
    public AccountEntity editUserName(String userName, String newUserName) {

        AccountEntity account = accountRepository.findByUserName(userName);
        if (account == null) {
            throw new UsernameNotFoundException("User " + userName + " not found");
        }

        account.setUserName(newUserName);

        return account;
    }

    @Transactional
    public AccountEntity editEmail(String userName, String newEmail) {

        AccountEntity account = accountRepository.findByUserName(userName);
        if (account == null) {
            throw new UsernameNotFoundException("User " + userName + " not found");
        }

        account.setEmail(newEmail);

        return account;
    }

    @Transactional
    public AccountEntity editPassword(String userName, String newPassword) {

        AccountEntity account = accountRepository.findByUserName(userName);
        if (account == null) {
            throw new UsernameNotFoundException("User " + userName + " not found");
        }

        String encodedPass = passwordService.encode(newPassword);
        account.setPass(encodedPass);

        return account;
    }

    public String getPassword(String userName) {

        AccountEntity account = accountRepository.findByUserName(userName);
        if (account == null) {
            throw new UsernameNotFoundException("User " + userName + " not found");
        }

        String password = account.getPass();
        return password;
    }
}
