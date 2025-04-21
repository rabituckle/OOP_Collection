package cards;

public enum Rank {
	ZERO("0"), THREE("3"), FOUR("4"), FIVE("5"), SIX("6"), SEVEN("7"), EIGHT("8"),
	NINE("9"), TEN("10"), JACK("J"), QUEEN("Q"), KING("K"), ACE("A"), TWO("2");
	private final String symbol;

	Rank(String symbol) {
		this.symbol = symbol;
	}
	
	@Override
	public String toString() {
		return symbol;
	}
	
}
