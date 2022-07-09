package com.ourproject.portfoliotracker.dtos;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class AccountDSO {

    private String userName;
    private String email;
    private Date registrationDate;
    private Date lastLoginDate;
    private LocalDateTime subscriptionStartDate;
    private LocalDateTime subscriptionEndDate;
    private String showCurrency1;
    private String showCurrency2;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }

    public Date getLastLoginDate() {
        return lastLoginDate;
    }

    public void setLastLoginDate(Date lastLoginDate) {
        this.lastLoginDate = lastLoginDate;
    }

    public LocalDateTime getSubscriptionStartDate() {
        return subscriptionStartDate;
    }

    public void setSubscriptionStartDate(LocalDateTime subscriptionStartDate) {
        this.subscriptionStartDate = subscriptionStartDate;
    }

    public LocalDateTime getSubscriptionEndDate() {
        return subscriptionEndDate;
    }

    public void setSubscriptionEndDate(LocalDateTime subscriptionEndDate) {
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
