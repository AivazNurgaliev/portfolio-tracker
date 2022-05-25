package com.ourproject.portfoliotracker.data.accountDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ourproject.portfoliotracker.data.account.AccountEntity;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;

// FIXME: 18.05.2022 change data types
@Entity
@Table(name = "account_details", schema = "public", catalog = "d1nbjmf3706qof")
public class AccountDetailsEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "account_id", nullable = false)
    private int accountId;
    @Column(name = "last_login_date", nullable = false)
    private Date lastLoginDate;
    @Column(name = "subscription_start_date", nullable = true)
    private Timestamp subscriptionStartDate;
    @Column(name = "subscription_end_date", nullable = true)
    private Timestamp subscriptionEndDate;
    @Column(name = "show_currency1", nullable = true, length = 2, columnDefinition = "bpchar")
    private String showCurrency1;
    @Column(name = "show_currency2", nullable = true, length = 2, columnDefinition = "bpchar")
    private String showCurrency2;
    @OneToOne
    @JoinColumn(name = "account_id", referencedColumnName = "account_id", nullable = false)
    @JsonIgnore
    private AccountEntity accountByAccountId;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AccountDetailsEntity that = (AccountDetailsEntity) o;

        if (accountId != that.accountId) return false;
        if (lastLoginDate != null ? !lastLoginDate.equals(that.lastLoginDate) : that.lastLoginDate != null)
            return false;
        if (subscriptionStartDate != null ? !subscriptionStartDate.equals(that.subscriptionStartDate) : that.subscriptionStartDate != null)
            return false;
        if (subscriptionEndDate != null ? !subscriptionEndDate.equals(that.subscriptionEndDate) : that.subscriptionEndDate != null)
            return false;
        if (showCurrency1 != null ? !showCurrency1.equals(that.showCurrency1) : that.showCurrency1 != null)
            return false;
        if (showCurrency2 != null ? !showCurrency2.equals(that.showCurrency2) : that.showCurrency2 != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = accountId;
        result = 31 * result + (lastLoginDate != null ? lastLoginDate.hashCode() : 0);
        result = 31 * result + (subscriptionStartDate != null ? subscriptionStartDate.hashCode() : 0);
        result = 31 * result + (subscriptionEndDate != null ? subscriptionEndDate.hashCode() : 0);
        result = 31 * result + (showCurrency1 != null ? showCurrency1.hashCode() : 0);
        result = 31 * result + (showCurrency2 != null ? showCurrency2.hashCode() : 0);
        return result;
    }

    public AccountEntity getAccountByAccountId() {
        return accountByAccountId;
    }

    public void setAccountByAccountId(AccountEntity accountByAccountId) {
        this.accountByAccountId = accountByAccountId;
    }
}
