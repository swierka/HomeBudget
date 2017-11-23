package model;

import db.FinancialItemDao;
import exceptions.ElementNotFoundException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static controller.AppController.EXPENSE;

public class Expense extends FinancialItem {

    public Expense(long id, String type, String description, double amount, LocalDate date) {
        super(id, type, description, amount, date);
    }

    public Expense(String type, String description, double amount, LocalDate date) {
        super(type, description, amount, date);
    }

    public Expense() {
    }

    public List<Expense> getAllExpenses() throws ElementNotFoundException {
        List<Expense> allItems = new ArrayList<>();
        FinancialItemDao financialItemDao = new FinancialItemDao();
        int itemsCount = financialItemDao.countItems(EXPENSE);
        Expense expense = null;
        FinancialItem financialItem;

        if (itemsCount == 0) {
            System.out.println("Brak wydatków w bazie");
        } else {
            for (long i = 1; i <= itemsCount; i++) {
                financialItem = financialItemDao.getFinancialItem(i, EXPENSE);
                if (financialItem instanceof Expense) {
                    expense = (Expense) financialItem;
                    allItems.add(expense);
                }
            }
        }
            return allItems;
    }

    public double getTotalAmountExpenses(){
        double totalAmount = 0;

        try {
            List<Expense> expenses = getAllExpenses();
            if (!expenses.equals(null)) {
                for (Expense expense : expenses) {
                    totalAmount += expense.getAmount();
                }
            } else {totalAmount =0;}
        } catch (ElementNotFoundException e) {
            e.printStackTrace();
        }
        return totalAmount;
    }

        @Override
        public String toString () {
            return "[ID:"+super.getId()+"] "+super.getDescription()+": "+super.getAmount() + " PLN z dnia "+super.getDate();
        }
    }
