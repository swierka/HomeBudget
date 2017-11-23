package ui;

import controller.AppController;
import controller.Option;
import model.Expense;
import model.FinancialItem;
import model.Income;

import java.time.LocalDate;
import java.util.Scanner;

public class UIService {
    private FinancialItem financialItem;
    private final Scanner sc = new Scanner(System.in);
    private AppController appController;


    public long getLong() {
        long value = sc.nextLong();
        sc.nextLine();
        return value;
    }

    public Option getOption() {
        Option[] values = Option.values();
        return values[(int)getLong()];
    }

    public void printOptions() {
        for (Option option : Option.values()) {
            System.out.println(option);
        }
    }

    public FinancialItem createItem(String type) {
        System.out.println("Podaj nazwe/opis: ");
        String description = sc.nextLine();
        System.out.println("Podaj kwotę: ");
        Double amount = sc.nextDouble();
        sc.nextLine();
        System.out.println("Podaj datę (RR-MM-DD):");
        String insertedDate = sc.nextLine();
        String[] insertedDateTab = insertedDate.split("-");
        int year = Integer.parseInt(insertedDateTab[0]);
        int month = Integer.parseInt(insertedDateTab[1]);
        int day = Integer.parseInt(insertedDateTab[2]);

        if (type == AppController.EXPENSE) {
            FinancialItem item = new Expense(type, description, amount, LocalDate.of(year, month, day));
            return item;
        } else {FinancialItem item = new Income(type, description, amount, LocalDate.of(year, month, day));
            return item;}
    }

    public void closeScanner (){
        sc.close();
    }
}
