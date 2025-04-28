package project;

import java.util.List;

public class TienLenMienBacCombination extends Combination {

    public TienLenMienBacCombination(List<Card> cards) {
        super(cards);
    }

    @Override
    public boolean isPair() {
        return cards.size() == 2 &&
                cards.get(0).getCardRank() == cards.get(1).getCardRank() &&
                areSameSuit(cards);
    }

    @Override
    public boolean isStraight() {
        if (cards.size() < 3) return false;

        // For TienLenMienBac, all cards must be of the same suit
        if (!areSameSuit(cards)) {
            return false;
        }

        // Check for consecutive ranks
        for (int i = 1; i < cards.size(); i++) {
            if (cards.get(i).getCardRank() - cards.get(i - 1).getCardRank() != 1) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean isConsecutivePairs(int n) {
        if (cards.size() != 2 * n) return false;

        for (int i = 0; i < cards.size(); i += 2) {
            Card c1 = cards.get(i);
            Card c2 = cards.get(i + 1);

            if (c1.getCardRank() != c2.getCardRank()) return false;

            // Cards must be same color for TienLenMienBac
            if (!c1.isSameColor(c2)) return false;

            if (i > 0) {
                int prevRank = cards.get(i - 2).getCardRank();
                if (c1.getCardRank() != prevRank + 1) return false;
            }
        }

        return true;
    }
}