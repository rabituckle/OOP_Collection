package project;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class BasicModeUI extends GameUI {
    private Stage stage;
    private Label gameInfoLabel;
    private Label playerTurnLabel;
    private Label playedCardsLabel;
    private Label winnerLabel;

    public BasicModeUI(Game game) {
        super(game);
    }

    @Override
    public void displayGameInfo() {
        if (gameInfoLabel != null) {
            gameInfoLabel.setText("Game started: " + game.gameType);
        }
    }

    @Override
    public void updatePlayerTurn(BasePlayer player) {
        if (playerTurnLabel != null) {
            playerTurnLabel.setText(player.getName() + "'s turn");
        }
    }

    @Override
    public void displayCards(List<Card> cards) {
        if (playedCardsLabel != null) {
            playedCardsLabel.setText("Cards played: " + cards);
        }
    }

    @Override
    public void displayEndGame(String winnerName) {
        if (winnerLabel != null) {
            winnerLabel.setText("🏆 " + winnerName + " wins the game!");
        }
    }

    public void start(Stage primaryStage) {
        this.stage = primaryStage;

        // Tạo giao diện người dùng đơn giản
        gameInfoLabel = new Label("Game Information: ");
        playerTurnLabel = new Label("Player's turn: ");
        playedCardsLabel = new Label("Cards Played: ");
        winnerLabel = new Label("Winner: ");

        VBox layout = new VBox(10); // Khoảng cách giữa các label
        layout.getChildren().addAll(gameInfoLabel, playerTurnLabel, playedCardsLabel, winnerLabel);

        Scene scene = new Scene(layout, 300, 200);
        primaryStage.setTitle("Basic Mode - Game");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
