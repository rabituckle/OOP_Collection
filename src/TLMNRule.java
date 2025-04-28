import java.util.ArrayList;
import java.util.Collections;


public class TLMNRule extends TienLenRule {
    @Override
    public boolean isValid(ArrayList<CardPlayingCard> chosenCards) {
        Collections.sort(chosenCards);

        switch (chosenCards.size()) {
            case 0:
                return false;
            case 1:
                return true; // Check for 3 of spades in first turn if needed
            case 2:
                return isPair(chosenCards);
            case 3:
                return isTriple(chosenCards);
            case 4:
                return isFourOfAKind(chosenCards);
            default:
                return isStraight(chosenCards) || isSequencePair(chosenCards);
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
        // 3 consecutive pairs can beat 2 (highest card)
        if (isThreeConsecutivePairs(chosenCards) && preChosenCards.size() == 1) {
            CardPlayingCard target = preChosenCards.get(0);
            if (target.getRank().equals("2")) { // 2 is the highest card
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

        // Special rules: beat 2
        if (preChosenCards.size() == 1 && preChosenCards.get(0).getRank().equals("2")) {
            if (isSequencePair(chosenCards) && chosenCards.size() >= 6) return true;
        }

        // Beat pair of 2s
        if (isPair(preChosenCards) &&
                preChosenCards.get(0).getRank().equals("2") &&
                preChosenCards.get(1).getRank().equals("2")) {
            if (isSequencePair(chosenCards) && chosenCards.size() >= 8) return true;
        }

        return false;
    }

    @Override
    protected boolean validateSingle(ArrayList<CardPlayingCard> lastPlayedCards,
                                     ArrayList<CardPlayingCard> chosenCards) {
        return chosenCards.get(0).compareTo(lastPlayedCards.get(0)) > 0;
    }

    @Override
    protected boolean validatePair(ArrayList<CardPlayingCard> lastPlayedCards,
                                   ArrayList<CardPlayingCard> chosenCards) {
        return chosenCards.get(1).compareTo(lastPlayedCards.get(1)) > 0;
    }

    @Override
    protected boolean validateStraight(ArrayList<CardPlayingCard> lastPlayedCards,
                                       ArrayList<CardPlayingCard> chosenCards) {
        return chosenCards.get(chosenCards.size() - 1).compareTo(lastPlayedCards.get(lastPlayedCards.size() - 1)) > 0;
    }
}