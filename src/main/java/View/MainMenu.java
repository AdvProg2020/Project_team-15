package View;

import Control.MainManager;
import Control.Manager;
import View.CustomerMenus.ConsoleCommand;

public class MainMenu extends Menu {

    protected final MainManager mainManager = (MainManager) manager;
    public MainMenu(Manager manager) {
        super(manager);
        execute();
    }

    private void execute(){
        String input;
        while((input = scanner.nextLine().trim()).equalsIgnoreCase("exit")) {
            if(ConsoleCommand.EXIT.getStringMatcher(input).find()){
                return;
            }
        }
    }

    public void mainMenu() {

    }

    private void openLoginMenu() {

    }

    private void openProductsMenu() {

    }

    private void openAuctionsMenu() {

    }
}
