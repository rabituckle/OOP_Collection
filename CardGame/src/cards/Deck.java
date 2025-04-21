package cards;

import java.util.*;

public class Deck {
	private List<Card> cards;
	
	public Deck() {
		cards = new ArrayList<>();
		for (Rank rank: Rank.values()) {
			for (Suit suit: Suit.values()) {
				if(rank != Rank.ZERO && suit != Suit.ZERO)
				cards.add(new Card(rank, suit));
			}
		}
		Collections.shuffle(cards);
	}
	
	public Card drawCard() {
		return cards.isEmpty()? null: cards.remove(0);
	}
	
	public int getRemainingCards() {
		return cards.size();
	}
	
	@Override
	public String toString() {
		String deck = cards + "\n[";
		for (Card card : cards) {
			deck += card.getScore() + " , ";
		}
		return deck;
	}

}
