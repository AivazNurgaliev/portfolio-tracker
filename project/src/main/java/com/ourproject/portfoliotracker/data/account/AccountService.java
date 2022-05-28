package com.ourproject.portfoliotracker.data.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Date;
import java.time.LocalDate;

@Service
public class AccountService {

    private final AccountRepository accountRepository;

    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public AccountService(AccountRepository accountRepository,
                          BCryptPasswordEncoder passwordEncoder) {
        this.accountRepository = accountRepository;
        this.passwordEncoder = passwordEncoder;
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
        System.out.println("User " + account.toString() + " not found");
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
        String encodedPass = passwordEncoder.encode(newPassword);

        while(passwordEncoder.upgradeEncoding(encodedPass)) {
            encodedPass = passwordEncoder.encode(encodedPass);
        }
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
