package com.ourproject.portfoliotracker.data.model.dto;

import com.ourproject.portfoliotracker.data.model.AccountEntity;
import com.ourproject.portfoliotracker.data.model.DealHistoryEntity;
import com.ourproject.portfoliotracker.data.model.PortfolioEntity;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class AccountDTO {

    private int accountId;
    private String userName;
    private String pass;
    private String email;
    private Date registrationDate;
// FIXME: 18.05.2022 one to one
// FIXME: 18.05.2022 надо добавить дилхисториДТО и портфолиоДТО а потом обрабатывать стримом
// FIXME: 18.05.2022 нужно создать классы ДТО чтобы участники бидиректионал связи знали о связующихся классах а не только АккоунтДТО знал о них
    private List<DealHistoryEntity> dealHistories = new ArrayList<>();
    private List<PortfolioEntity> portfolios = new ArrayList<>();

    public static AccountDTO from(AccountEntity account) {
        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setAccountId(account.getAccountId());
        accountDTO.setUserName(account.getUserName());
        accountDTO.setPass(account.getPass());
        accountDTO.setEmail(account.getEmail());
        accountDTO.setRegistrationDate(account.getRegistrationDate());
        accountDTO.setDealHistories(account.getDealHistoriesByAccountId());
        accountDTO.setPortfolios(account.getPortfoliosByAccountId());
        return accountDTO;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
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

    public List<DealHistoryEntity> getDealHistories() {
        return dealHistories;
    }

    public void setDealHistories(List<DealHistoryEntity> dealHistories) {
        this.dealHistories = dealHistories;
    }

    public List<PortfolioEntity> getPortfolios() {
        return portfolios;
    }

    public void setPortfolios(List<PortfolioEntity> portfolios) {
        this.portfolios = portfolios;
    }
}
