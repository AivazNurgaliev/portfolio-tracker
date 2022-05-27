package com.ourproject.portfoliotracker.data.dealHistory;

import com.fasterxml.jackson.annotation.JsonIgnore;
<<<<<<< HEAD:project/src/main/java/com/ourproject/portfoliotracker/data/model/DealHistoryEntity.java
import com.ourproject.portfoliotracker.data.model.utils.DealType;
=======
import com.ourproject.portfoliotracker.data.account.AccountEntity;
import com.ourproject.portfoliotracker.data.dealHistory.utils.DealType;
>>>>>>> origin/main:project/src/main/java/com/ourproject/portfoliotracker/data/dealHistory/DealHistoryEntity.java

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table( name = "deal_history",
        schema = "public",
        catalog = "d1nbjmf3706qof",
        uniqueConstraints = {
        @UniqueConstraint(columnNames = {
                "account_id", "ticker",
                "amount", "stock_price",
                "deal_date", "deal_type", "currency"
        })}
)
public class DealHistoryEntity {
    @Column(name = "account_id", nullable = false)
    private int accountId;
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "deal_id", nullable = false)
    private int dealId;
    @Column(name = "ticker", nullable = false, length = 5)
    private String ticker;
    @Column(name = "amount", nullable = false)
    private int amount;
    @Column(name = "stock_price", nullable = false)
    private int stockPrice;
    @Column(name = "deal_date", nullable = false)
    private Timestamp dealDate;
    @Enumerated(EnumType.STRING)
    @Column(name = "deal_type", nullable = false)
    private DealType dealType;
    @Column(name = "currency", nullable = false, length = 2, columnDefinition = "bpchar")
    private String currency;
    @ManyToOne
    @JoinColumn(name = "account_id", referencedColumnName = "account_id", nullable = false, insertable = false, updatable = false)
    @JsonIgnore
    private AccountEntity accountByAccountId;

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public int getDealId() {
        return dealId;
    }

    public void setDealId(int dealId) {
        this.dealId = dealId;
    }

    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getStockPrice() {
        return stockPrice;
    }

    public void setStockPrice(int stockPrice) {
        this.stockPrice = stockPrice;
    }

    public Timestamp getDealDate() {
        return dealDate;
    }

    public void setDealDate(Timestamp dealDate) {
        this.dealDate = dealDate;
    }

    public DealType getDealType() {
        return dealType;
    }

    public void setDealType(DealType dealType) {
        this.dealType = dealType;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DealHistoryEntity that = (DealHistoryEntity) o;

        if (accountId != that.accountId) return false;
        if (dealId != that.dealId) return false;
        if (amount != that.amount) return false;
        if (stockPrice != that.stockPrice) return false;
        if (ticker != null ? !ticker.equals(that.ticker) : that.ticker != null) return false;
        if (dealDate != null ? !dealDate.equals(that.dealDate) : that.dealDate != null) return false;
        if (dealType != null ? !dealType.equals(that.dealType) : that.dealType != null) return false;
        if (currency != null ? !currency.equals(that.currency) : that.currency != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = accountId;
        result = 31 * result + dealId;
        result = 31 * result + (ticker != null ? ticker.hashCode() : 0);
        result = 31 * result + amount;
        result = 31 * result + stockPrice;
        result = 31 * result + (dealDate != null ? dealDate.hashCode() : 0);
        result = 31 * result + (dealType != null ? dealType.hashCode() : 0);
        result = 31 * result + (currency != null ? currency.hashCode() : 0);
        return result;
    }


    public AccountEntity getAccountByAccountId() {
        return accountByAccountId;
    }

    public void setAccountByAccountId(AccountEntity accountByAccountId) {
        this.accountByAccountId = accountByAccountId;
    }
}
