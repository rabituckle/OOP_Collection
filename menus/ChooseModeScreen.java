package ui.menus;

import enums.ModeGUI;

import javax.swing.*;

public class ChooseModeScreen extends MenuScreen {

    public void show() {
        JPanel mainPanel = createBasePanel("Choose Mode");
        JPanel buttonPanel = createButtonPanel();

        for (ModeGUI modeGUI : ModeGUI.values()) {
            addButtonWithAction(buttonPanel, modeGUI.getModeGUI(), e -> {
                mainFrame.dispose();
                MenuScreen.modeGUI = modeGUI;
                new GameSelectionMenuScreen().show();
            });
        }

        addButtonWithAction(buttonPanel, "EXIT", e -> System.exit(0));
        setupAndShowFrame(mainPanel, buttonPanel, 400, 300);
    }
}
