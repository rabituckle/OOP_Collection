package project;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public abstract class GameUI {
    protected Game game;

    public GameUI(Game game) {
        this.game = game;
    }

    public abstract void displayGameInfo();
    public abstract void updatePlayerTurn(BasePlayer player);
    public abstract void displayCards(List<Card> cards);
    public abstract void displayEndGame(String winnerName);
}
