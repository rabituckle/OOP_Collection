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
    public ArrayList<Player> dealer(int players, int bots, int maxCards) {
    	ArrayList<Card> deck = shuffleDeck();
        ArrayList<Player> player = new ArrayList<Player>();
        int idx = 0;
        for (int j = 0; j < players; j++) {
            player.add(new Human());
        }
        for(int i = 0; i < bots; i++) {
        	player.add(new Bot());
        }
        for (int i = 0; i < maxCards; i++) {
            for (int j = 0; j < players + bots; j++) {
                player.get(j).add(deck.get(idx));
                idx++;
            }
        }
        for (int i = 0; i < players + bots; i++) {
            player.get(i).getCards().sort(null);  // Sorts based on compareTo method
        }
        return player;
    }
}
