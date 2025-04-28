package project;

import java.util.List;

public class ValidateCreator {
    public static TienLenValidator createValidator(List<Card> lastPlayedCards, List<Card> chosenCards, String gameType) {
        if ("TienLenMienBac".equals(gameType)) {
            return new TienLenMienBacValidator(lastPlayedCards, chosenCards);
        } else {
            return new TienLenMienNamValidator(lastPlayedCards, chosenCards);
        }
    }
}