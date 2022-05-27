package com.ourproject.portfoliotracker.data.account;

import com.ourproject.portfoliotracker.data.dealHistory.DealHistoryEntity;
import com.ourproject.portfoliotracker.data.portfolio.PortfolioEntity;
import com.ourproject.portfoliotracker.data.accountDetails.AccountDetailsService;
import com.ourproject.portfoliotracker.data.dealHistory.DealHistoryService;
import com.ourproject.portfoliotracker.data.portfolio.PortfolioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class AccountService {

    private final AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Integer getUserId(String userName) {
        AccountEntity account = accountRepository.findByUserName(userName);
        if (account == null) {
            throw new UsernameNotFoundException("User " + userName + " not found");
        }
        return account.getAccountId();
    }
    //Creating an account object
    //Returning persisted object
/*
    public AccountEntity addAccount(AccountEntity account) {
        return accountRepository.save(account);
    }
*/
    public AccountEntity addAccount(AccountDRO accountDRO) {
        AccountEntity account = new AccountEntity();
        account.setUserName(accountDRO.getUserName());
        account.setEmail(accountDRO.getEmail());
        account.setPass(accountDRO.getPass());
        account.setRegistrationDate(Date.valueOf(LocalDate.now()));
        return accountRepository.save(account);
    }

    //Returning a List of all accounts
/*
    public List<AccountEntity> getAccounts() {
        return StreamSupport
                .stream(accountRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }
*/

    //Getting an Account by userName
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

    //Deleting an Account by id
    public AccountEntity deleteAccount(String userName) {
        //Validating, if the object does not exist it will throw an error
        AccountEntity account = accountRepository.findByUserName(userName);
        if (account == null) {
            throw new UsernameNotFoundException("User " + userName + " not found");
        }
        accountRepository.delete(account);
        return account;
    }

    //id - account to edit
    //accountObj - account that we use to edit object with certain id
    //get account entity
    //editAcc (accEnt a)
    //edit
/*
    public AccountEntity editAccount(String userName, AccountEntity accountObj) {
        AccountEntity account = accountRepository.findByUserName(userName);
        if (account == null) {
            throw new UsernameNotFoundException("User " + userName + " not found");
        }
        account.setUserName(accountObj.getUserName());
        account.setPass(accountObj.getPass());
        account.setEmail(accountObj.getEmail());
        account.setRegistrationDate(accountObj.getRegistrationDate());
        accountRepository.save(account);
        return account;
    }
*/

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
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String encodedPass = bCryptPasswordEncoder.encode(newPassword);

        while(bCryptPasswordEncoder.upgradeEncoding(encodedPass)) {
            encodedPass = bCryptPasswordEncoder.encode(encodedPass);
        }
        account.setPass(encodedPass);
        return account;
    }

/*    @Transactional
    public AccountEntity addDealHistory(String userName, DealHistoryEntity dealHistoryToAdd) {
        AccountEntity account = accountRepository.findByUserName(userName);
        if (account == null) {
            throw new UsernameNotFoundException("User " + userName + " not found");
        }
        Integer accountId = accountRepository.findByUserName(userName).getAccountId();
        DealHistoryEntity dealHistory = dealHistoryService.addDealHistory(dealHistoryToAdd);
        account.addDealHistory(dealHistory);
        return account;
    }*/
/*
    @Transactional
    public AccountEntity removeDealHistory(String userName, Integer dealId) {
        AccountEntity account = accountRepository.findByUserName(userName);
        DealHistoryEntity dealHistory = dealHistoryService.getDealHistory(dealId);
        account.removeDealHistory(dealHistory);
        return account;
    }
*/
/*
    @Transactional
    public AccountEntity addPortfolio(String userName, PortfolioEntity portfolioToAdd) {
        AccountEntity account = accountRepository.findByUserName(userName);
        if (account == null) {
            throw new UsernameNotFoundException("User " + userName + " not found");
        }
        PortfolioEntity portfolio = portfolioService.addPortfolio(portfolioToAdd);
        account.addPortfolio(portfolio);

        return account;
    }

    @Transactional
    public AccountEntity removePortfolio(String userName, String portfolioName) {
        AccountEntity account = accountRepository.findByUserName(userName);
        if (account == null) {
            throw new UsernameNotFoundException("User " + userName + " not found");
        }
        PortfolioEntity portfolio = portfolioService.deletePorfolio(, portfolioName);
        account.removePortfolio(portfolio);
        return account;
    }*/
}
