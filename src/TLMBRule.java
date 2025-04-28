import java.util.ArrayList;
import java.util.Collections;

public class TLMBRule extends TienLenRule {
    @Override
    public boolean isValid(ArrayList<CardPlayingCard> chosenCards) {
        Collections.sort(chosenCards);

        switch (chosenCards.size()) {
            case 0:
                return false;
            case 1:
                return true;
            case 2:
                return isPair(chosenCards) && sameColor(chosenCards);
            case 3:
                return isTriple(chosenCards);
            case 4:
                return isFourOfAKind(chosenCards);
            default:
                return (isStraight(chosenCards) && sameSuit(chosenCards)) ||
                        (isSequencePair(chosenCards) && sameColor(chosenCards));
        }
    }


    @Override
    public boolean canBeat(ArrayList<CardPlayingCard> preChosenCards, ArrayList<CardPlayingCard> chosenCards) {
        Collections.sort(preChosenCards);
        Collections.sort(chosenCards);

        // First check for special cases
        if (validateSpecialCases(preChosenCards, chosenCards)) {
            return true;
        }

        int preSize = preChosenCards.size();
        int currSize = chosenCards.size();

        // Must be same size
        if (preSize != currSize) {
            return false;
        }

        switch (currSize) {
            case 1:
                return validateSingle(preChosenCards, chosenCards);
            case 2:
                return validatePair(preChosenCards, chosenCards);
            case 3:
                return validateTriple(preChosenCards, chosenCards);
            default:
                return validateStraight(preChosenCards, chosenCards);
        }
    }

    @Override
    protected boolean validateSpecialCases(ArrayList<CardPlayingCard> preChosenCards,
                                           ArrayList<CardPlayingCard> chosenCards) {
        // 3 consecutive pairs can beat 2 (highest card in TLMB)
        if (isThreeConsecutivePairs(chosenCards) && preChosenCards.size() == 1) {
            CardPlayingCard target = preChosenCards.get(0);
            if (target.getRank().equals("2")) {
                return true;
            }
        }

        // Four of a kind can beat 2
        if (isFourOfAKind(chosenCards) && preChosenCards.size() == 1) {
            CardPlayingCard target = preChosenCards.get(0);
            if (target.getRank().equals("2")) {
                return true;
            }
        }

        // Four of a kind can beat a pair of 2s
        if (isFourOfAKind(chosenCards) && isPair(preChosenCards)) {
            CardPlayingCard target = preChosenCards.get(0);
            if (target.getRank().equals("2")) {
                return true;
            }
        }

        // Four of a kind can beat 3 consecutive pairs
        if (isFourOfAKind(chosenCards) && isThreeConsecutivePairs(preChosenCards)) {
            return true;
        }

        return false;
    }

    @Override
    protected boolean validateSingle(ArrayList<CardPlayingCard> preChosenCars,
                                     ArrayList<CardPlayingCard> chosenCards) {
        // Must be same suit
        if (!preChosenCars.get(0).getSuit().equals(chosenCards.get(0).getSuit())) {
            return false;
        }
        return chosenCards.get(0).compareTo(preChosenCars.get(0)) > 0;
    }

    @Override
    protected boolean validatePair(ArrayList<CardPlayingCard> preChosenCars,
                                   ArrayList<CardPlayingCard> chosenCards) {
        // Must be same color
        if (!preChosenCars.get(0).checkColorSuit(chosenCards.get(0))) {
            return false;
        }
        return chosenCards.get(1).compareTo(preChosenCars.get(1)) > 0;
    }

    @Override
    protected boolean validateStraight(ArrayList<CardPlayingCard> preChosenCars,
                                       ArrayList<CardPlayingCard> chosenCards) {
        // Must be same suit
        if (!preChosenCars.get(0).getSuit().equals(chosenCards.get(0).getSuit())) {
            return false;
        }
        return chosenCards.get(chosenCards.size() - 1).compareTo(preChosenCars.get(preChosenCars.size() - 1)) > 0;
    }

    // Helper methods for TLMB specific rules
    private boolean sameSuit(ArrayList<CardPlayingCard> cards) {
        String suit = cards.get(0).getSuit();
        return cards.stream().allMatch(c -> c.getSuit().equals(suit));
    }

    private boolean sameColor(ArrayList<CardPlayingCard> cards) {
        CardPlayingCard card = cards.get(0);

        for (CardPlayingCard x: cards) {
            if (!x.checkColorSuit(card)) return false;
        }

        return true;
    }
}