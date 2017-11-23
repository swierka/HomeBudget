package model;

import java.time.LocalDate;

public class FinancialItem {
    private long id;
    private String type;
    private String description;
    private double amount;
    private LocalDate transactionDate;

    public FinancialItem(long id, String type, String description, double amount, LocalDate date) {
        this.id = id;
        this.type = type;
        this.description = description;
        this.amount = amount;
        this.transactionDate = date;
    }

    public FinancialItem(String type, String description, double amount, LocalDate date) {
        this.type = type;
        this.description = description;
        this.amount = amount;
        this.transactionDate = date;
    }

    public FinancialItem(){}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public LocalDate getDate() {
        return transactionDate;
    }

    public void setDate(LocalDate date) {
        this.transactionDate = date;
    }

    @Override
    public String toString() {
        return  "ID: " + id + ", typ: " + type + ", opis: " + description + ", kwota: " + amount + ", data transakcji: " + transactionDate;
    }
}
