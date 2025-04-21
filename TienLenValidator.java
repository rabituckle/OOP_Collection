package project;

import java.util.List;

public class TienLenValidator {
    private List<Card> lastPlayedCards;
    private List<Card> chosenCards;
    private String gameType;
    private Combination cards1;
    private Combination cards2;

    public TienLenValidator(List<Card> lastPlayedCards, List<Card> chosenCards, String gameType) {
        lastPlayedCards.sort((a, b) -> a.getCardRank() - b.getCardRank());
        chosenCards.sort((a, b) -> a.getCardRank() - b.getCardRank());
        this.lastPlayedCards = lastPlayedCards;
        this.chosenCards = chosenCards;
        this.gameType = gameType;
        cards1 = new Combination(gameType, lastPlayedCards);
        cards2 = new Combination(gameType, chosenCards);
    }

    public boolean isValid() {
        // Single
        if (cards1.isSingle() && cards2.isSingle() &&
                lastPlayedCards.get(0).getSuit().equals(chosenCards.get(0).getSuit())) {
            return chosenCards.get(0).getCardRank() > lastPlayedCards.get(0).getCardRank();
        }

        // Pair
        if (cards1.isPair() && cards2.isPair()) {
            if (gameType.equals("TienLenMienBac") &&
                    !lastPlayedCards.get(0).getSuit().equals(chosenCards.get(0).getSuit())) {
                return false;
            }
            return chosenCards.get(1).getCardRank() > lastPlayedCards.get(1).getCardRank();
        }

        // Triple
        if (cards1.isTriple() && cards2.isTriple()) {
            if (gameType.equals("TienLenMienBac") &&
                    !lastPlayedCards.get(0).getSuit().equals(chosenCards.get(0).getSuit())) {
                return false;
            }
            return chosenCards.get(2).getCardRank() > lastPlayedCards.get(2).getCardRank();
        }

        // Four of a Kind
        if (cards1.isFourOfAKind() && cards2.isFourOfAKind()) {
            return chosenCards.get(3).getCardRank() > lastPlayedCards.get(3).getCardRank();
        }

        // Straight
        if (cards1.isStraight() && cards2.isStraight()) {
            int highestCard1 = lastPlayedCards.getLast().getCardRank();
            int highestCard2 = chosenCards.getLast().getCardRank();
            return highestCard2 > highestCard1;
        }

        // 3 đôi thông chặt heo
        if (cards2.isConsecutivePairs(3) && cards1.isSingle()) {
            Card target = lastPlayedCards.get(0);
            if (target.getCardRank() == 15) { // Heo
                return true;
            }
        }

        // Tứ quý chặt heo
        if (cards2.isFourOfAKind() && cards1.isSingle()) {
            Card target = lastPlayedCards.get(0);
            if (target.getCardRank() == 15) { // Heo
                return true;
            }
        }

        // Tứ quý chặt đôi heo
        if (cards2.isFourOfAKind() && cards1.isPair()) {
            Card target = lastPlayedCards.get(0);
            if (target.getCardRank() == 15) { // Đôi heo
                return true;
            }
        }

        // Tứ quý chặt 3 đôi thông
        if (cards2.isFourOfAKind() && cards1.isConsecutivePairs(3)) {
            return true;
        }
        return false;
    }

}
