package project;

import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javafx.scene.layout.Pane;

public class Bot extends BasePlayer {
    private Random random;

    public Bot(String name, Pane handPane) {
        super(name, handPane);
        this.random = new Random();
    }

    @Override
    public List<Card> playTurn() {
        if (this.hand.isEmpty()) {
            System.out.println(this.name + " has no cards left.");
            return null;
        }

        // Tạo một số ngẫu nhiên, nếu nhỏ hơn 0.1 thì bỏ lượt
        if (this.random.nextDouble() < 0.1) {
            System.out.println(this.name + " decides to skip this turn.");
            return null; // Trả về null để biểu thị bot bỏ lượt
        }

        int choice = this.random.nextInt(this.hand.size()); // Chọn một lá bài ngẫu nhiên
        List<Card> chosenCards = new ArrayList<>();
        chosenCards.add(this.hand.remove(choice));

        System.out.println(this.name + " plays: " + chosenCards.get(0));
        return chosenCards;
    }
}
