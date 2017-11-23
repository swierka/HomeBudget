package model;

import db.FinancialItemDao;
import exceptions.ElementNotFoundException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static controller.AppController.INCOME;

public class Income extends FinancialItem {

    public Income(long id, String type, String description, double amount, LocalDate date) {
        super(id, type, description, amount, date);
    }

    public Income(String type, String description, double amount, LocalDate date) {
        super(type, description, amount, date);
    }

    public Income() {
    }

    public List<Income> getAllIncomePositions() throws ElementNotFoundException {
        List<Income> allItems = new ArrayList<>();
        FinancialItemDao financialItemDao = new FinancialItemDao();
        int itemsCount = financialItemDao.countItems(INCOME);
        Income income = null;
        FinancialItem financialItem;

        if (itemsCount == 0) {
            System.out.println("Brak przychodow w bazie");
        } else {
            for (long i = 1; i <= itemsCount; i++) {
                financialItem = financialItemDao.getFinancialItem(i, INCOME);
                if (financialItem instanceof Income) {
                    income = (Income) financialItem;
                    allItems.add(income);
                }
            }
        }
        return allItems;
    }

    public double getTotalAmountIncome (){
        double totalAmount = 0;

        try {
            List<Income> incomeList = getAllIncomePositions();
            if (!incomeList.equals(null)) {
                for (Income income : incomeList) {
                    totalAmount += income.getAmount();
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
