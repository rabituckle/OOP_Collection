package rule;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import utilities.card.Card;

public class BaCayRule {
    
    public static int getPoint(ArrayList<Card> cards) {
        if (cards.size() != 3) return -1;

        int total = 0;
        for (Card card : cards) {
            int cost = card.getCost();
            if (cost >= 11 && cost <= 13) {
                total += 10;
            } else if (cost == 14) { // A
                total += 1;
            } else {
                total += cost;
            }
        }
        return total % 10;
    }

    public static int compareHands(ArrayList<Card> hand1, ArrayList<Card> hand2) {
        int point1 = getPoint(hand1);
        int point2 = getPoint(hand2);

        if (point1 != point2) {
            return point1 - point2;
        }

        Comparator<Card> cardComparator = (c1, c2) -> c2.compare(c1);
        Collections.sort(hand1, cardComparator);
        Collections.sort(hand2, cardComparator);

        for (int i = 0; i < 3; i++) {
            int cmp = hand1.get(i).compare(hand2.get(i));
            if (cmp != 0) return cmp;
        }

        return 0; 
    }
}
