package game;

import java.awt.event.MouseAdapter;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import ui.game.*;
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
	private ArrayList<String> name;
	private int maxCards;
	private int turn = 0;
	protected int show = 0;
	protected GameUI ui;
	protected Deck deckObj;
	protected ArrayList<Player> player = new ArrayList<Player>();
    protected ArrayList<Card> chosenCard = new ArrayList<Card>();
    protected ArrayList<Card> previousCard = new ArrayList<Card>();
    protected ArrayList<Card> botCards = new ArrayList<Card>();
    
    public ControlGame(int mode, int numHuman, int numBots, ArrayList<String> name) {
    	this.numHuman = numHuman;
    	this.numBots = numBots;
    	this.numPlayers = numHuman + numBots;
    	this.mode = mode;
    	this.name = name;
    }
    
    public abstract MouseAdapter handleInput();
	public abstract JPanel controlPanel();
	public abstract void newGame();
	public abstract void endGame();
	public abstract void nextTurn();
	
    public void initialize() {
    	// Initialize for cardState and players
		for(int i = 0; i < numHuman; i++) {
			Player player = new Human();
			this.player.add(player);
		}
		for(int i = 0; i < numBots; i++) {
			Player player = new Bot();
			this.player.add(player);
		}
		getPlayerName();
    }
    
	public void getPlayerCards() {
		for(int i = 0; i < numPlayers; i++) {
			for(int j = 0; j < maxCards; j++) {
				player.get(i).getCardState().add(0);
			}
		}
		
		ArrayList<Card>[] playerCard = deckObj.dealer(numPlayers, maxCards);
		for(int i = 0; i < numPlayers; i++) {
			player.get(i).setCards(playerCard[i]);
		}
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
		int bot = 1;
		for(int i = 0; i < numPlayers; i++) {
			String name;
			if(player.get(i).getLabel() == Label.HUMAN) {
				name = this.name.get(i);
			}
			else {
				name = "Bot " + bot;
				bot++;
			}
        	player.get(i).setName(name);
        }
		Collections.shuffle(player);
		this.name.clear();
		for(int i = 0; i < numPlayers; i++) {
			this.name.add(player.get(i).getName());
		}
	}
	
	public void setMaxCards(int max) {
		this.maxCards = max;
	}
}