package com.ourproject.portfoliotracker.data.dealHistory.utils;

public enum DealType {
    BUY("BUY"),
    SELL("SELL"),
    DIV_COUP("DIV_COUP");

    private final String operation;

    DealType(String operation) {
        this.operation = operation;
    }

    public static DealType dealValidation(String operation) {
        if (operation == "BUY") return BUY;
        if (operation == "SELL") return SELL;
        if (operation == "DIV_COUP") return DIV_COUP;
        throw new UnsupportedOperationException(
                "The operation " + operation + " is not supported!"
        );
    }

    public String getOperation() {
        return operation;
    }
}
