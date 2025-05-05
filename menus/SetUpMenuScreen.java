package ui.menus;

import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;
import javax.swing.*;

import enums.ModeGUI;
import utilities.player.Bot;
import utilities.player.Human;
import utilities.player.Player;

public class SetUpMenuScreen extends MenuScreen {

    private ArrayList<JTextField> nameFields = new ArrayList<>();
    private ArrayList<JTextField> passwordFields = new ArrayList<>();
    private JTextField selectionBots;

    public void show() {
        JPanel mainPanel = createBasePanel("Game Setup");
        JPanel buttonPanel = createButtonPanel();
        
        MenuScreen.minBots = (MenuScreen.numHumans == 1) ? 1 : 0;
        MenuScreen.maxBots = MAX_PLAYERS - MenuScreen.numHumans;
        
        JPanel playerInfoPanel = createPlayerInfoPanel(MenuScreen.numHumans);
        buttonPanel.add(playerInfoPanel);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, SPACING)));

        if (MenuScreen.numHumans < MAX_PLAYERS) {
            JPanel botsControlPanel = createBotsControlPanel(MenuScreen.minBots);
            buttonPanel.add(botsControlPanel);
            buttonPanel.add(Box.createRigidArea(new Dimension(0, SPACING)));
        }

        addButtonWithAction(buttonPanel, "START", e -> onStart(MenuScreen.numHumans));
        addButtonWithAction(buttonPanel, "BACK", e -> onBack());

        setupAndShowFrame(mainPanel, buttonPanel, 500, 500);
    }

    private JPanel createPlayerInfoPanel(int numPlayers) {
        JPanel playerInfoPanel = new JPanel();
        playerInfoPanel.setLayout(new BoxLayout(playerInfoPanel, BoxLayout.Y_AXIS));
        playerInfoPanel.setOpaque(false);
        playerInfoPanel.setAlignmentX(JPanel.CENTER_ALIGNMENT);
        playerInfoPanel.setBorder(BorderFactory.createEmptyBorder(0, 20, 20, 20));

        for (int i = 0; i < MAX_PLAYERS; i++) {
            JPanel playerPanel = new JPanel();
            playerPanel.setLayout(new BoxLayout(playerPanel, BoxLayout.X_AXIS));
            playerPanel.setOpaque(false);
            playerPanel.setMaximumSize(new Dimension(400, 40));
            playerPanel.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));

            JLabel playerLabel = new JLabel("Player " + (i + 1) + ": ");
            playerLabel.setForeground(Color.WHITE);
            playerLabel.setPreferredSize(new Dimension(80, 30));

            JTextField nameField = new JTextField("Player" + (i + 1), 10);
            nameField.setPreferredSize(new Dimension(120, 30));

            JTextField passwordField = new JTextField("", 10);
            passwordField.setPreferredSize(new Dimension(120, 30));

            playerPanel.add(playerLabel);
            playerPanel.add(nameField);
            playerPanel.add(Box.createRigidArea(new Dimension(10, 0)));
            playerPanel.add(passwordField);

            playerPanel.setVisible(i < numPlayers);

            nameFields.add(nameField);
            passwordFields.add(passwordField);
            playerInfoPanel.add(playerPanel);
        }

        return playerInfoPanel;
    }

    private JPanel createBotsControlPanel(int numBots) {
        JPanel botsControlPanel = new JPanel();
        botsControlPanel.setOpaque(false);
        botsControlPanel.setMaximumSize(new Dimension(300, 40));
        botsControlPanel.setAlignmentX(JPanel.CENTER_ALIGNMENT);

        JLabel botsLabel = new JLabel("BOTS: ");
        botsLabel.setForeground(Color.WHITE);

        selectionBots = new JTextField(String.valueOf(numBots), 3);
        if (numBots == 1) {
            bots.add(new Bot("Bot " + (bots.size() + 1)));
        }
        selectionBots.setPreferredSize(new Dimension(40, 30));
        selectionBots.setHorizontalAlignment(JTextField.CENTER);
        selectionBots.setEditable(false);

        JButton raiseButton = createStyledButton("+", 30, 30);
        
        raiseButton.addActionListener(e -> {
        	//System.out.println("Raise button clicked!");
            if (bots.size() < maxBots) {
                bots.add(new Bot("Bot " + (bots.size() + 1)));
                selectionBots.setText(String.valueOf(bots.size()));
                bots.forEach(bot -> System.out.println(bot.getName()));
            }
        });

        JButton reduceButton = createStyledButton("-", 30, 30);
        reduceButton.addActionListener(e -> {
            if (bots.size() > minBots) {
                bots.remove(bots.size() - 1);
                selectionBots.setText(String.valueOf(bots.size()));
                bots.forEach(bot -> System.out.println(bot.getName()));
            }
        });

        botsControlPanel.add(botsLabel);
        botsControlPanel.add(selectionBots);
        botsControlPanel.add(raiseButton);
        botsControlPanel.add(reduceButton);

        return botsControlPanel;
    }

    private void onStart(int numPlayers) {
        mainFrame.dispose();
        for (int i = 0; i < numPlayers; i++) {
            String name = nameFields.get(i).getText();
            String password = passwordFields.get(i).getText();
            humans.add(new Human(name, password));
        }
        typeGame.startGame(modeGUI, humans, bots, bots.size());
    }

    private void onBack() {
        mainFrame.dispose();
        new NumberPlayerMenuScreen().show(); 
    }
}
