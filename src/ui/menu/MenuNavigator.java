package ui.menu;

public class MenuNavigator {
	
	public static final int MAIN_MENU_SCREEN = 0;
    public static final int GAME_SELECTION_SCREEN = 1;
    public static final int PLAYER_COUNT_SCREEN = 2;
    public static final int PLAYER_NAMES_SCREEN = 3;
    public static final int BOT_SELECTION_SCREEN = 4;
    private GameConfiguration gameConfig;
    
    public MenuNavigator(GameConfiguration gameConfig) {
    	this.gameConfig = gameConfig;
    }
    
    public void navigateTo(int screenType, MenuScreen currentScreen) {
        // Close current screen if any
        if (currentScreen != null) {
            currentScreen.close();
        }
        
        // Navigate to new screen
        switch (screenType) {
            case MAIN_MENU_SCREEN:
                currentScreen = new MainMenu(gameConfig, this);
                break;
            case GAME_SELECTION_SCREEN:
                currentScreen = new GameSelectionScreen(gameConfig, this);
                break;
            case PLAYER_COUNT_SCREEN:
                currentScreen = new NumPlayerSelectionScreen(gameConfig, this);
                break;
            case PLAYER_NAMES_SCREEN:
                currentScreen = new PlayerNameScreen(gameConfig, this);
                break;
            case BOT_SELECTION_SCREEN:
                currentScreen = new BotsSelectionScreen(gameConfig, this);
                break;
            default:
                throw new IllegalArgumentException("Invalid screen type: " + screenType);
        }
        
        // Show the new screen
        currentScreen.show();
    }
}
