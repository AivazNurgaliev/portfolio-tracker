package com.ourproject.portfoliotracker.data.accountDetails;

import com.ourproject.portfoliotracker.data.account.AccountDRO;
import com.ourproject.portfoliotracker.data.account.AccountEntity;
import com.ourproject.portfoliotracker.data.account.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Date;
import java.sql.Timestamp;

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

    //Creating an AccountDetails object
    //Returning persisted object
    public AccountDetailsEntity addAccountDetails(AccountDRO accountDRO) {
        AccountDetailsEntity accountDetails = new AccountDetailsEntity();
        accountDetails.setAccountId(accountRepository.findByUserName(accountDRO.getUserName()).getAccountId());
        accountDetails.setShowCurrency1(accountDRO.getShowCurrency1());
        accountDetails.setShowCurrency2(accountDRO.getShowCurrency2());
        return accountDetailsRepository.save(accountDetails);
    }

    //Returning a List of all AccountDetails
/*    public List<AccountDetailsEntity> getAllAccountsDetails() {
        return StreamSupport
                .stream(accountDetailsRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }*/

    //Getting an AccountDetail by id
/*    public AccountDetailsEntity getAccountDetails(Integer id) {
        return accountDetailsRepository.findById(id).orElseThrow(() ->
                new RuntimeException("Error! Account Details not found"));
    }

    //Deleting an AccountDetail by id
    public AccountDetailsEntity deleteAccountDetails(Integer id) {
        //Validating, if the object does not exist it will throw an error
        AccountDetailsEntity account = getAccountDetails(id);
        accountDetailsRepository.delete(account);
        return account;
    }*/

/*    @Transactional
    public AccountDetailsEntity editAccountDetails(Integer id, AccountDetailsEntity accountObj) {
        AccountDetailsEntity account = getAccountDetails(id);
        account.setLastLoginDate(accountObj.getLastLoginDate());
        account.setSubscriptionStartDate(accountObj.getSubscriptionStartDate());
        account.setSubscriptionEndDate(accountObj.getSubscriptionEndDate());
        account.setShowCurrency1(accountObj.getShowCurrency1());
        account.setShowCurrency2(accountObj.getShowCurrency2());
        return account;
    }*/
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
                                                     Timestamp startDate,
                                                     Timestamp endDate) {
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
