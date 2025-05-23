package ui.menu;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class GameSelectionScreen extends MenuScreen {
	
	public GameSelectionScreen(GameConfiguration gameConfig, MenuNavigator navigator) {
        super(gameConfig, navigator);
    }

	@Override
	public void show() {
		frame = createFrame("Card Game - Game Selection", 500, 450);
        
        // Create main panel
        JPanel mainPanel = MenuComponents.createPurpleGradientPanel();
        
        // Create title
        JLabel titleLabel = MenuComponents.createTitleLabel("SELECT GAME MODE");
        
        // Create button panel
        JPanel buttonPanel = MenuComponents.createButtonPanel();
        
        // Add game type buttons
        String[] modeNames = {"BA CAY", "TLMN", "TLMB"};
        
        for (int i = 0; i < modeNames.length; i++) {
            final int gameType = i;
            JButton button = MenuComponents.createStyledButton(modeNames[i], 250, 50);
            button.addActionListener(e -> {
                gameConfig.setTypeGame(gameType);
                navigator.navigateTo(MenuNavigator.PLAYER_COUNT_SCREEN, this);
            });
            
            buttonPanel.add(button);
            buttonPanel.add(MenuComponents.createButtonSpacing());
        }
        
        // Add back button
        JButton backButton = MenuComponents.createStyledButton("BACK", 250, 50);
        backButton.addActionListener(e -> navigator.navigateTo(MenuNavigator.MAIN_MENU_SCREEN, this));
        buttonPanel.add(backButton);
        
        mainPanel.add(titleLabel, BorderLayout.NORTH);
        mainPanel.add(buttonPanel, BorderLayout.CENTER);
        
        frame.setContentPane(mainPanel);
        frame.setVisible(true);
	}
}
