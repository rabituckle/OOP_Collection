package implementation;

import ui.menu.MainMenu;
import ui.menu.GameConfiguration;
import ui.menu.MenuNavigator;
import ui.menu.MenuScreen;

public class Main {
	
	public static void main(String[] args) {
		GameConfiguration gameConfig = new GameConfiguration();
        MenuNavigator navigator = new MenuNavigator();
        MenuScreen menuSystem = new MainMenu(gameConfig, navigator);
        
        // Start with the main menu
        navigator.navigateTo(0, menuSystem, gameConfig);
	}
}
