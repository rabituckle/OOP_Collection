package utilities.player;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import enums.Label;
import enums.State;
import enums.TypeGame;
import utilities.card.Card;

public abstract class Player {
	
	private String name;
	private String password;
	private ArrayList<Card> cards;
	private Map<Card, Rectangle> mapCard = new HashMap<Card, Rectangle>();
    private ArrayList<Integer> cardState = new ArrayList<Integer>();
	private State state;
	private Label label;
    private int[] scores = new int[TypeGame.values().length];
    private int bet;
	
	public Player(String name, String password) {
		this.name = name;
		this.password = password;
		cards = new ArrayList<Card>();
		state = State.PLAY;
	}
	
	public int getNumCards() {
		return cards.size();
	}
	
	public void removeCard(int i) {
		cards.remove(i);
	}

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public Card getCard(int i) {
		return cards.get(i);
	}

	public void setState(State state) {
		this.state = state;
	}

	public State getState() {
		return state;
	}
	
	public void add(Card c) {
		cards.add(c);
	}
	
	public ArrayList<Card> getCards(){
		return cards;
	}

	public Label getLabel() {
		return label;
	}

	public void setLabel(Label label) {
		this.label = label;
	}

	public Map<Card, Rectangle> getMapCard() {
		return mapCard;
	}

	public void setMapCard(Map<Card, Rectangle> mapCard) {
		this.mapCard = mapCard;
	}

	public ArrayList<Integer> getCardState() {
		return cardState;
	}

	public void setCardState(ArrayList<Integer> cardState) {
		this.cardState = cardState;
	}
	
    public void setScore(TypeGame game, int score) {
        scores[game.ordinal()] = score;
    }

    public int getScore(TypeGame game) {
        return scores[game.ordinal()];
    }
    
    public int getBet() {
    	return bet;
    }
    public void setBet(int bet) {
    	this.bet = bet;
    }
}
