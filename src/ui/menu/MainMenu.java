package ui.menu;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class MainMenu extends MenuScreen{
	
	public MainMenu(GameConfiguration gameConfig, MenuNavigator navigator) {
		super(gameConfig, navigator);
	}
	
	@Override
	public void show() {
		frame = createFrame("Card Game - Main Menu", 400, 320);
		
		// Create main panel
		JPanel mainPanel = MenuComponents.createPurpleGradientPanel();
        
        // Create title
        JLabel titleLabel = MenuComponents.createTitleLabel("MAIN MENU");
        
        // Create button panel
        JPanel buttonPanel = MenuComponents.createButtonPanel();
        
        // Add mode selection buttons
        String[] menuItems = {"GRAPHICAL MODE", "BASIC MODE"};
        for (int i = 0; i < menuItems.length; i++) {
            final int mode = i;
            JButton button = MenuComponents.createStyledButton(menuItems[i], 250, 50);
            button.addActionListener(e -> {
                gameConfig.setModeGUI(mode);
                navigator.navigateTo(MenuNavigator.GAME_SELECTION_SCREEN, this);
            });
            buttonPanel.add(button);
            buttonPanel.add(MenuComponents.createButtonSpacing());
        }
        
        JButton exitButton = MenuComponents.createStyledButton("EXIT", 250, 50);
        exitButton.addActionListener(e -> System.exit(0));
        buttonPanel.add(exitButton);
        
        // Add components to the main panel
        mainPanel.add(titleLabel, BorderLayout.NORTH);
        mainPanel.add(buttonPanel, BorderLayout.CENTER);
        
        // Set up and display the frame
        frame.setContentPane(mainPanel);
        frame.setVisible(true);
	}
}
