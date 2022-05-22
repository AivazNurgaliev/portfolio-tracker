package com.ourproject.portfoliotracker.data.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table(name = "stock", schema = "public", catalog = "d1nbjmf3706qof")
public class StockEntity {
    @Column(name = "portfolio_id", nullable = false)
    private int portfolioId;
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "stock_id", nullable = false)
    private int stockId;
    @Column(name = "ticker", unique = true, nullable = false, length = 5)
    private String ticker;
    @Column(name = "amount", nullable = false)
    private int amount;
    @ManyToOne
    @JoinColumn(name = "portfolio_fk", referencedColumnName = "portfolio_id", nullable = false)
    @JsonIgnore
    private PortfolioEntity portfolioByPortfolioId;

    public int getPortfolioId() {
        return portfolioId;
    }

    public void setPortfolioId(int portfolioId) {
        this.portfolioId = portfolioId;
    }

    public int getStockId() {
        return stockId;
    }

    public void setStockId(int stockId) {
        this.stockId = stockId;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StockEntity that = (StockEntity) o;

        if (portfolioId != that.portfolioId) return false;
        if (stockId != that.stockId) return false;
        if (amount != that.amount) return false;
        if (ticker != null ? !ticker.equals(that.ticker) : that.ticker != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = portfolioId;
        result = 31 * result + stockId;
        result = 31 * result + (ticker != null ? ticker.hashCode() : 0);
        result = 31 * result + amount;
        return result;
    }

    public PortfolioEntity getPortfolioByPortfolioId() {
        return portfolioByPortfolioId;
    }

    public void setPortfolioByPortfolioId(PortfolioEntity portfolioByPortfolioId) {
        this.portfolioByPortfolioId = portfolioByPortfolioId;
    }
}
