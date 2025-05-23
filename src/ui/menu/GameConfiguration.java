package ui.menu;

import java.util.ArrayList;

import game.BaCay;
import game.TienLen;
import game.TienLenMienBac;
import game.TienLenMienNam;

public class GameConfiguration {
	
	 private int modeGUI; // 0 for graphical, 1 for basic
	 private int typeGame; // 0 for BA CAY, 1 for TLMN, 2 for TLMB
	 private int numPlayers;
	 private int numBots;
	 private int minBots, maxBots;
	 private ArrayList<String> playerNames = new ArrayList<>();

	private void calculateBotLimits() {
		 if (numPlayers == 1) {
	            minBots = 1; // At least one bot for single player
	     }
		 else {
	            minBots = 0;
	     }
		 maxBots = 4 - numPlayers;
	}
	
	public boolean increaseBots() {
        if (numBots < maxBots) {
            numBots++;
            return true;
        }
        return false;
    }
	
	public boolean decreaseBots() {
        if (numBots > minBots) {
            numBots--;
            return true;
        }
        return false;
    }
	
	public int getModeGUI() {
        return modeGUI;
    }

    public void setModeGUI(int modeGUI) {
        this.modeGUI = modeGUI;
    }

    public int getTypeGame() {
        return typeGame;
    }

    public void setTypeGame(int typeGame) {
        this.typeGame = typeGame;
    }

    public int getNumPlayers() {
        return numPlayers;
    }
    
    public void setNumPlayers(int numPlayers) {
        this.numPlayers = numPlayers;
        calculateBotLimits();
        numBots = minBots;
    }
    
    public int getNumBots() {
        return numBots;
    }

    public void setNumBots(int numBots) {
        if (numBots >= minBots && numBots <= maxBots) {
            this.numBots = numBots;
        }
    }
    
    public void setPlayerNames(ArrayList<String> names) {
        // Clear existing names
        playerNames.clear();
        // Add all new names
        playerNames.addAll(names);
    }
    
    public void startGame() {
    	if(typeGame == 0) {
    		BaCay game = new BaCay(modeGUI, numPlayers, numBots, playerNames);
    		game.gameStart();
    	}
    	else if(typeGame == 1) {
    		TienLen game = new TienLenMienNam(modeGUI, numPlayers, numBots, playerNames);
    		game.gameStart();
    	}
    	else if(typeGame == 2) {
    		TienLen game = new TienLenMienBac(modeGUI, numPlayers, numBots, playerNames);
    		game.gameStart();
    	}
    }
    
    public void startMenuSystem() {
    	MenuNavigator navigator = new MenuNavigator(this);
        MenuScreen menuSystem = new MainMenu(this, navigator);
        
        // Start with the main menu
        navigator.navigateTo(0, menuSystem);
    }
}
