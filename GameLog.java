package project;

import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class GameLog {
    private VBox messageBox;

    public GameLog() {
        this.messageBox = new VBox(); // Dùng chung cho cả Basic và Graphic Mode
    }

    public void updateLog(String message) {
        Label newMessage = new Label(message);
        this.messageBox.getChildren().add(newMessage);
    }

    public VBox getMessageBox() {
        return this.messageBox;
    }
}
