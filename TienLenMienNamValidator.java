package project;

import java.util.List;

public class TienLenMienNamValidator extends TienLenValidator {
    public TienLenMienNamValidator(List<Card> lastPlayedCards, List<Card> chosenCards) {
        super(lastPlayedCards, chosenCards);
    }

    @Override
    protected void initializeCombinations() {
        this.lastPlayedCombination = new TienLenMienNamCombination(lastPlayedCards);
        this.chosenCombination = new TienLenMienNamCombination(chosenCards);
    }

    @Override
    protected boolean validatePair() {
        return chosenCards.get(1).getCardRank() > lastPlayedCards.get(1).getCardRank();
    }

    @Override
    protected boolean validateTriple() {
        return chosenCards.get(2).getCardRank() > lastPlayedCards.get(2).getCardRank();
    }
}