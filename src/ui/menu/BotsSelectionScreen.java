package ui.menu;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class BotsSelectionScreen extends MenuScreen {

	public BotsSelectionScreen(GameConfiguration gameConfig, MenuNavigator navigator) {
		super(gameConfig, navigator);
	}

	@Override
	public void show() {
		frame = createFrame("Card Game - Number of bots", 500, 380);
        
        // Create main panel with gradient background
        JPanel mainPanel = MenuComponents.createPurpleGradientPanel();
        
        // Create title
        JLabel titleLabel = MenuComponents.createTitleLabel("NUMBER OF BOTS");
        
        // Create button panel
        JPanel buttonPanel = MenuComponents.createButtonPanel();
        
        JTextField selectionBots = new JTextField(gameConfig.getNumBots() + "", 10);
        selectionBots.setPreferredSize(new Dimension(50, 30));
        selectionBots.setEditable(false);
        
        // Increase button
        JButton raiseButton = MenuComponents.createStyledButton("+", 30, 30);
        raiseButton.addActionListener(e -> {
        	if (gameConfig.increaseBots()) {
                selectionBots.setText(gameConfig.getNumBots() + "");
            }
        });
        
        // Decrease button
        JButton decreaseButton = MenuComponents.createStyledButton("-", 30, 30);
        decreaseButton.addActionListener(e -> {
            if (gameConfig.decreaseBots()) {
                selectionBots.setText(gameConfig.getNumBots() + "");
            }
        });
        
        JPanel botsArea = new JPanel();
        botsArea.setBackground(new Color(72, 42, 102));
        botsArea.setPreferredSize(new Dimension(250, 40));
        botsArea.setMaximumSize(new Dimension(250, 40));
        botsArea.add(selectionBots);
        botsArea.add(raiseButton);
        botsArea.add(decreaseButton);
        buttonPanel.add(botsArea);
        buttonPanel.add(MenuComponents.createButtonSpacing());
        
        // Start button
        JButton startButton = MenuComponents.createStyledButton("START", 250, 50);
        startButton.addActionListener(e -> {
        	this.close();
            gameConfig.startGame();
        });
        buttonPanel.add(startButton);
        buttonPanel.add(MenuComponents.createButtonSpacing());
        
        // Back button
        JButton backButton = MenuComponents.createStyledButton("BACK", 250, 50);
        backButton.addActionListener(e -> navigator.navigateTo(MenuNavigator.PLAYER_NAMES_SCREEN, this));
        buttonPanel.add(backButton);
        
        mainPanel.add(titleLabel, BorderLayout.NORTH);
        mainPanel.add(buttonPanel, BorderLayout.CENTER);
        
        frame.setContentPane(mainPanel);
        frame.setVisible(true);
	}

}
