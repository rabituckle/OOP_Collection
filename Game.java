package project;

import java.util.ArrayList;

public class Game {
    public int numCardsPerPlayer;
    public int numRealPlayers;
    public int numBots;
    public int numPlayers;
    public String gameType;
    public Deck deck;
    public ArrayList<BasePlayer> players;
    public String mode;
    public Game (String gameType, int RealPlayers, int Bots, int numCardsPerPlayer, ArrayList<BasePlayer> players, String mode) {
        this.deck = new Deck(gameType);
        this.gameType = gameType;
        this.numRealPlayers = RealPlayers;
        this.numBots = Bots;
        this.numCardsPerPlayer = numCardsPerPlayer;
        this.numPlayers = this.numRealPlayers + this.numBots;
        this.players = players;
        this.mode = mode;
    }

    public void dealCardsToPlayers() {
        ArrayList<ArrayList<Card>> dealtCards = this.deck.dealCards(numPlayers, numCardsPerPlayer);
        for (int i = 0; i < numPlayers; i++) {
            players.get(i).receiveCards(dealtCards.get(i)); // Sửa lỗi ở đây
        }
    }
}
