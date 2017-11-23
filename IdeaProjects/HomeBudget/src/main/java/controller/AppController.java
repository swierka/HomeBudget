package controller;

import exceptions.ElementNotFoundException;
import model.Expense;
import model.FinancialItem;
import db.FinancialItemDao;
import model.Income;
import ui.UIService;

import java.util.ArrayList;
import java.util.List;

public class AppController {
    public static final String EXPENSE = "Wydatek";
    public static final String INCOME = "Przychod";
    private UIService uiService;
    private FinancialItemDao financialItemDao;


    public AppController(UIService uiService, FinancialItemDao financialItemDao) {
        this.uiService = uiService;
        this.financialItemDao = financialItemDao;
    }

    public void showOptions() {
        uiService.printOptions();
    }

    public Option chooseOption() {
        Option option = uiService.getOption();
        switch (option) {
            case ADD_EXPENSE:
                addItemToDatabase(EXPENSE);
                break;
            case ADD_INCOME:
                addItemToDatabase(INCOME);
                break;
            case SHOW_ALL_EXPENSES:
                showAllExpenses();
                break;
            case SHOW_ALL_INCOME:
                showAllIncome();
                break;
            case GET_BALANCE:
                showTheBalance();
                break;
            case DELETE_ITEM:
                deleteItem();
                break;
        }
        return option;
    }

    private void showAllExpenses() {
        List<Expense> expenses = new ArrayList<>();
        Expense expense = new Expense();

        System.out.println("Wydatki:");
        try {
            expenses = expense.getAllExpenses();
        } catch (ElementNotFoundException e) {
            System.out.println("Brak rekordów do wyświetlenia");
        }

        if (expenses == null) {
            System.out.println("Brak rekordów do wyświetlenia");
        } else {
            for (Expense expense1 : expenses) {
                if (expense1 != null) {
                    System.out.println(expense1.toString());
                }
            }
        }
        System.out.println("Laczna kwota wydatkow to: " + expense.getTotalAmountExpenses() + "\n");
    }

    private void showAllIncome() {
        List<Income> incomeList = new ArrayList<>();
        Income income = new Income();

        System.out.println("Przychody:");
        try {
            incomeList = income.getAllIncomePositions();
        } catch (ElementNotFoundException e) {
            System.out.println("Brak rekordów do wyświetlenia");
        }

        if (incomeList == null) {
            System.out.println("Brak rekordów do wyświetlenia");
        } else {
            for (Income income1 : incomeList) {
                if (income1 != null) {
                    System.out.println(income1.toString());
                }
            }
        }
        System.out.println("Laczna kwota wydatkow to: " + income.getTotalAmountIncome() + "\n");
    }

    private void addItemToDatabase(String type) {
        FinancialItem item = uiService.createItem(type);
        financialItemDao.saveNewFinancialItem(item);
    }

    private void showTheBalance() {
        Expense expense = new Expense();
        Income income = new Income();
        Double sumExpenses = expense.getTotalAmountExpenses();
        Double sumIncome = income.getTotalAmountIncome();
        String balanceMessage;


        if (sumExpenses==0 && sumIncome==0) {
            balanceMessage = "Brak zarejestorwanych przychodow i wydatkow. Bilans wynosi: 0 PLN.\n";
        } else if (sumExpenses!=0 && sumIncome==0) {
            balanceMessage = "Brak zarejestorwanych przychodow. Bilans wynosi: " + sumExpenses + " PLN.\n";
        } else if (sumExpenses==0 && sumIncome!=0) {
            balanceMessage = "Brak zarejestorwanych wydatkow. Bilans wynosi: " + sumIncome + " PLN.\n";
        } else {
            balanceMessage = "Bilans wynosi: " + (sumIncome - sumExpenses) +
                    " PLN.\nLaczna kwota przychodow to: " + sumIncome + " PLN.\nLaczna kwota wydatkow to: " + sumExpenses + " PLN.\n";
        }
        System.out.println(balanceMessage);
    }

    private void deleteItem() {
        System.out.println("Podaj id pozycji, którą chcesz usunąć");
        long id = uiService.getLong();
        financialItemDao.deleteItem(id);
        System.out.println("Pozycja o id: "+id+" została usunięta z bazy.\n");
    }
}
