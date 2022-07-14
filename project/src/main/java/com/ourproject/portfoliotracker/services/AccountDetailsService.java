package com.ourproject.portfoliotracker.services;

import com.ourproject.portfoliotracker.dtos.AccountDRO;
import com.ourproject.portfoliotracker.entities.AccountEntity;
import com.ourproject.portfoliotracker.repositories.AccountRepository;
import com.ourproject.portfoliotracker.entities.AccountDetailsEntity;
import com.ourproject.portfoliotracker.repositories.AccountDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Date;
import java.time.LocalDateTime;

@Service
public class AccountDetailsService {

    private final AccountDetailsRepository accountDetailsRepository;
    private final AccountRepository accountRepository;

    @Autowired
    public AccountDetailsService(AccountDetailsRepository accountDetailsRepository,
                                 AccountRepository accountRepository) {
        this.accountDetailsRepository = accountDetailsRepository;
        this.accountRepository = accountRepository;
    }

    public AccountDetailsEntity addAccountDetails(AccountDRO accountDRO) {

        AccountDetailsEntity accountDetails = new AccountDetailsEntity();
        AccountEntity account = accountRepository.findByUserName(accountDRO.getUserName());
        if (account == null) {
            throw new UsernameNotFoundException("User " + account.getUserName() + " not found");
        }
        accountDetails.setAccountId(account.getAccountId());
        accountDetails.setShowCurrency1(accountDRO.getShowCurrency1());
        accountDetails.setShowCurrency2(accountDRO.getShowCurrency2());
        return accountDetailsRepository.save(accountDetails);
    }

    @Transactional
    public AccountDetailsEntity editLastLoginDate(String userName, Date date) {

        AccountEntity account = accountRepository.findByUserName(userName);
        if (account == null) {
            throw new UsernameNotFoundException("User " + userName + " not found");
        }

        Integer accountId = accountRepository.findByUserName(userName).getAccountId();
        AccountDetailsEntity accountDetails = accountDetailsRepository.getById(accountId);
        accountDetails.setLastLoginDate(date);

        return accountDetails;
    }

    @Transactional
    public AccountDetailsEntity editSubscriptionDate(String userName,
                                                     LocalDateTime startDate,
                                                     LocalDateTime endDate) {

        AccountEntity account = accountRepository.findByUserName(userName);
        if (account == null) {
            throw new UsernameNotFoundException("User " + userName + " not found");
        }

        Integer accountId = accountRepository.findByUserName(userName).getAccountId();
        AccountDetailsEntity accountDetails = accountDetailsRepository.getById(accountId);
        accountDetails.setSubscriptionStartDate(startDate);
        accountDetails.setSubscriptionEndDate(endDate);

        return accountDetails;
    }

    @Transactional
    public AccountDetailsEntity editShowCurrency1(String userName, String currency1) {

        AccountEntity account = accountRepository.findByUserName(userName);
        if (account == null) {
            throw new UsernameNotFoundException("User " + userName + " not found");
        }

        Integer accountId = accountRepository.findByUserName(userName).getAccountId();
        AccountDetailsEntity accountDetails = accountDetailsRepository.getById(accountId);
        accountDetails.setShowCurrency1(currency1);

        return accountDetails;
    }

    @Transactional
    public AccountDetailsEntity editShowCurrency2(String userName, String currency2) {

        AccountEntity account = accountRepository.findByUserName(userName);
        if (account == null) {
            throw new UsernameNotFoundException("User " + userName + " not found");
        }

        Integer accountId = accountRepository.findByUserName(userName).getAccountId();
        AccountDetailsEntity accountDetails = accountDetailsRepository.getById(accountId);
        accountDetails.setShowCurrency2(currency2);

        return accountDetails;
    }
}
