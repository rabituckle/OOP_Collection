package Player;

import java.util.*;

import cards.*;
import games.*;
import rules.ICardRule;

public abstract class Player {
	private String name;
	protected List<Card> hand;
	private State state;
	static Scanner sc = new Scanner(System.in);
	
	public Player(String name) {
		this.name = name;
		this.hand = new ArrayList<>();
		this.state = State.PLAY;
		
	}
	
	public void drawCard(Deck deck) {
		Card card = deck.drawCard();
		if (card != null) {
			hand.add(card);
		}
		else System.out.println("The deck is empty.");
	}
	
	public abstract int chooseCard();
	
	public Card playCard(ICardRule rule, boolean isFirstTurn, Card prevCard) {
	    while (true) {
	    	if(!canPlay(prevCard)) {
	    		state = State.PASS;
	    		System.out.println("Can`t play");
	    		return prevCard;
	    	}
	        int choosen = chooseCard(); 

	        if (choosen == 141) {
	        	if (isFirstTurn) {
	        		System.out.println("The first can`t pass!");
	        		continue;
	        	}
	        	else {
		            state = State.PASS;
		            System.out.println("Player passed.");
		            break;
	        	}
	        }

	        if (choosen < 0 || choosen >= hand.size()) {
	            System.out.println("Invalid index. Try again.");
	            continue;
	        }

	        Card cardChoosen = hand.get(choosen);

	        if (rule.checkValidate(cardChoosen, prevCard)) {
	            hand.remove(choosen);
	            System.out.println("Player " + this.name + " played " + cardChoosen);
	            return cardChoosen;
	        } else {
	            System.out.println("Invalid card. Please choose again.");
	        }
	    }
	    return null;
	}
	
	public boolean canPlay(Card prevCard) {
		for (Card card: hand) {
			if(card.getScore() > prevCard.getScore()) {
				return true;
			}
		}
		return false;
	}

	
	public void showHand() {
		System.out.println(name + ": " + hand);
	}
	
	public boolean checkWin() {
		return this.hand.isEmpty();
	}

	public String getName() {
		return name;
	}
	
	public State getState() {
		return state;
	}
	
	public void setState(State state) {
		this.state = state;
	}
	
	

	
}
