package model.utilities.card;

import java.awt.Color;

public abstract class BasePlayingCard extends Card implements Comparable<BasePlayingCard> {
    protected String type;   // suit
    protected String rank;
    protected int cost;
    protected String value;
    protected Color color;
    protected static final String[] SUITS = {"S", "C", "D", "H"};

    public BasePlayingCard(String suit, String rank) {
        this.type = suit;
        this.rank = rank;

        if (suit.equals("S") || suit.equals("C")) {
            color = Color.BLACK;
        } else {
            color = Color.RED;
        }

        this.value = rank + suit;

        int index = findIndex(getRANKS(), rank);
        this.cost = (index != -1) ? index + 3 : 0;
    }

    // Abstract method to be implemented by subclass
    protected abstract String[] getRANKS();

    @Override
    public String toString() {
        return rank + type;
    }

    @Override
    public int getCost() {
        return cost;
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public String getRank() {
        return rank;
    }

    @Override
    public String getSuit() {
        return type;
    }

    @Override
    public Color getColor() {
        return color;
    }

    protected int findIndex(String[] array, String value) {
        for (int i = 0; i < array.length; i++) {
            if (array[i].equals(value)) {
                return i;
            }
        }
        return -1;
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

        int thisSuit = findIndex(SUITS, this.type);
        int otherSuit = findIndex(SUITS, other.type);
        return thisSuit - otherSuit;
    }
}
