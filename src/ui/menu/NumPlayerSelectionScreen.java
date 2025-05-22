package ui.menu;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class NumPlayerSelectionScreen extends MenuScreen {

	public NumPlayerSelectionScreen(GameConfiguration gameConfig, MenuNavigator navigator) {
		super(gameConfig, navigator);
	}

	@Override
	public void show() {
		frame = createFrame("Card Game - Number of players selection", 500, 500);
        
        // Create main panel
        JPanel mainPanel = MenuComponents.createPurpleGradientPanel();
        
        // Create title
        JLabel titleLabel = MenuComponents.createTitleLabel("NUMBER OF PLAYERS");
        
        // Create button panel
        JPanel buttonPanel = MenuComponents.createButtonPanel();
        
        // Add player count buttons
        String[] modeNames = {"1 PLAYER", "2 PLAYERS", "3 PLAYERS", "4 PLAYERS"};
        
        for (int i = 0; i < modeNames.length; i++) {
            final int playerCount = i + 1;
            JButton button = MenuComponents.createStyledButton(modeNames[i], 250, 50);
            button.addActionListener(e -> {
                gameConfig.setNumPlayers(playerCount);
                navigator.navigateTo(MenuNavigator.PLAYER_NAMES_SCREEN, this);
            });
            buttonPanel.add(button);
            buttonPanel.add(MenuComponents.createButtonSpacing());
        }
        
        // Add back button
        JButton backButton = MenuComponents.createStyledButton("BACK", 250, 50);
        backButton.addActionListener(e -> navigator.navigateTo(MenuNavigator.GAME_SELECTION_SCREEN, this));
        buttonPanel.add(backButton);
        
        mainPanel.add(titleLabel, BorderLayout.NORTH);
        mainPanel.add(buttonPanel, BorderLayout.CENTER);
        
        frame.setContentPane(mainPanel);
        frame.setVisible(true);
	}

}
