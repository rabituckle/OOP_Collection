package project;

import java.util.List;

public abstract class TienLenValidator {
    protected List<Card> lastPlayedCards;
    protected List<Card> chosenCards;
    protected Combination lastPlayedCombination;
    protected Combination chosenCombination;

    protected TienLenValidator(List<Card> lastPlayedCards, List<Card> chosenCards) {
        sortCards(lastPlayedCards, chosenCards);
        this.lastPlayedCards = lastPlayedCards;
        this.chosenCards = chosenCards;
        initializeCombinations();
    }

    private void sortCards(List<Card> lastPlayedCards, List<Card> chosenCards) {
        lastPlayedCards.sort((a, b) -> a.getCardRank() - b.getCardRank());
        chosenCards.sort((a, b) -> a.getCardRank() - b.getCardRank());
    }

    protected abstract void initializeCombinations();

    public boolean isValid() {
        // Single
        if (lastPlayedCombination.isSingle() && chosenCombination.isSingle()) {
            return validateSingle();
        }

        // Pair
        if (lastPlayedCombination.isPair() && chosenCombination.isPair()) {
            return validatePair();
        }

        // Triple
        if (lastPlayedCombination.isTriple() && chosenCombination.isTriple()) {
            return validateTriple();
        }

        // Four of a Kind
        if (lastPlayedCombination.isFourOfAKind() && chosenCombination.isFourOfAKind()) {
            return validateFourOfAKind();
        }

        // Straight
        if (lastPlayedCombination.isStraight() && chosenCombination.isStraight()) {
            return validateStraight();
        }

        // Special cases
        return validateSpecialCases();
    }

    protected boolean validateSingle() {
        if (!lastPlayedCards.get(0).getSuit().equals(chosenCards.get(0).getSuit())) {
            return false;
        }
        return chosenCards.get(0).getCardRank() > lastPlayedCards.get(0).getCardRank();
    }

    protected abstract boolean validatePair();

    protected abstract boolean validateTriple();

    protected boolean validateFourOfAKind() {
        return chosenCards.get(3).getCardRank() > lastPlayedCards.get(3).getCardRank();
    }

    protected boolean validateStraight() {
        int highestCard1 = lastPlayedCards.getLast().getCardRank();
        int highestCard2 = chosenCards.getLast().getCardRank();
        return highestCard2 > highestCard1;
    }

    protected boolean validateSpecialCases() {
        // 3 consecutive pairs can beat Ace (2 in traditional cards)
        if (chosenCombination.isThreeConsecutivePairs() && lastPlayedCombination.isSingle()) {
            Card target = lastPlayedCards.get(0);
            if (target.getCardRank() == 15) { // Ace
                return true;
            }
        }

        // Four of a kind can beat Ace
        if (chosenCombination.isFourOfAKind() && lastPlayedCombination.isSingle()) {
            Card target = lastPlayedCards.get(0);
            if (target.getCardRank() == 15) { // Ace
                return true;
            }
        }

        // Four of a kind can beat a pair of Aces
        if (chosenCombination.isFourOfAKind() && lastPlayedCombination.isPair()) {
            Card target = lastPlayedCards.get(0);
            if (target.getCardRank() == 15) { // Pair of Aces
                return true;
            }
        }

        // Four of a kind can beat 3 consecutive pairs
        if (chosenCombination.isFourOfAKind() && lastPlayedCombination.isThreeConsecutivePairs()) {
            return true;
        }

        return false;
    }
}