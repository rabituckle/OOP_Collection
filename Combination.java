package project;

import java.util.List;

public class Combination {
    private String gameType;
    private List<Card> cards;

    public Combination(String gameType, List<Card> cards) {
        this.gameType = gameType;
        cards.sort((a, b) -> a.getCardRank() - b.getCardRank());
        this.cards = cards;

    }

    public boolean isSingle() {
        return cards.size() == 1;
    }

    public boolean isPair() {
        if (gameType.compareTo("TienLenMienBac") != 0) {
            return cards.size() == 2 && cards.get(0).getCardRank() == cards.get(1).getCardRank();
        }
        else
        {
            return cards.size() == 2 && cards.get(0).getCardRank() == cards.get(1).getCardRank()
                    && areSameSuit(cards);
        }
    }

    public boolean isTriple() {
        return cards.size() == 3 && cards.get(0).getCardRank() == cards.get(1).getCardRank() &&
                cards.get(0).getCardRank() == cards.get(2).getCardRank();
    }

    public boolean isStraight() {
        if (cards.size() < 3) return false;

        // Nếu là Tiến Lên Miền Bắc, kiểm tra tất cả các lá bài phải cùng chất
        if (gameType.equals("TienLenMienBac") && !areSameSuit(cards)) {
            return false; // Nếu không cùng chất thì không hợp lệ
        }

        // Kiểm tra thứ tự liên tiếp của các lá bài
        for (int i = 1; i < cards.size(); i++) {
            if (cards.get(i).getCardRank() - cards.get(i - 1).getCardRank() != 1) {
                return false;
            }
        }
        return true;
    }


    public boolean isFourOfAKind() {
        if (cards.size() != 4) return false;

        int value1 = cards.get(0).getCardRank();
        int value2 = cards.get(1).getCardRank();
        int value3 = cards.get(2).getCardRank();
        int value4 = cards.get(3).getCardRank();

        return value1 == value2 && value1 == value3 && value1 == value4;
    }

    public boolean isConsecutivePairs(int n) {
        if (cards.size() != 2 * n) return false;

        for (int i = 0; i < cards.size(); i += 2) {
            Card c1 = cards.get(i);
            Card c2 = cards.get(i + 1);

            // Kiểm tra từng đôi có cùng rank
            if (c1.getCardRank() != c2.getCardRank()) return false;

            // Nếu là Tiến Lên Miền Bắc, phải cùng màu
            if (gameType.equals("TienLenMienBac") && !c1.isSameColor(c2)) return false;

            // Kiểm tra thứ tự liên tiếp giữa các đôi
            if (i > 0) {
                int prevRank = cards.get(i - 2).getCardRank();
                if (c1.getCardRank() != prevRank + 1) return false;
            }
        }

        return true;
    }

    public boolean isThreeConsecutivePairs() {
        return isConsecutivePairs(3);
    }

    public boolean isFourConsecutivePairs() {
        return isConsecutivePairs(4);
    }

    public boolean isValidCombination() {
        return isSingle() ||
                isPair() ||
                isTriple() ||
                isStraight() ||
                isFourOfAKind() ||
                isThreeConsecutivePairs() ||
                isFourConsecutivePairs();
    }

    private boolean areSameSuit(List<Card> cards) {
        if (cards == null || cards.isEmpty()) return true;

        String firstSuit = cards.get(0).getSuit();
        for (Card card : cards) {
            if (!card.getSuit().equals(firstSuit)) {
                return false;
            }
        }
        return true;
    }

}
