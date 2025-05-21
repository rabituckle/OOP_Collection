package ui;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.RenderingHints;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.geom.RoundRectangle2D;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import exception.DuplicatePlayerNameException;
import exception.EmptyPlayerNameException;
import exception.PlayerNameTooLongException;
import game.BaCay;
import game.TienLen;
import game.TienLenMienBac;
import game.TienLenMienNam;

//import utils.BasicModePlayer;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;

public class MainMenu{
    private int modeGUI;
    private int typeGame;
    private int numPlayers;
    private int numBots;
    private int minBots, maxBots;
    private JFrame mainFrame;
    private ArrayList<String> namePlayer = new ArrayList<String>();
    
    public MainMenu() {
    	showMainMenu();
    }
    
    private JPanel createPurpleGradientPanel() {
        JPanel panel = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                // Create purple gradient background
                GradientPaint gradient = new GradientPaint(
                    0, 0, new Color(194, 19, 194), // Light purple
                    0, getHeight(), new Color(70, 27, 147)  // Dark purple
                );
                g2d.setPaint(gradient);
                
                // Draw rounded rectangle for the panel
                RoundRectangle2D roundedRectangle = new RoundRectangle2D.Float(
                    10, 10, getWidth() - 20, getHeight() - 20, 30, 30
                );
                g2d.fill(roundedRectangle);
                
                // Draw border
                g2d.setColor(new Color(160, 90, 200));
                g2d.draw(roundedRectangle);
                
                g2d.dispose();
            }
        };
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        panel.setOpaque(false);
        return panel;
    }
    
    private JButton createStyledButton(String text, int x, int y) {
        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                // Draw button background - light gray to dark gray gradient
                GradientPaint gradient = new GradientPaint(
                    0, 0, new Color(230, 230, 230, 200),
                    0, getHeight(), new Color(120, 120, 120, 200)
                );
                g2d.setPaint(gradient);
                
                // Create rounded rectangle for button
                RoundRectangle2D roundedRectangle = new RoundRectangle2D.Float(
                    0, 0, getWidth() - 1, getHeight() - 1, 25, 25
                );
                g2d.fill(roundedRectangle);
                
                // Draw button border
                g2d.setColor(new Color(150, 150, 150));
                g2d.draw(roundedRectangle);
                
                // Draw text
                g2d.setColor(new Color(60, 60, 60));
                g2d.setFont(getFont());
                
                // Center text
                g2d.setFont(new Font("Arial", Font.BOLD, 18));
                g2d.drawString(text, 
                    (getWidth() - g2d.getFontMetrics().stringWidth(text)) / 2,
                    (getHeight() + g2d.getFontMetrics().getAscent() - g2d.getFontMetrics().getDescent()) / 2);
                
                g2d.dispose();
            }
        };
        
        // Configure button properties
        button.setFont(new Font("Arial", Font.BOLD, 18));
        button.setPreferredSize(new Dimension(x, y));
        button.setMaximumSize(new Dimension(x, y));
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setAlignmentX(JButton.CENTER_ALIGNMENT);
        
        return button;
    }
    
    private void showMainMenu() {
        mainFrame = new JFrame("Card Game - Main Menu");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // Create purple gradient background panel
        JPanel mainPanel = createPurpleGradientPanel();
        
        // Title
        JLabel titleLabel = new JLabel("MAIN MENU", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 32));
        titleLabel.setForeground(Color.WHITE);
        
        // Menu buttons panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setOpaque(false);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));
        
        // Add main menu buttons
        String[] menuItems = {"GRAPHICAL MODE", "BASIC MODE"};
        
        for (int i = 0; i < menuItems.length; i++) {
        	int mode = i;
            JButton button = createStyledButton(menuItems[i], 250, 50);
            button.addActionListener(e -> {
            	mainFrame.dispose();
                showGameSelectionScreen();
                this.modeGUI = mode;
        });
            buttonPanel.add(button);
            buttonPanel.add(Box.createRigidArea(new Dimension(0, 15))); // Spacing between buttons
        }
        // Add exit button
        JButton button = createStyledButton("EXIT", 250, 50);
        button.addActionListener(e -> {
        	System.exit(0);
    });
        buttonPanel.add(button);
        
        // Add components to the main panel
        mainPanel.add(titleLabel, BorderLayout.NORTH);
        mainPanel.add(buttonPanel, BorderLayout.CENTER);
        
        // Set up and display the frame
        mainFrame.setContentPane(mainPanel);
        mainFrame.setSize(400, 320);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setResizable(false);
        mainFrame.setVisible(true);
    }
    /**
     * Shows the game mode selection screen
     */
    private void showGameSelectionScreen() {
        mainFrame = new JFrame("Card Game - Game Selection");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // Create purple gradient background panel
        JPanel mainPanel = createPurpleGradientPanel();
        
        // Title
        JLabel titleLabel = new JLabel("SELECT GAME MODE", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 32));
        titleLabel.setForeground(Color.WHITE);
        
        // Game selection buttons panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setOpaque(false);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));
        
        // Game buttons
        String[] modeNames = {"BA CAY", "TLMN", "TLMB"};
        for (int i = 0; i < modeNames.length; i++) {
            final int value = i;
            JButton button = createStyledButton(modeNames[i], 250, 50);
            button.addActionListener(e -> {
                this.typeGame = value;
                mainFrame.dispose();
                showNumberPlayersMenu();
            });
            
            buttonPanel.add(button);
            buttonPanel.add(Box.createRigidArea(new Dimension(0, 15))); // Spacing between buttons
        }
        
        // Back button
        JButton backButton = createStyledButton("BACK", 250, 50);
        backButton.addActionListener(e -> {
            mainFrame.dispose();
            showMainMenu();
        });
        buttonPanel.add(backButton);
        
        // Add components to the main panel
        mainPanel.add(titleLabel, BorderLayout.NORTH);
        mainPanel.add(buttonPanel, BorderLayout.CENTER);
        
        // Set up and display the frame
        mainFrame.setContentPane(mainPanel);
        mainFrame.setSize(500, 450);
        mainFrame.setResizable(false);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);
    }
    
    private void showNumberPlayersMenu() {
    	mainFrame = new JFrame("Card Game - Number of players selection");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // Create purple gradient background panel
        JPanel mainPanel = createPurpleGradientPanel();
        
        // Title
        JLabel titleLabel = new JLabel("NUMBER OF PLAYERS", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 32));
        titleLabel.setForeground(Color.WHITE);
        
        // Mode selection buttons panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setOpaque(false);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));
        
        // Mode buttons
        String[] modeNames = {"1 PLAYERS", "2 PLAYERS", "3 PLAYERS", "4 PLAYERS"};
        for (int i = 0; i < modeNames.length; i++) {
            int value = i + 1;
            JButton button = createStyledButton(modeNames[i], 250, 50);
            	button.addActionListener(e -> {
                    this.numPlayers = value;
                    if(numPlayers == 1) {
                    	minBots = 1;
                    }
                    else {
                    	minBots = 0;
                    }
                    maxBots = 4 - numPlayers;
                    numBots = minBots;
                    mainFrame.dispose();
                    fillPlayerName();
                });
            buttonPanel.add(button);
            buttonPanel.add(Box.createRigidArea(new Dimension(0, 15))); // Spacing between buttons
        }
        
        // Back button
        JButton backButton = createStyledButton("BACK", 250, 50);
        backButton.addActionListener(e -> {
            mainFrame.dispose();
            showGameSelectionScreen();
        });
        buttonPanel.add(backButton);
        
        // Add components to the main panel
        mainPanel.add(titleLabel, BorderLayout.NORTH);
        mainPanel.add(buttonPanel, BorderLayout.CENTER);
        
        // Set up and display the frame
        mainFrame.setContentPane(mainPanel);
        mainFrame.setSize(500, 500);
        mainFrame.setResizable(false);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);
    }
    
    private void fillPlayerName() {
    	mainFrame = new JFrame("Card Game - Players'name");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // Create purple gradient background panel
        JPanel mainPanel = createPurpleGradientPanel();
        // Title
        JLabel titleLabel = new JLabel("NAME OF PLAYERS", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 32));
        titleLabel.setForeground(Color.WHITE);
        
        // Enter players'name
        JPanel namePanel = new JPanel();
        ArrayList<JTextField> names = new ArrayList<JTextField>();
        namePanel.setBackground(new Color(0, 0, 0, 0));
        namePanel.setLayout(new GridLayout(numPlayers, 1, 0, 0));
        for(int i = 1; i <= numPlayers; i++) {
        	int j = i;
        	JPanel panel = new JPanel();
        	panel.setBackground(new Color(0, 0, 0, 0));
        	JLabel lb = new JLabel("Player " + i + ": ");
        	JTextField name = new JTextField(10);
        	name.setText("Player " + i);
        	name.setPreferredSize(new Dimension(50, 25));
        	name.addFocusListener(new FocusAdapter() {
        	    @Override
        	    public void focusLost(FocusEvent e) {
        	    	try {
        	            String text = name.getText();
        	            // Check for empty name
        	            if (text.isEmpty() || text.trim().isEmpty()) {
        	                throw new EmptyPlayerNameException();
        	            }
        	        } catch (EmptyPlayerNameException exception) {
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
        	                if (text.length() > 10) {
        	                    throw new PlayerNameTooLongException(10);
        	                }
        	                
        	                for (JTextField otherField : names) {
    	                        // Skip comparing with itself
    	                        if (otherField != name && text.equalsIgnoreCase(otherField.getText())) {
    	                            throw new DuplicatePlayerNameException();
    	                        }
    	                    }
        	                    
        	            } catch (PlayerNameTooLongException exception) {
        	                exception.handle(name, 10);
        	            } catch (DuplicatePlayerNameException exception) {
							// TODO Auto-generated catch block
							exception.handle(name, j);
						}
        	            
        	        });
        	    }

        	    @Override
        	    public void removeUpdate(DocumentEvent e) {
        	        // No need to check when removing characters
        	    }

        	    @Override
        	    public void changedUpdate(DocumentEvent e) {
        	        // Not used for plain text components
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
        
        
     // Mode selection buttons panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setOpaque(false);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));
        // Start button
        if(numPlayers == 4) {
        	JButton startButton = createStyledButton("START", 250, 50);
        	startButton.addActionListener(e -> {
                mainFrame.dispose();
                for(int i = 0; i < numPlayers; i++) {
                	namePlayer.add(names.get(i).getText());
                }
                startGame();
            });
            buttonPanel.add(startButton);
        }
        else {
        	JButton startButton = createStyledButton("NEXT", 250, 50);
        	startButton.addActionListener(e -> {
                mainFrame.dispose();
                for(int i = 0; i < numPlayers; i++) {
                	namePlayer.add(names.get(i).getText());
                }
                showChoseBotsMenu();
            });
            buttonPanel.add(startButton);
        }
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 15))); // Spacing between buttons
        
        // Back button
        JButton backButton = createStyledButton("BACK", 250, 50);
        backButton.addActionListener(e -> {
            mainFrame.dispose();
            showNumberPlayersMenu();
        });
        buttonPanel.add(backButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 15))); // Spacing between buttons
        
        mainPanel.add(panel, BorderLayout.NORTH);
        mainPanel.add(buttonPanel);
        
        // Set up and display the frame
        mainFrame.setContentPane(mainPanel);
        mainFrame.setVisible(true);
        mainFrame.setSize(500, 250 + panel.getHeight());
        mainFrame.setResizable(false);
        mainFrame.setLocationRelativeTo(null);
    }
    
    private void showChoseBotsMenu() {
    	mainFrame = new JFrame("Card Game - Number of bots");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // Create purple gradient background panel
        JPanel mainPanel = createPurpleGradientPanel();
        // Title
        JLabel titleLabel = new JLabel("NUMBER OF BOTS", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 32));
        titleLabel.setForeground(Color.WHITE);
        
        // Mode selection buttons panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setOpaque(false);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));
        
        JTextField selectionBots = new JTextField(numBots + "", 10);
        selectionBots.setPreferredSize(new Dimension(50, 30));
        selectionBots.setEditable(false);
        // Increase button
        JButton raiseButton = createStyledButton("+", 30, 30);
        raiseButton.addActionListener(e -> {
            if(numBots < maxBots) {
         	   numBots++;
         	   selectionBots.setText(numBots + "");
            }
         });
        // Decrease button
        JButton reduceButton = createStyledButton("-", 30, 30);
        reduceButton.addActionListener(e -> {
           if(numBots > minBots) {
        	   numBots--;
        	   selectionBots.setText(numBots + "");
           }
        });
        
        JPanel botsArea = new JPanel();
        botsArea.setBackground(new Color(72, 42, 102));
        botsArea.setPreferredSize(new Dimension(250, 40));
        botsArea.setMaximumSize(new Dimension(250, 40));
        botsArea.add(selectionBots);
        botsArea.add(raiseButton);
        botsArea.add(reduceButton);
        buttonPanel.add(botsArea);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 15))); // Spacing between buttons
        
        // Start button
        JButton startButton = createStyledButton("START", 250, 50);
        startButton.addActionListener(e -> {
            mainFrame.dispose();
            startGame();
        });
        buttonPanel.add(startButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 15))); // Spacing between buttons
        
        // Back button
        JButton backButton = createStyledButton("BACK", 250, 50);
        backButton.addActionListener(e -> {
            mainFrame.dispose();
            showNumberPlayersMenu();
        });
        buttonPanel.add(backButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 15))); // Spacing between buttons
        
        // Add components to the main panel
        mainPanel.add(titleLabel, BorderLayout.NORTH);
        mainPanel.add(buttonPanel, BorderLayout.CENTER);
        
        // Set up and display the frame
        mainFrame.setContentPane(mainPanel);
        mainFrame.setSize(500, 380);
        mainFrame.setResizable(false);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);
    }
    
    private void startGame() {
    	if(typeGame == 0) {
    		BaCay game = new BaCay(modeGUI, numPlayers, numBots, namePlayer);
    		game.gameStart();
    	}
    	else if(typeGame == 1) {
    		TienLen game = new TienLenMienNam(modeGUI, numPlayers, numBots, namePlayer);
    		game.gameStart();
    	}
    	else if(typeGame == 2) {
    		TienLen game = new TienLenMienBac(modeGUI, numPlayers, numBots, namePlayer);
    		game.gameStart();
    	}
    }
}