package utilities.deck;

import java.util.ArrayList;
import utilities.card.Card;
import utilities.card.CardPlayingCard;
import utilities.player.Bot;
import utilities.player.Human;
import utilities.player.Player;

public class DeckPlayingCard extends Deck{
	
	private static String[] suit = {"S", "C", "D", "H"}; // ♠, ♣, ♦, ♥
    private static String[] val = {"3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "A", "2"};
	
    // Create deck of 52 cards
    @Override
	public ArrayList<Card> createDeck() {
        ArrayList<Card> deck = new ArrayList<Card>();
        for (int i = 0; i < val.length; i++) {
            for (int j = 0; j < suit.length; j++) {
                CardPlayingCard card = new CardPlayingCard(suit[j], val[i]);
                deck.add(card);
            }
        }
        return deck;
    }

    // Distribute cards to players
    @Override
    public ArrayList<Card>[] dealer(int players, int maxCards) {
    	ArrayList<Card> deck = shuffleDeck();
    	ArrayList<Card>[] playerCard = new ArrayList[players];
        for (int i = 0; i < players; i++) {
            playerCard[i] = new ArrayList<Card>();
        }
        int idx = 0;
        for (int i = 0; i < maxCards; i++) {
            for (int j = 0; j < players; j++) {
                playerCard[j].add(deck.get(idx));
                idx++;
            }
        }
        for (int i = 0; i < players; i++) {
            playerCard[i].sort(null);  // Sorts based on compareTo method
        }
        return playerCard;
    }
}
