package com.ourproject.portfoliotracker.utils;

public enum DealType {

    BUY("BUY"),
    SELL("SELL"),
    DIV_COUP("DIV_COUP");

    private final String operation;

    DealType(String operation) {
        this.operation = operation;
    }

    public static DealType fromString(String operation) {

        if (operation.equals("BUY")) return BUY;
        if (operation.equals("SELL")) return SELL;
        if (operation.equals("DIV_COUP")) return DIV_COUP;
        throw new UnsupportedOperationException(
                "The operation " + operation + " is not supported!"
        );
    }

    public String getOperation() {
        return operation;
    }
}
