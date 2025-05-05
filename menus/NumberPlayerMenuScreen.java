package ui.menus;

import enums.ModeGUI;
import enums.TypeGame;

import javax.swing.*;

public class NumberPlayerMenuScreen extends MenuScreen {
    public void show() {
        JPanel mainPanel = createBasePanel("Number Of Human");
        JPanel buttonPanel = createButtonPanel();

        // Player number buttons
        for (int i = 0; i < MAX_PLAYERS; i++) {
            MenuScreen.numHumans = i + 1;
            String playerOption = MenuScreen.numHumans + " HUMAN" + (i == 0 ? "" : "S");
            addButtonWithAction(buttonPanel, playerOption, e -> {
                mainFrame.dispose();

                MenuScreen.minBots = (MenuScreen.numHumans == 1) ? 1 : 0;
                MenuScreen.maxBots = MAX_PLAYERS - MenuScreen.numHumans;
                new SetUpMenuScreen().show();
            });
        }

        // Back button
        addButtonWithAction(buttonPanel, "BACK", e -> {
            mainFrame.dispose();
            new GameSelectionMenuScreen().show();
        });

        setupAndShowFrame(mainPanel, buttonPanel, 500, 500);
    }
}
