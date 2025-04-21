package project;

import java.util.ArrayList;
import java.util.List;

public class BaCay extends Game {

    public BaCay(int numRealPlayers, int numBots, ArrayList<BasePlayer> players, String mode) {
        super("BaCay", numRealPlayers, numBots, 3, players, mode); // Mỗi người chơi nhận 3 lá bài

    }

    public void startGame() {
        this.deck.clearDeck();
        this.deck.createDeck();
        this.deck.shuffle();
        dealCardsToPlayers();
        determineWinner();

    }

    public void determineWinner() {
        BasePlayer winner = null;
        int highestScore = -1;

        for (BasePlayer player : players) {
            player.displayCards();
            int score = 0;
            for (Card card : player.getHand()) {
                if (card.getRank() == "A") continue;
                score += Integer.parseInt(card.getRank());
            }
            System.out.println(player.getName() + " có số điểm: " + score);

            if (score > highestScore) {
                highestScore = score;
                winner = player;
            }
        }

        System.out.println("🏆 Người chiến thắng: " + winner.getName() + " với " + highestScore + " điểm!");
    }


}
