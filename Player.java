package project;

import java.util.List;

//public class Player extends BasePlayer {
//    public Player(String name) {
//        super(name);
//    }
//
//    @Override
//    public List<Card> playTurn() {
//        if (hand.isEmpty()) {
//            System.out.println(name + " has no cards left.");
//            return null;
//        }
//
//        Scanner scanner = new Scanner(System.in);
//        System.out.print("Enter the cards to play: ");
//        String input = scanner.nextLine();
//
//        if (input.compareTo("Skip") == 0) {
//            return null;
//        }
//        input = input.toUpperCase();
//        String[] cardStrings = input.split(" ");
//        System.out.println(cardStrings);
//        List<Card> chosenCards = new ArrayList<>();
//
//        for (String cardStr : cardStrings) {
//            Card card = findCardInHand(cardStr);
//            if (card != null) {
//                chosenCards.add(card);
//            } else {
//                System.out.println("❌ Invalid card: " + cardStr);
//                return null;
//            }
//        }
//
//        hand.removeAll(chosenCards);
//        return chosenCards;
//    }
//
//    private Card findCardInHand(String cardStr) {
//        for (Card card : hand) {
//            if (card.toString().equals(cardStr)) {
//                return card;
//            }
//        }
//        return null;
//    }
//}


import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.HBox;
import java.util.ArrayList;
import java.util.List;

public class Player extends BasePlayer {
    public List<Card> selectedCards;
    private Pane handPane;
    private Button skipButton;

    public Player(String name, Pane handPane) {
        super(name, handPane);
        this.selectedCards = new ArrayList<>();
        this.handPane = handPane;
    }

    @Override
    public List<Card> playTurn() {
        selectedCards.clear();
        createCardSelectionUI();
        return selectedCards.isEmpty() ? null : selectedCards;
    }

    private void createCardSelectionUI() {
        handPane.getChildren().clear(); // Clear previous content
        HBox cardButtonsContainer = new HBox(10);
        cardButtonsContainer.setStyle("-fx-padding: 10;");

        // Create a button for each card in the player's hand.
        for (Card card : this.hand) {
            Button cardButton = new Button(String.valueOf(card.getValue())); // Show card value on the button
            cardButton.setStyle("-fx-font-size: 16px; -fx-background-color: lightgray;");
            cardButton.setOnAction(event -> handleCardSelection(card));
            cardButton.setId(card.toString()); // Assign a unique ID based on the card's string representation
            cardButtonsContainer.getChildren().add(cardButton);
        }

        handPane.getChildren().add(cardButtonsContainer);

        // Skip button
        skipButton = new Button("Skip");
        skipButton.setStyle("-fx-font-size: 16px; -fx-background-color: lightgray;");
        skipButton.setOnAction(event -> handleSkipAction());
        handPane.getChildren().add(skipButton);
    }

    // Handle selecting or deselecting a card
    private void handleCardSelection(Card card) {
        Button cardButton = (Button) handPane.lookup("#" + card.toString());
        if (!selectedCards.contains(card)) {
            selectedCards.add(card);
            cardButton.setStyle("-fx-background-color: lightblue;");  // Highlight the selected card
        } else {
            selectedCards.remove(card);
            cardButton.setStyle("-fx-background-color: lightgray;"); // Remove highlight if deselected
        }
    }

    // Handle skipping the turn
    private void handleSkipAction() {
        selectedCards.clear();
    }
}




