package app;

import controller.AppController;
import controller.Option;
import exceptions.ElementNotFoundException;
import db.FinancialItemDao;
import ui.UIService;

public class App {

    private static final String APP_NAME = "Home Bugdet v.1";

    public static void main(String[] args) {
        System.out.println("Witamy w aplikacji "+APP_NAME);
        UIService uiService = new UIService();
        FinancialItemDao financialItemDao = new FinancialItemDao();
        AppController controller = new AppController(uiService,financialItemDao);

        try {
            financialItemDao.getFinancialItem((long) 3,"Wydatek");
        } catch (ElementNotFoundException e) {
            e.printStackTrace();
        }

        Option option = null;
        do {
            controller.showOptions();
            System.out.println("Wpisz nr akcji, ktora chcesz wykonac:");
            option = controller.chooseOption();
        } while(option != Option.EXIT);

        financialItemDao.close();
        uiService.closeScanner();
        System.out.println("Dziękujemy za korzystanie z programu");
    }
}
