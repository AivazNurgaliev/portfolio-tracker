package com.ourproject.portfoliotracker.dtos;

import com.ourproject.portfoliotracker.entities.AccountEntity;

import java.sql.Date;
import java.time.LocalDate;

public class AccountDRO {

    private String userName;
    private String email;
    private String pass;
    private String showCurrency1;
    private String showCurrency2;


    public AccountEntity toAccountEntity() {
        AccountEntity account = new AccountEntity();
        account.setUserName(userName);
        account.setEmail(email);
        account.setPass(pass);
        account.setRegistrationDate(Date.valueOf(LocalDate.now()));
        return account;
    }


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

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
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
