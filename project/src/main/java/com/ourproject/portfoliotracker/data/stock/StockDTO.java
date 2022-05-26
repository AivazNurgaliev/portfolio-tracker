package com.ourproject.portfoliotracker.data.stock;

public class StockDTO {
    private String ticker;
    private int amount;

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
}
