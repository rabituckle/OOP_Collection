package utilities;

import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import ui.*;
import utilities.player.*;

public abstract class ControlGame extends JPanel {
	
	protected JButton btnPlay = new JButton("Play");
	private int numPlayers;
	private int numHuman;
	private int numBots;
	private int mode;
	private String namePlayer[];
	protected int maxCards;
	protected int turn = 0;
	protected int show = 0;
	protected GraphicalMode ui = new GraphicalMode();
	protected ArrayList<Player> player;
    protected ArrayList<Card> chosenCard = new ArrayList<Card>();
    protected ArrayList<Card> previousCard = new ArrayList<Card>();
    
    public ControlGame(int mode, int numHuman, int numBots) {
    	this.numHuman = numHuman;
    	this.numBots = numBots;
    	this.numPlayers = numHuman + numBots;
    	this.mode = mode;
    	namePlayer = new String[numPlayers];
    }
	
    public void initialize() {
    	for(int i = 0; i < numHuman; i++) {
        	player.add(new Human());
        }
        for(int i = 0; i < numBots; i++) {
        	player.add(new Bot());
        }
    	// Initialize for cardState
		for(int i = 0; i < numHuman; i++) {
			for(int j = 0; j < maxCards; j++) {
				player.get(i).getCardState().add(0);
			}
		}
    }
    
	public void getPlayerCards() {
		Deck deckObj = new Deck();
        ArrayList<Card> deck = deckObj.createDeck();
        deck = deckObj.shuffleDeck(deck);
        player = deckObj.dealer(deck, numPlayers, maxCards);
	}
	
	public void deleteChosenCards(){
		int i = 0;
		while(i < player.get(turn).getNumCards()) {
			if(player.get(turn).getCardState().get(i) == 1) {
				player.get(turn).getCardState().remove(i);
				player.get(turn).removeCard(i);
			}
			else {
				i++;
			}
		}
		chosenCard.clear();
	}
	
	public Player getPlayer() {
		return player.get(turn);
	}
	
	public int getNumPlayers() {
		return numPlayers;
	}
	
	public int getTurn() {
		return turn;
	}
	
	public void setTurn(int turn) {
		this.turn = turn;
	}
	
	public void drawCardsVertical(ControlGame control, int x) {
		if(mode == 0) {
			ui.drawCardsVertical(control, x);
		}
	}
	
	public void drawCardsHorizontal(ControlGame control, int y) {
		if(mode == 0) {
			ui.drawCardsHorizontal(control, y);
		}
	}
	
	public void drawChosenCards(ArrayList<Card> chosenCard, ControlGame control) {
		if(mode == 0) {
			ui.drawChosenCards(chosenCard, control);
		}
	}
	
	public void drawHidingStateVertical(ControlGame control, int x) {
		if(mode == 0) {
			ui.drawHidingStateVertical(control, x);
		}
	}
	
	public void drawHidingStateHorizontal(ControlGame control, int y) {
		if(mode ==0) {
			ui.drawHidingStateHorizontal(control, y);
		}
	}
	
	public void drawHidingState(ControlGame control, int x, int y) {
		if(mode == 0) {
			ui.drawHidingState(control, x, y);
		}
	}
	
	public void drawStatePlaying(ControlGame control, int x, int y){
		if(mode == 0) {
			ui.drawStatePlaying(control, x, y);
		}
	}
	
	public void getPlayerName() {
		for(int i = 1; i <= numHuman; i++) {
    		String name = JOptionPane.showInputDialog("Enter your name of player " + i);
   		 	if(name == null || name.equals("")) {
   		 		name = "Player " + i;
   		 		namePlayer[i - 1] = name;
   		 	}
   		 	player.get(i-1).setName(name);
   		 	namePlayer[i - 1] = name;
    	}
		
		for(int i = numHuman + 1; i <= numPlayers; i++) {
			String name = "Bot " + (i - numHuman);
			namePlayer[i - 1] = name;
        	player.get(i-1).setName(name);
        }
		//shufflePlayer();
		for(int i = 0; i < numPlayers; i++) {
			System.out.println(namePlayer[i]);
		}
	}
	
	public void shufflePlayer() {
		Collections.shuffle(player);
	}
	
	public String getName(int i) {
		return namePlayer[i];
	}
	
	public abstract MouseAdapter handleInput();
	public abstract JPanel controlPanel();
	public abstract void newGame();
	public abstract void checkWin();
}