package project;

import java.util.Arrays;

/**
 * Represents a playing card used in various card games.
 * Each card has a suit, a rank, and an associated game type.
 */
public class Card implements Comparable<Card> {
    private String suit;
    private String rank;
    private String gameType;
    private String value;

    public String getRank() {
        return rank;
    }

    public String getSuit() {
        return suit;
    }

    public String getValue() {
        return value;
    }
    /**
     * Constructs a card with the given suit, rank, and game type.
     *
     * @param suit     The suit of the card (e.g., "S" for Spades, "H" for Hearts).
     * @param rank     The rank of the card (e.g., "A", "2", ..., "K").
     * @param gameType The game type this card belongs to (e.g., "TienLenMienNam", "BaCay").
     */
    public Card(String suit, String rank, String gameType) {
        this.suit = suit;
        this.rank = rank;
        this.gameType = gameType; // Store game type inside the card
        this.value = rank + suit;
    }

    /**
     * Returns a string representation of the card.
     *
     * @return The card in "rank + suit" format (e.g., "AS", "10H").
     */
    @Override
    public String toString() {
        return this.rank + this.suit;
    }

    /**
     * Compares this card with another card based on game-specific rules.
     *
     * @param other The card to compare against.
     * @return A negative integer, zero, or a positive integer as this card is less than,
     *         equal to, or greater than the specified card.
     * @throws IllegalArgumentException if the cards belong to different games.
     */
    @Override
    public int compareTo(Card other) {
        if (!this.gameType.equals(other.gameType)) {
            throw new IllegalArgumentException("Cards belong to different games!");
        }

        String[] rankOrder = GameOrder.getRankOrder(this.gameType);
        int thisRankIndex = Arrays.asList(rankOrder).indexOf(this.rank);
        int otherRankIndex = Arrays.asList(rankOrder).indexOf(other.rank);

        if (thisRankIndex != otherRankIndex) {
            return Integer.compare(thisRankIndex, otherRankIndex);
        }

        String[] suitOrder = GameOrder.getSuitOrder(this.gameType);
        int thisSuitIndex = Arrays.asList(suitOrder).indexOf(this.suit);
        int otherSuitIndex = Arrays.asList(suitOrder).indexOf(other.suit);

        return Integer.compare(thisSuitIndex, otherSuitIndex);
    }

    public int getCardRank() {
        // Get the rank order for the specific game
        String[] rankOrder = GameOrder.getRankOrder(this.gameType);

        // Find the index of the card's rank in the rank order array
        for (int i = 0; i < rankOrder.length; i++) {
            if (rankOrder[i].equals(this.rank)) {
                return i;  // Return the rank index as an integer
            }
        }

        // If rank is not found, return -1 (this should not happen if the card is valid)
        return -1;
    }

    public boolean isRed() {
        return suit.equals("H") || suit.equals("D");
    }

    public boolean isSameColor(Card other) {
        return this.isRed() == other.isRed();
    }

}