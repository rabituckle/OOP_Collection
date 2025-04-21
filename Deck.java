package project;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Deck {
    private ArrayList<Card> deck;
    private String gameType;
    private static final String[] SUITS = {"S", "C", "D", "H"};
    private static final String[] FULL_VALUES = {"A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K"};

    public Deck(String gameType) {
        this.gameType = gameType;
        this.deck = new ArrayList<>();
        this.createDeck();
    }

    public void createDeck() {
        this.deck.clear(); // Xóa bộ bài cũ trước khi tạo mới
        switch (this.gameType) {
            case "TienLenMienNam":
            case "TienLenMienBac":
                for (String suit : SUITS) {
                    for (String value : FULL_VALUES) {
                        this.deck.add(new Card(suit, value, this.gameType));
                    }
                }
                break;
            case "BaCay":
                for (String suit : SUITS) {
                    for (int i = 0; i < 10; i++) {
                        this.deck.add(new Card(suit, FULL_VALUES[i], this.gameType));
                    }
                }
                break;
            default:
                throw new IllegalArgumentException("Unsupported game type: " + this.gameType);
        }
    }

    public void shuffle() {
        Collections.shuffle(this.deck, new Random());
    }

    public ArrayList<ArrayList<Card>> dealCards(int numPlayers, int numCardsPerPlayer) {
        if (numPlayers * numCardsPerPlayer > this.deck.size()) {
            throw new IllegalArgumentException("Not enough cards to deal!");
        }

        ArrayList<ArrayList<Card>> playerHands = new ArrayList<>();
        for (int i = 0; i < numPlayers; i++) {
            playerHands.add(new ArrayList<>());
        }

        for (int i = 0; i < numCardsPerPlayer; i++) {
            for (int j = 0; j < numPlayers; j++) {
                if (!this.deck.isEmpty()) {
                    playerHands.get(j).add(this.deck.remove(0));
                }
            }
        }
        return playerHands;
    }

    public void clearDeck() {
        this.deck.clear();
    }

    public ArrayList<Card> getRemainingCards() {
        return this.deck;
    }

    public String getGameType() {
        return this.gameType;
    }
}
