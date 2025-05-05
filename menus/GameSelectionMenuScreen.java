package ui.menus;

import enums.ModeGUI;
import enums.TypeGame;

import javax.swing.*;

public class GameSelectionMenuScreen extends MenuScreen {

    public void show() {
        JPanel mainPanel = createBasePanel("Game Selection");
        JPanel buttonPanel = createButtonPanel();

        for (TypeGame typeGame : TypeGame.values()) {
            addButtonWithAction(buttonPanel, typeGame.getTypeGame(), e -> {
                mainFrame.dispose();
                MenuScreen.typeGame = typeGame;
                new NumberPlayerMenuScreen().show(); 
            });
        }

        addButtonWithAction(buttonPanel, "BACK", e -> {
            mainFrame.dispose();
            new ChooseModeScreen().show();
        });

        setupAndShowFrame(mainPanel, buttonPanel, 500, 450);
    }
}
