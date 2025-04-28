package project;

import java.util.List;

public abstract class Combination {
    protected List<Card> cards;

    public Combination(List<Card> cards) {
        cards.sort((a, b) -> a.getCardRank() - b.getCardRank());
        this.cards = cards;
    }

    public boolean isSingle() {
        return cards.size() == 1;
    }

    public boolean isTriple() {
        return cards.size() == 3 && cards.get(0).getCardRank() == cards.get(1).getCardRank() &&
                cards.get(0).getCardRank() == cards.get(2).getCardRank();
    }

    public boolean isFourOfAKind() {
        if (cards.size() != 4) return false;

        int value1 = cards.get(0).getCardRank();
        int value2 = cards.get(1).getCardRank();
        int value3 = cards.get(2).getCardRank();
        int value4 = cards.get(3).getCardRank();

        return value1 == value2 && value1 == value3 && value1 == value4;
    }

    public abstract boolean isPair();
    public abstract boolean isStraight();
    public abstract boolean isConsecutivePairs(int n);

    public boolean isThreeConsecutivePairs() {
        return isConsecutivePairs(3);
    }

    public boolean isFourConsecutivePairs() {
        return isConsecutivePairs(4);
    }

    public boolean isValidCombination() {
        return isSingle() ||
                isPair() ||
                isTriple() ||
                isStraight() ||
                isFourOfAKind() ||
                isThreeConsecutivePairs() ||
                isFourConsecutivePairs();
    }

    protected boolean areSameSuit(List<Card> cards) {
        if (cards == null || cards.isEmpty()) return true;

        String firstSuit = cards.get(0).getSuit();
        for (Card card : cards) {
            if (!card.getSuit().equals(firstSuit)) {
                return false;
            }
        }
        return true;
    }
}
