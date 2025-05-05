package ui.menus;

import javax.swing.JPanel;

import enums.ModeGUI;

public class MainMenuScreen extends MenuScreen {

    public void show() {
        JPanel mainPanel = createBasePanel("Main Menu");
        JPanel buttonPanel = createButtonPanel();

        addButtonWithAction(buttonPanel, "REGISTER", e -> {
            mainFrame.dispose();
            new RegisterMenuScreen().show();
        });
        
        addButtonWithAction(buttonPanel, "CHOOSE MODE", e -> {
            mainFrame.dispose();
            new ChooseModeScreen().show();
        });
        

        addButtonWithAction(buttonPanel, "HIGH SCORE", e -> {
            mainFrame.dispose();
        });
        setupAndShowFrame(mainPanel, buttonPanel, 400, 300);
    }
}
