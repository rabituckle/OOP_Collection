package ui;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.RoundRectangle2D;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

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
    
    public MainMenu() {
    	showMainMenu();
    }
    
    /**
     * Creates a panel with purple gradient background
     * 
     * @return The configured panel
     */
    
    public JPanel createPurpleGradientPanel() {
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
    
    public JButton createStyledButton(String text, int x, int y) {
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
    
    public void showMainMenu() {
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
        mainFrame.setSize(400, 300);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setResizable(false);
        mainFrame.setVisible(true);
    }
    /**
     * Shows the game mode selection screen
     */
    public void showGameSelectionScreen() {
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
    
    public void showNumberPlayersMenu() {
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
            if(value < 4) {
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
                    showChoseBotsMenu();
                });
            }
            else {
            	button.addActionListener(e -> {
                    this.numPlayers = value;
                    mainFrame.dispose();
                    startGame();
                });
            }
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
    
    public void showChoseBotsMenu() {
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
    
    public void startGame() {
    	if(typeGame == 0) {
    		
    	}
    	else if(typeGame == 1) {
    		TienLen game = new TienLenMienNam(modeGUI, numPlayers, numBots);
    		game.gameStart();
    	}
    	else if(typeGame == 2) {
    		TienLen game = new TienLenMienBac(modeGUI, numPlayers, numBots);
    		game.gameStart();
    	}
    }
    
    public static void main(String[] args) {
        // Start the application with main menu screen
    	new MainMenu();
    }
}