//import java.util.ArrayList;
//import java.util.List;
//
//public abstract class BasePlayer {
//    protected String name;
//    protected List<Card> hand;
//
//    public BasePlayer(String name) {
//        this.name = name;
//        this.hand = new ArrayList<>();
//    }
//
//    public abstract List<Card> playTurn();
//
//    /**
//     * Nhận một danh sách bài từ phương thức dealCards() của Deck.
//     * @param cards Danh sách các lá bài được chia.
//     */
//    public void receiveCards(List<Card> cards) {
//        if (cards != null) {
//            this.hand.addAll(cards);
//        }
//    }
//
//    public List<Card> getHand() {
//        return this.hand;
//    }
//
//    public String getName() {
//        return this.name;
//    }
//
//    /**
//     * Hiển thị bài của người chơi theo định dạng dễ đọc.
//     */
//    public void displayCards() {
//        System.out.println("Bài của " + name + ":");
//        for (Card card : this.hand) {
//            System.out.print(card + "  "); // Gọi phương thức toString() của Card
//        }
//        System.out.println();
//    }
//}
package project;

import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import java.util.ArrayList;
import java.util.List;

public abstract class BasePlayer {
    protected String name;
    protected List<Card> hand;
    protected Pane handPane;  // This Pane will hold the player's cards

    public BasePlayer(String name, Pane handPane) {
        this.name = name;
        this.hand = new ArrayList<>();
        this.handPane = handPane;  // Initialize the Pane to display the cards
    }

    public abstract List<Card> playTurn();

    public void receiveCards(List<Card> cards) {
        if (cards != null) {
            this.hand.addAll(cards);
        }
    }

    public List<Card> getHand() {
        return this.hand;
    }

    public String getName() {
        return this.name;
    }

    /**
     * Display the player's cards in the GUI (no parameters).
     */
    public void displayCards() {
        // Clear the previous cards if any
        this.handPane.getChildren().clear();

        // Display player's name (this can be a separate label, etc.)
        Label nameLabel = new Label("Player: " + name);
        nameLabel.setLayoutX(10);  // Position it at (10, 10) in the Pane
        nameLabel.setLayoutY(10);  // Position at Y = 10 (adjust as needed)
        this.handPane.getChildren().add(nameLabel);  // Add name to Pane

        // Display each card as a Label
        int cardOffset = 40;  // The offset for card position (adjust if needed)
        for (int i = 0; i < this.hand.size(); i++) {
            Card card = this.hand.get(i);
            Label cardLabel = new Label(card.toString());  // Display card as text
            cardLabel.setLayoutX(10 + i * 50);  // Position cards horizontally
            cardLabel.setLayoutY(40);  // Position all cards at Y = 40
            this.handPane.getChildren().add(cardLabel);  // Add card label to Pane
        }
    }
}




