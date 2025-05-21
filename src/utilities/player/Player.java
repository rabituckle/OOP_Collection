package utilities.player;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import utilities.card.Card;

public abstract class Player {
	
	private String name;
	private ArrayList<Card> cards;
	private Map<Card, Rectangle> mapCard = new HashMap<Card, Rectangle>();
    private ArrayList<Integer> cardState = new ArrayList<Integer>();
	private State state;
	private Label label;
	private int score;
	
	public Player() {
		cards = new ArrayList<Card>();
		state = State.PLAY;
	}
	
	public void setCards(ArrayList<Card> cards) {
		this.cards = cards;
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
	
	public void setScore(int score) {
		this.score = score;
	}
	
	public int getScore() {
		return score;
	}
}
