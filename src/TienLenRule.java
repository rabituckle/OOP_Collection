import java.util.ArrayList;
import java.util.Collections;

import static java.lang.Math.abs;

public abstract class TienLenRule {

    public abstract boolean isValid(ArrayList<CardPlayingCard> chosenCards);

    public abstract boolean canBeat(ArrayList<CardPlayingCard> preChosenCards, ArrayList<CardPlayingCard> chosenCards);

    protected abstract boolean validateSpecialCases(ArrayList<CardPlayingCard> preChosenCards, ArrayList<CardPlayingCard> chosenCards);

    protected boolean isPair(ArrayList<CardPlayingCard> cards) {
        if (cards.size() != 2) return false;
        return cards.get(0).getRank().equals(cards.get(1).getRank());
    }

    protected boolean isTriple(ArrayList<CardPlayingCard> cards) {
        if (cards.size() != 3) return false;
        String rank = cards.getFirst().getRank();
        return cards.stream().allMatch(c -> c.getRank().equals(rank));
    }

    protected boolean isFourOfAKind(ArrayList<CardPlayingCard> cards) {
        if (cards.size() != 4) return false;
        String rank = cards.getFirst().getRank();
        return cards.stream().allMatch(c -> c.getRank().equals(rank));
    }

    protected boolean isStraight(ArrayList<CardPlayingCard> cards) {
        if (cards.size() < 3) return false;

        for (int i = 0; i < cards.size() - 1; i++) {
            int current = cards.get(i).getCost();
            int next = cards.get(i + 1).getCost();
            if (next != current + 1) return false;
        }

        return true;
    }

    protected boolean isSequencePair(ArrayList<CardPlayingCard> cards) {
        int n = cards.size();
        if (n % 2 != 0) return false;

        for (int i = 0; i < n; i += 2) {

            if (!cards.get(i).getRank().equals(cards.get(i + 1).getRank())) return false;

            if (i > 0) {
                int prevRank = cards.get(i - 1).getCost();
                int currRank = cards.get(i).getCost();
                if (abs(prevRank - currRank) != 1) return false;
            }
        }

        return true;
    }

    protected boolean isThreeConsecutivePairs(ArrayList<CardPlayingCard> cards) {
        if (cards.size() != 6) return false;
        return isSequencePair(cards);
    }
}