package utilities.card;

import java.awt.Color;

public abstract class Card {
	
	public abstract String getSuit();
	public abstract String getRank();
	public abstract String getValue();
	public abstract Color getColor();
	public abstract int getCost();
	public abstract int compare(Card card);
}
