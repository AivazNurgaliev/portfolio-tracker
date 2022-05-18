package com.ourproject.portfoliotracker.data.service;

import com.ourproject.portfoliotracker.data.model.AccountDetailsEntity;
import com.ourproject.portfoliotracker.data.repository.AccountDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class AccountDetailsService {

    private final AccountDetailsRepository accountDetailsRepository;

    @Autowired
    public AccountDetailsService(AccountDetailsRepository accountDetailsRepository) {
        this.accountDetailsRepository = accountDetailsRepository;
    }

    //Creating an AccountDetails object
    //Returning persisted object
    public AccountDetailsEntity addAccountDetails(AccountDetailsEntity account) {
        return accountDetailsRepository.save(account);
    }

    //Returning a List of all AccountDetails
    public List<AccountDetailsEntity> getAllAccountsDetails() {
        return StreamSupport
                .stream(accountDetailsRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    //Getting an AccountDetail by id
    public AccountDetailsEntity getAccountDetail(Integer id) {
        return accountDetailsRepository.findById(id).orElseThrow(() ->
                new RuntimeException("Error! Account Details not found"));
    }

    //Deleting an AccountDetail by id
    public AccountDetailsEntity deleteAccountDetails(Integer id) {
        //Validating, if the object does not exist it will throw an error
        AccountDetailsEntity account = getAccountDetail(id);
        accountDetailsRepository.delete(account);
        return account;
    }

    @Transactional
    public AccountDetailsEntity editAccountDetails(Integer id, AccountDetailsEntity accountObj) {
        AccountDetailsEntity account = getAccountDetail(id);
        account.setLastLoginDate(accountObj.getLastLoginDate());
        account.setSubscriptionStartDate(accountObj.getSubscriptionStartDate());
        account.setSubscriptionEndDate(accountObj.getSubscriptionEndDate());
        account.setShowCurrency1(accountObj.getShowCurrency1());
        account.setShowCurrency2(accountObj.getShowCurrency2());
        return account;
    }
}
