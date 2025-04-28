package project;

import java.util.List;

public class TienLenMienBacValidator extends TienLenValidator {
    public TienLenMienBacValidator(List<Card> lastPlayedCards, List<Card> chosenCards) {
        super(lastPlayedCards, chosenCards);
    }

    @Override
    protected void initializeCombinations() {
        this.lastPlayedCombination = new TienLenMienBacCombination(lastPlayedCards);
        this.chosenCombination = new TienLenMienBacCombination(chosenCards);
    }

    @Override
    protected boolean validatePair() {
        if (!lastPlayedCards.get(0).getSuit().equals(chosenCards.get(0).getSuit())) {
            return false;
        }
        return chosenCards.get(1).getCardRank() > lastPlayedCards.get(1).getCardRank();
    }

    @Override
    protected boolean validateTriple() {
        if (!lastPlayedCards.get(0).getSuit().equals(chosenCards.get(0).getSuit())) {
            return false;
        }
        return chosenCards.get(2).getCardRank() > lastPlayedCards.get(2).getCardRank();
    }
}