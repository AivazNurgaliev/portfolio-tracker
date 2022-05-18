package com.ourproject.portfoliotracker.data.model.dto;

import com.ourproject.portfoliotracker.data.model.AccountDetailsEntity;

import java.sql.Date;
import java.sql.Timestamp;

public class AccountDetailsDTO {

    private int accountId;
    private Date lastLoginDate;
    private Timestamp subscriptionStartDate;
    private Timestamp subscriptionEndDate;
    private String showCurrency1;
    private String showCurrency2;

    //Transforming AccountDetails object to AccountDetailsDTO object
    public static AccountDetailsDTO from(AccountDetailsEntity accountDetails) {
        AccountDetailsDTO accountDetailsDTO = new AccountDetailsDTO();
        accountDetailsDTO.setAccountId(accountDetails.getAccountId());
        accountDetailsDTO.setLastLoginDate(accountDetails.getLastLoginDate());
        accountDetailsDTO.setSubscriptionStartDate(accountDetails.getSubscriptionStartDate());
        accountDetailsDTO.setSubscriptionEndDate(accountDetails.getSubscriptionEndDate());
        accountDetailsDTO.setShowCurrency1(accountDetails.getShowCurrency1());
        accountDetailsDTO.setShowCurrency2(accountDetails.getShowCurrency2());
        return accountDetailsDTO;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public Date getLastLoginDate() {
        return lastLoginDate;
    }

    public void setLastLoginDate(Date lastLoginDate) {
        this.lastLoginDate = lastLoginDate;
    }

    public Timestamp getSubscriptionStartDate() {
        return subscriptionStartDate;
    }

    public void setSubscriptionStartDate(Timestamp subscriptionStartDate) {
        this.subscriptionStartDate = subscriptionStartDate;
    }

    public Timestamp getSubscriptionEndDate() {
        return subscriptionEndDate;
    }

    public void setSubscriptionEndDate(Timestamp subscriptionEndDate) {
        this.subscriptionEndDate = subscriptionEndDate;
    }

    public String getShowCurrency1() {
        return showCurrency1;
    }

    public void setShowCurrency1(String showCurrency1) {
        this.showCurrency1 = showCurrency1;
    }

    public String getShowCurrency2() {
        return showCurrency2;
    }

    public void setShowCurrency2(String showCurrency2) {
        this.showCurrency2 = showCurrency2;
    }
}
