package ru.binarysimple.billng.model;

public enum OperationType {
    WITHDRAW("Withdrawal"),
    PAYMENT("Payment"),
    REFUND("Refund Payment"),
    DEPOSIT("Deposit");

    private final String typeName;

    OperationType(String typeName) {
        this.typeName = typeName;
    }


    @Override
    public String toString() {
        return typeName;
    }
}
