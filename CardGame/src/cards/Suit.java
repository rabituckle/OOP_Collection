package cards;

public enum Suit {
	ZERO("0"), SPADES("♠"), CLUBS("♣"), DIAMONDS("♦"), HEARTS("♥");
	private final String symbol;

	Suit(String symbol) {
		this.symbol = symbol;
	}
	
	@Override
	public String toString() {
		return symbol;
	}
	
}
