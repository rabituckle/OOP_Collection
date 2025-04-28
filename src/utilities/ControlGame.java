package utilities;

import java.awt.event.MouseAdapter;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import ui.*;
import utilities.card.Card;
import utilities.card.CardPlayingCard;
import utilities.deck.Deck;
import utilities.deck.DeckPlayingCard;
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
    protected ArrayList<Card> botCards = new ArrayList<Card>();
    
    public ControlGame(int mode, int numHuman, int numBots) {
    	this.numHuman = numHuman;
    	this.numBots = numBots;
    	this.numPlayers = numHuman + numBots;
    	this.mode = mode;
    	namePlayer = new String[numPlayers];
    }
	
    public void initialize() {
    	// Initialize for cardState
		for(int i = 0; i < numHuman; i++) {
			for(int j = 0; j < maxCards; j++) {
				player.get(i).getCardState().add(0);
			}
		}
    }
    
	public void getPlayerCards() {
		Deck deckObj = new DeckPlayingCard();
		player = deckObj.dealer(numHuman, numBots, maxCards);
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
		int human = 1, bot = 1;
		for(int i = 0; i < numPlayers; i++) {
			String name;
			if(player.get(i).getLabel() == Label.HUMAN) {
				name = JOptionPane.showInputDialog("Enter your name of player " + human);
	   		 	if(name == null || name.equals("")) {
	   		 		name = "Player " + human;
	   		 	}
	   		 	human++;
			}
			else {
				name = "Bot " + bot;
				bot++;
			}
        	player.get(i).setName(name);
        }
		shufflePlayer();
		for(int i = 0; i < numPlayers; i++) {
			namePlayer[i] = player.get(i).getName();
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
	public abstract boolean endGame();
	public abstract void nextTurn();
	public abstract void findFirstTurn();
}