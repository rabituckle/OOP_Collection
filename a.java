package project;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class a extends Application {

    private Player currentPlayer;
    private Player player1;
    private Player player2;

    @Override
    public void start(Stage primaryStage) {
        // Create a pane to display the hand of the players
        VBox root = new VBox(10);

        // Create hand pane for each player
        Pane player1HandPane = new Pane();
        Pane player2HandPane = new Pane();

        // Create player instances
        player1 = new Player("Player 1", player1HandPane);
        player2 = new Player("Player 2", player2HandPane);

        // Add some cards to each player's hand
        player1.hand.add(new Card("2", "S", "TienLenMienNam"));
        player1.hand.add(new Card("3", "H", "TienLenMienNam"));
        player1.hand.add(new Card("K", "D", "TienLenMienNam"));

        player2.hand.add(new Card("A", "C", "TienLenMienNam"));
        player2.hand.add(new Card("10", "S", "TienLenMienNam"));
        player2.hand.add(new Card("7", "H", "TienLenMienNam"));

        // Add UI elements to show the hands of each player
        VBox player1Box = new VBox(10);
        player1Box.getChildren().addAll(new javafx.scene.text.Text("Player 1's Hand"), player1HandPane);
        VBox player2Box = new VBox(10);
        player2Box.getChildren().addAll(new javafx.scene.text.Text("Player 2's Hand"), player2HandPane);

        root.getChildren().addAll(player1Box, player2Box);

        // Add Submit button for player's turn
        Button submitButton = new Button("Submit");
        submitButton.setOnAction(e -> handleTurn());

        root.getChildren().add(submitButton);

        // Set the current player to player1 initially
        currentPlayer = player1;

        // Create the scene and show the stage
        Scene scene = new Scene(root, 600, 400);
        primaryStage.setTitle("Card Game");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void handleTurn() {
        // Handle the turn of the current player
        currentPlayer.playTurn();  // Let the current player play their turn

        // After the player plays their turn, check if they selected any cards
        if (currentPlayer.selectedCards.isEmpty()) {
            System.out.println(currentPlayer.getName() + " skipped the turn.");
        } else {
            System.out.println(currentPlayer.getName() + " played: " + currentPlayer.selectedCards);
        }

        // Switch to the next player
        if (currentPlayer == player1) {
            currentPlayer = player2;
        } else {
            currentPlayer = player1;
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
