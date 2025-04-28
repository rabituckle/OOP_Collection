package project;

import java.util.List;

public class TienLenMienNamCombination extends Combination {

    public TienLenMienNamCombination(List<Card> cards) {
        super(cards);
    }

    @Override
    public boolean isPair() {
        return cards.size() == 2 && cards.get(0).getCardRank() == cards.get(1).getCardRank();
    }

    @Override
    public boolean isStraight() {
        if (cards.size() < 3) return false;

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

            // Check if each pair has the same rank
            if (c1.getCardRank() != c2.getCardRank()) return false;

            // Check if pairs are in consecutive order
            if (i > 0) {
                int prevRank = cards.get(i - 2).getCardRank();
                if (c1.getCardRank() != prevRank + 1) return false;
            }
        }

        return true;
    }
}
