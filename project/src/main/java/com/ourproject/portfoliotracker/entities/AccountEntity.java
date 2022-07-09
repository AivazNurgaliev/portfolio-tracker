package com.ourproject.portfoliotracker.entities;

import javax.persistence.*;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "account"/*, schema = "public", catalog = "d1nbjmf3706qof"*/)
public class AccountEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "account_id", nullable = false)
    private int accountId;

    @Column(name = "user_name", unique = true, nullable = false, length = 64)
    private String userName;

    @Column(name = "pass", nullable = false, length = 64)
    private String pass;

    @Column(name = "email", unique = true, nullable = false, length = 255)
    private String email;

    @Column(name = "registration_date", nullable = false)
    private Date registrationDate;

    @OneToOne(
            mappedBy = "accountByAccountId",
            cascade = CascadeType.ALL
    )
    private AccountDetailsEntity accountDetailsByAccountId;

    @OneToMany(
            mappedBy = "accountByAccountId",
            cascade = CascadeType.ALL
    )
    private List<DealHistoryEntity> dealHistoriesByAccountId = new ArrayList<>();

    @OneToMany(
            mappedBy = "accountByAccountId",
            cascade = CascadeType.ALL
    )
    private List<PortfolioEntity> portfoliosByAccountId = new ArrayList<>();



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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AccountEntity that = (AccountEntity) o;

        if (accountId != that.accountId) return false;
        if (userName != null ? !userName.equals(that.userName) : that.userName != null) return false;
        if (pass != null ? !pass.equals(that.pass) : that.pass != null) return false;
        if (email != null ? !email.equals(that.email) : that.email != null) return false;
        if (registrationDate != null ? !registrationDate.equals(that.registrationDate) : that.registrationDate != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = accountId;
        result = 31 * result + (userName != null ? userName.hashCode() : 0);
        result = 31 * result + (pass != null ? pass.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (registrationDate != null ? registrationDate.hashCode() : 0);
        return result;
    }

    public AccountDetailsEntity getAccountDetailsByAccountId() {
        return accountDetailsByAccountId;
    }

    public void setAccountDetailsByAccountId(AccountDetailsEntity accountDetailsByAccountId) {
        this.accountDetailsByAccountId = accountDetailsByAccountId;
    }

    public List<DealHistoryEntity> getDealHistoriesByAccountId() {
        return dealHistoriesByAccountId;
    }

    public void setDealHistoriesByAccountId(List<DealHistoryEntity> dealHistoriesByAccountId) {
        this.dealHistoriesByAccountId = dealHistoriesByAccountId;
    }

    public List<PortfolioEntity> getPortfoliosByAccountId() {
        return portfoliosByAccountId;
    }

    public void setPortfoliosByAccountId(List<PortfolioEntity> portfoliosByAccountId) {
        this.portfoliosByAccountId = portfoliosByAccountId;
    }

    //Methods for adding and removing Entities
    public void addDealHistory(DealHistoryEntity dealHistory) {
        dealHistoriesByAccountId.add(dealHistory);
    }

    public void removeDealHistory(DealHistoryEntity dealHistory) {
        dealHistoriesByAccountId.remove(dealHistory);
    }

    public void addPortfolio(PortfolioEntity portfolio) {
        portfoliosByAccountId.add(portfolio);
    }

    public void removePortfolio(PortfolioEntity portfolio) {
        portfoliosByAccountId.remove(portfolio);
    }
}
