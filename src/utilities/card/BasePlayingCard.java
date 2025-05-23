package utilities.card;

import java.awt.Color;

public abstract class BasePlayingCard extends Card implements Comparable<BasePlayingCard> {
    
	protected String type;   // suit
    protected String rank;
    protected int cost;
    protected String value;
    protected Color color;

    public BasePlayingCard(String suit, String rank) {
        this.type = suit;
        this.rank = rank;

        if (suit.equals("S") || suit.equals("C")) {
            color = Color.BLACK;
        } else {
            color = Color.RED;
        }

        this.value = rank + suit;
    }

    // Abstract method to be implemented by subclass
    protected abstract String[] getRANKS();
    protected abstract String[] getSUITS();

    @Override
    public String toString() {
        return rank + type;
    }

    @Override
    public String getSuit() {
        return type;
    }
    
    @Override
    public String getRank() {
        return rank;
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public Color getColor() {
        return color;
    }
    
    @Override
    public int getCost() {
        return cost;
    }
    
    @Override
    public int compare(Card card) {
        if (!(card instanceof BasePlayingCard)) {
            throw new IllegalArgumentException("Card must be instance of BasePlayingCard");
        }
        return compareTo((BasePlayingCard) card);
    }

    @Override
    public int compareTo(BasePlayingCard other) {
        int thisRank = findIndex(getRANKS(), this.rank);
        int otherRank = findIndex(other.getRANKS(), other.rank);
        if (thisRank != otherRank) {
            return thisRank - otherRank;
        }

        int thisSuit = findIndex(getSUITS(), this.type);
        int otherSuit = findIndex(getSUITS(), other.type);
        return thisSuit - otherSuit;
    }
    
    protected int findIndex(String[] array, String value) {
        for (int i = 0; i < array.length; i++) {
            if (array[i].equals(value)) {
                return i;
            }
        }
        return -1;
    }
}