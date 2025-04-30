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
	private int maxCards;
	private int turn = 0;
	protected int show = 0;
	protected GameUI ui;
	protected Deck deckObj;
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
	
	public void chooseModeGUI() {
		if(mode == 0) {
			ui = new GraphicalMode();
		}
		else if(mode == 1) {
			ui = new BasicMode();
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
		Collections.shuffle(player);
		for(int i = 0; i < numPlayers; i++) {
			namePlayer[i] = player.get(i).getName();
		}
	}
	
	public String getName(int i) {
		return namePlayer[i];
	}
	
	public void setMaxCards(int max) {
		this.maxCards = max;
	}
	
	public abstract MouseAdapter handleInput();
	public abstract JPanel controlPanel();
	public abstract void newGame();
	public abstract boolean endGame();
	public abstract void nextTurn();
	public abstract void findFirstTurn();
}