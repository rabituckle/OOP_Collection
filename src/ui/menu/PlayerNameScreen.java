package ui.menu;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import exception.DuplicatePlayerNameException;
import exception.EmptyPlayerNameException;
import exception.PlayerNameTooLongException;

public class PlayerNameScreen extends MenuScreen {
	
	public static final int MAX_LEN_NAME = 10;

	public PlayerNameScreen(GameConfiguration gameConfig, MenuNavigator navigator) {
		super(gameConfig, navigator);
	}

	@Override
	public void show() {
		int numPlayers = gameConfig.getNumPlayers();
        frame = createFrame("Card Game - Players' Names", 500, 250 + (numPlayers * 40));
        
        // Create main panel with gradient background
        JPanel mainPanel = MenuComponents.createPurpleGradientPanel();
        
        // Create title
        JLabel titleLabel = MenuComponents.createTitleLabel("NAME OF PLAYERS");
        
        // Enter players'name
        JPanel namePanel = new JPanel();
        ArrayList<JTextField> names = new ArrayList<JTextField>();
        namePanel.setBackground(new Color(0, 0, 0, 0));
        namePanel.setLayout(new GridLayout(numPlayers, 1, 0, 0));
        
        for(int i = 1; i <= numPlayers; i++) {
        	final int j = i;
        	JPanel panel = new JPanel();
        	panel.setBackground(new Color(0, 0, 0, 0));
        	
        	JLabel lb = new JLabel("Player " + i + ": ");
        	JTextField name = new JTextField(10);
        	name.setText("Player " + i);
        	name.setPreferredSize(new Dimension(50, 25));
        	
        	// Focus to check empty name
        	name.addFocusListener(new FocusAdapter() {
        	    @Override
        	    public void focusLost(FocusEvent e) {
        	    	try {
        	            String text = name.getText();
        	            // Check for empty name
        	            if (text.isEmpty() || text.trim().isEmpty()) {
        	                throw new EmptyPlayerNameException();
        	            }
        	            // Check for duplicate names
        	            for (JTextField otherField : names) {
                            // Skip comparing with itself
                            if (otherField != name && text.equals(otherField.getText())) {
                                throw new DuplicatePlayerNameException();
                            }
                        }
        	        } catch (EmptyPlayerNameException exception) {
        	            exception.handle(name, j);
        	        } catch (DuplicatePlayerNameException exception) {
        				exception.handle(name, j);
        			}
        	    }
        	});
        	
        	name.getDocument().addDocumentListener(new DocumentListener() {
        		@Override
                public void insertUpdate(DocumentEvent e) {
        			SwingUtilities.invokeLater(() -> {
        	            try {
        	            	String text = name.getText();
        	                // Check for length exceeding maximum
        	                if (text.length() > MAX_LEN_NAME) {
        	                    throw new PlayerNameTooLongException(MAX_LEN_NAME);
        	                }
        	                
        	            } catch (PlayerNameTooLongException exception) {
        	                exception.handle(name, MAX_LEN_NAME);
        	            }
        	        });
                }

                @Override
                public void removeUpdate(DocumentEvent e) {
                	
                }

                @Override
                public void changedUpdate(DocumentEvent e) {
                    
                }
        	});
        	names.add(name);
        	panel.add(lb);
        	panel.add(name);
        	namePanel.add(panel);
        }
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(0, 0, 0, 0));
        panel.add(titleLabel, BorderLayout.NORTH);
        panel.add(namePanel, BorderLayout.CENTER);
        
        // Create button panel
        JPanel buttonPanel = MenuComponents.createButtonPanel();
        
        if (numPlayers == 4) {
            // With 4 players, go directly to game start
            JButton startButton = MenuComponents.createStyledButton("START", 250, 50);
            startButton.addActionListener(e -> {
                savePlayerNames(names);
		this.close();
                gameConfig.startGame();
            });
            buttonPanel.add(startButton);
        } else {
        	// With fewer players, go to bot selection
            JButton nextButton = MenuComponents.createStyledButton("NEXT", 250, 50);
            nextButton.addActionListener(e -> {
                savePlayerNames(names);
                navigator.navigateTo(MenuNavigator.BOT_SELECTION_SCREEN, this);
            });
            buttonPanel.add(nextButton);
        }
        buttonPanel.add(MenuComponents.createButtonSpacing());
        
        // Back button
        JButton backButton = MenuComponents.createStyledButton("BACK", 250, 50);
        backButton.addActionListener(e -> navigator.navigateTo(MenuNavigator.PLAYER_COUNT_SCREEN, this));
        buttonPanel.add(backButton);
        
        mainPanel.add(panel, BorderLayout.NORTH);
        mainPanel.add(buttonPanel, BorderLayout.CENTER);
        
        frame.setContentPane(mainPanel);
        frame.setVisible(true);
	}
	
    private void savePlayerNames(ArrayList<JTextField> nameFields) {
        ArrayList<String> names = new ArrayList<>();
        for (JTextField field : nameFields) {
            names.add(field.getText());
        }
        gameConfig.setPlayerNames(names);
    }
}
