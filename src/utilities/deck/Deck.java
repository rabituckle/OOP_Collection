package utilities.deck;

import java.util.ArrayList;
import java.util.Random;

import utilities.card.Card;
import utilities.player.Player;

public abstract class Deck {
	
	public abstract ArrayList<Card> createDeck();
	public abstract ArrayList<Card>[] dealer (int players, int maxCards);
	
	public ArrayList<Card> shuffleDeck(){
		ArrayList<Card> deck = createDeck();
        Random random = new Random();
        for (int i = 0; i < 2 * deck.size(); i++) {
            int j = random.nextInt(deck.size());
            Card currCard = deck.get(i % deck.size());
            Card randomCard = deck.get(j);
            deck.set(i % deck.size(), randomCard);
            deck.set(j, currCard);
        }
        return deck;
	}
}
