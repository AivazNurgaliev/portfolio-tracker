package com.ourproject.portfoliotracker.data.model;

import javax.persistence.*;

@Entity
@Table(name = "portfolio", schema = "public", catalog = "d1nbjmf3706qof")
public class PortfolioEntity {
    @Column(name = "account_id", nullable = false)
    private int accountId;
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "portfolio_id", nullable = false)
    private int portfolioId;
    @Column(name = "name", unique = true, nullable = false, length = 64)
    private String name;
    @Column(name = "description", nullable = true, length = 256)
    private String description;
    @ManyToOne
    @JoinColumn(name = "account_id", referencedColumnName = "account_id", nullable = false, insertable = false, updatable = false)
    private AccountEntity accountByAccountId;

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public int getPortfolioId() {
        return portfolioId;
    }

    public void setPortfolioId(int portfolioId) {
        this.portfolioId = portfolioId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PortfolioEntity that = (PortfolioEntity) o;

        if (accountId != that.accountId) return false;
        if (portfolioId != that.portfolioId) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = accountId;
        result = 31 * result + portfolioId;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        return result;
    }

    public AccountEntity getAccountByAccountId() {
        return accountByAccountId;
    }

    public void setAccountByAccountId(AccountEntity accountByAccountId) {
        this.accountByAccountId = accountByAccountId;
    }
}
