package cards;

public class Card {
	private Rank rank;
	private Suit suit;
	
	public Card(Rank rank, Suit suit) {
		super();
		this.rank = rank;
		this.suit = suit;
	}
	
	@Override
	public String toString() {
		return rank + "" + suit;
	}
	
	public int getScore() {
		return 10 * rank.ordinal() + suit.ordinal();
	}
}


