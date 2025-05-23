package game;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Map;

import rule.TienLenRule;
import ui.game.GameUI;
import ui.menu.GameConfiguration;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import bot.TienLenBot;
import exception.NegativeScoreException;
import utilities.card.Card;
import utilities.deck.DeckPlayingCard;
import utilities.player.Label;
import utilities.player.Player;
import utilities.player.State;

public abstract class TienLen extends ControlGame{

	private JButton btnPass = new JButton ("Pass");
	private JButton btnShow = new JButton("Show");
	private int numPass = 0;
	private String firstTurn;
	private int numChosen = 0;
	private JLabel[] score;
	protected TienLenRule rule;
	protected Thread thread;
	protected TienLenBot bot;

	public TienLen(int mode, int numHuman, int numBots, ArrayList<String> name) {
		super(mode, numHuman, numBots, name);
		score = new JLabel[getNumPlayers()];
		deckObj = new DeckPlayingCard();
		chooseModeGUI();
		setMaxCards(13);
		initialize();
		newGame();
		ui.getFrame().add(controlPanel(), BorderLayout.EAST);
		ui.getFrame().add(this);
		ui.getFrame().setLocationRelativeTo(null);
		ui.getFrame().setVisible(true);
	}
	
	public abstract void gameStart();

	@Override
	public MouseAdapter handleInput() {
		return new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Player hand = getPlayer();
				Map<Card, Rectangle> mapCard = hand.getMapCard();
				ArrayList<Integer> cardState = hand.getCardState();
				if(show == 1) {
					for(int i = 0; i < hand.getNumCards(); i++) {
						if(mapCard.get(hand.getCard(i)).contains(e.getPoint())) {
							if(cardState.get(i) == 0) { // If card hasn't been chosen, put it up
								numChosen++;
								cardState.set(i, 1);
							}
							else if(cardState.get(i) == 1) { // If card was chose, put id down
								numChosen--;
								cardState.set(i, 0);
							}
						}
					}
					if(numChosen == 0) {
						btnPlay.setEnabled(false);
						if(previousCard.size() == 0) {
							btnPass.setEnabled(false);
						}
						else {
							btnPass.setEnabled(true);
						}
					}
					else if(numChosen > 0) {
						int i = 0;
						chosenCard.clear();
						while(i < hand.getNumCards()) {
							if(cardState.get(i) == 1) {
								chosenCard.add(hand.getCard(i));
							}
							i++;
						}
						if(rule.isValid(chosenCard) && previousCard.size() == 0){
							btnPlay.setEnabled(true);
						}
						else if(rule.canBeat(previousCard, chosenCard)) {
							btnPlay.setEnabled(true);
						}
						else {
							btnPlay.setEnabled(false);
						}
						btnPass.setEnabled(false);
					}
				}
			}
		};
	}
	
	@Override
	public JPanel controlPanel() {
	    // Main control panel with 3 buttons
	    JPanel panel = new JPanel(new GridLayout(3, 1, 8, 8));
	    panel.setBackground(new Color(240, 240, 250));
	    
	    // Set up the control buttons with improved styling
	    setFunctionButtonPlay();
	    setFunctionButtonPass();
	    setFunctionButtonShow();
	    
	    btnPlay.setEnabled(false);
	    btnPass.setEnabled(false);

	    // Add buttons to panel with proper spacing
	    panel.add(btnShow);
	    panel.add(btnPlay);
	    panel.add(btnPass);
	    
	    // Create the main board with a nice border
	    JPanel board = new JPanel();
	    board.setLayout(new BorderLayout(10, 10));
	    board.setBorder(BorderFactory.createCompoundBorder(
	        BorderFactory.createEmptyBorder(10, 10, 10, 10),
	        BorderFactory.createCompoundBorder(
	            BorderFactory.createLineBorder(new Color(100, 100, 150), 2),
	            BorderFactory.createEmptyBorder(10, 10, 10, 10)
	        )
	    ));
	    board.setBackground(new Color(235, 235, 250)); // Light purple-gray
	    
	    // Add a title label at the top
	    JLabel titleLabel = new JLabel("GAME CONTROLS", JLabel.CENTER);
	    titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
	    titleLabel.setForeground(new Color(25, 25, 112));
	    titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
	    
	    // Create panel for buttons with proper title
	    JPanel controlsPanel = new JPanel(new BorderLayout());
	    controlsPanel.setBackground(new Color(240, 240, 250));
	    controlsPanel.setBorder(BorderFactory.createCompoundBorder(
	        BorderFactory.createEmptyBorder(5, 5, 5, 5),
	        BorderFactory.createTitledBorder(
	            BorderFactory.createLineBorder(new Color(100, 100, 150)),
	            "ACTIONS",
	            TitledBorder.CENTER,
	            TitledBorder.TOP,
	            new Font("Arial", Font.BOLD, 14),
	            new Color(25, 25, 112)
	        )
	    ));
	    controlsPanel.add(panel, BorderLayout.NORTH);
	    
	    // Add components to the main board
	    board.add(titleLabel, BorderLayout.NORTH);
	    JPanel pn = new JPanel(new BorderLayout());
	    pn.add(controlsPanel, BorderLayout.NORTH);
	    pn.add(createScorePanel(), BorderLayout.CENTER);
	    board.add(pn, BorderLayout.CENTER);
	    
	    return board;
	}
	
	@Override
	public void newGame() {
		// TODO Auto-generated method stub
		getPlayerCards();
		for(int i = 0; i < getNumPlayers(); i++) {
			player.get(i).setState(State.PLAY);
			score[i].setText(player.get(i).getScore() + "");
			
		}
		previousCard.clear();
		numPass = 0;
		show = 0;
		numChosen = 0;
		chosenCard.clear();
		firstTurn = null;
		newRound();
		if(getPlayer().getLabel() == Label.HUMAN) {
			btnShow.setEnabled(true);
		}
		else {
			btnShow.setEnabled(false);
		}
	}
	
	@Override
	public void endGame() {
		changeScore();
		int select = JOptionPane.showOptionDialog(null, "Do you want to have a new game", firstTurn + " won!",
				JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,
							null, null);
		if(select == 0) {
			newGame();
		}
		else {
			ui.getFrame().dispose();
			new GameConfiguration().startMenuSystem();
			thread = null;
		}
	}
	
	@Override
	public void nextTurn() {
		if(checkWin()) {
			endGame();
			return;
		}
		do {
			setTurn((getTurn() + 1) % getNumPlayers());
		}while(getPlayer().getState() != State.PLAY);
		
		if(getNumPlayers() == numPass + 1) {
			newRound();
		}
		
		show = 0;
		if(getPlayer().getLabel() == Label.HUMAN) {
			btnShow.setEnabled(true);
		}
		else {
			btnShow.setEnabled(false);
		}
	}
	
	public void initialize() {
		super.initialize();
		for(int i = 0; i < getNumPlayers(); i++) {
			score[i] = new JLabel();
			player.get(i).setScore(500);
		}
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		GameUI.g2 = (Graphics2D) g;
        ui.drawBackground(this);
        ui.drawTurn(this);
        ui.drawChosenCards(previousCard, this);
        if(show == 0 && firstTurn == null) {
        	ui.drawHidingState(this, 25, 25);
        }
        else if(show == 1 && firstTurn == null) {
        	ui.drawStatePlaying(this, 25, 25);
        	if(getTurn() == 0 || getTurn() == 2) {
        		ui.drawCardsHorizontal(this, 25);
        	}
        	if(getTurn() == 1 || getTurn() == 3) {
        		ui.drawCardsVertical(this, 30);
        	}
        }
        if(firstTurn != null) {
        	int savedTurn = getTurn();
        	for (int i = 0; i < getNumPlayers(); i++) {
                setTurn(i);
                if(getTurn() == 0 || getTurn() == 2) {
            		ui.drawCardsHorizontal(this, 25);
            	}
            	if(getTurn() == 1 || getTurn() == 3) {
            		ui.drawCardsVertical(this, 30);
            	}
            }
        	setTurn(savedTurn);
        }
	}
	
	protected void execution() {
		repaint();
		// Only handle bot actions in this thread
		if(getPlayer().getLabel() == Label.BOT) {
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			botCards = bot.engineCard(getPlayer().getCards(), previousCard);
			if(botCards == null) {
				getPlayer().setState(State.PASS);
				numPass++;
			}
			else {
				previousCard = botCards;
			}
			repaint();
			nextTurn();
		} 
				
		// Add a small sleep to prevent high CPU usage
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	private void newRound() {
		for(int i = 0; i <= 3; i++) {
			int idx = (getTurn() + i) % getNumPlayers();
			if(player.get(idx).getState() != State.WIN) {
				player.get(idx).setState(State.PLAY);
			}
		}
		previousCard.clear();
		numPass = 0;
	}
	
	private void setFunctionButtonPlay() {
		btnPlay.addActionListener(e -> {
			previousCard = new ArrayList<Card>(chosenCard);
			deleteChosenCards();
			nextTurn();
			btnPlay.setEnabled(false);
			numChosen = 0;
		});
	}
	
	private void setFunctionButtonPass() {
		btnPass.addActionListener(e -> {
			getPlayer().setState(State.PASS);
			numPass++;
			nextTurn();
			btnPass.setEnabled(false);
		});
	}
	
	private void setFunctionButtonShow() {
		btnShow.addActionListener(e -> {
			show = 1;
			btnShow.setEnabled(false);
			if(previousCard.size() == 0) {
				btnPass.setEnabled(false);
			}
			else {
				btnPass.setEnabled(true);
			}
		});
	}
	
	private JPanel scorePanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		for(Player p : player) {
			JLabel lb = new JLabel(p.getName() + ": " + p.getScore() + " scores");
			panel.add(lb);
		}
		return panel;
	}
	
	private JPanel createScorePanel() {
	    // Create the betting panel with a gradient background
	    JPanel bettingPanel = new JPanel() {
	        @Override
	        protected void paintComponent(Graphics g) {
	            super.paintComponent(g);
	            Graphics2D g2d = (Graphics2D) g;
	            g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
	            int w = getWidth();
	            int h = getHeight();
	            GradientPaint gp = new GradientPaint(0, 0, new Color(230, 240, 255), 
	                                               0, h, new Color(200, 220, 240));
	            g2d.setPaint(gp);
	            g2d.fillRect(0, 0, w, h);
	        }
	    };
	    bettingPanel.setLayout(new BoxLayout(bettingPanel, BoxLayout.Y_AXIS));
	    
	    // Add player score panels with alternating row colors
	    int savedTurn = getTurn();
	    for (int i = 0; i < this.getNumPlayers(); i++) {
	        this.setTurn(i);
	        Player player = this.getPlayer();
	        
	        JLabel label = new JLabel(player.getName() + ": ");
	        label.setFont(new Font("Arial", Font.BOLD, 14));
	        label.setForeground(new Color(25, 25, 112)); // Dark blue text
	        
	        JPanel playerPanel = new JPanel();
	        playerPanel.setLayout(new GridLayout(1, 2, 4, 0));
	        // Alternate row background colors
	        playerPanel.setBackground(i % 2 == 0 ? new Color(240, 248, 255) : new Color(220, 240, 255));
	        playerPanel.setBorder(BorderFactory.createCompoundBorder(
	            BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(180, 200, 220)),
	            BorderFactory.createEmptyBorder(3, 5, 3, 5)
	        ));
	        
	        playerPanel.add(label);
	        
	        // Style the score display
	        score[i].setFont(new Font("Arial", Font.BOLD, 14));
	        score[i].setForeground(new Color(0, 100, 0)); // Dark green for scores
	        score[i].setHorizontalAlignment(JTextField.CENTER);
	        score[i].setBorder(BorderFactory.createCompoundBorder(
	            BorderFactory.createLineBorder(new Color(180, 180, 180)),
	            BorderFactory.createEmptyBorder(2, 5, 2, 5)
	        ));
	        
	        playerPanel.add(score[i]);
	        bettingPanel.add(playerPanel);
	        bettingPanel.add(Box.createRigidArea(new Dimension(0, 5)));
	    }
	    setTurn(savedTurn);

	    // Create a decorated wrapper panel
	    JPanel wrapperPanel = new JPanel(new BorderLayout());
	    TitledBorder titledBorder = BorderFactory.createTitledBorder("SCORE");
	    titledBorder.setTitleFont(new Font("Arial", Font.BOLD, 16));
	    titledBorder.setTitleColor(new Color(0, 0, 128)); // Navy blue
	    wrapperPanel.setBorder(BorderFactory.createCompoundBorder(
	        BorderFactory.createEmptyBorder(5, 5, 5, 5),
	        titledBorder
	    ));
	    wrapperPanel.setBackground(new Color(245, 245, 255)); // Light blueish background
	    wrapperPanel.add(bettingPanel, BorderLayout.NORTH);

	    return wrapperPanel;
	}

	public boolean checkWin() {
		if(getPlayer().getNumCards() == 0) {
			firstTurn = getPlayer().getName();
			return true;
		}
		return false;
	}
	
	private void changeScore() {
		int maxNumCards = 0;
		for(int i = 0; i < getNumPlayers(); i++) {	
			if(!player.get(i).getName().equals(firstTurn)) {
				if(player.get(i).getNumCards() > maxNumCards) {
					maxNumCards = player.get(i).getNumCards();
				}
			}
		}
		
		for(int i = 0; i < getNumPlayers(); i++) {
			try {
	            int newScore;
	            if(player.get(i).getName().equals(firstTurn)) {
	                newScore = player.get(i).getScore() + maxNumCards * 10;
	            } else {
	                newScore = player.get(i).getScore() - player.get(i).getNumCards() * 10;
	            }
	            
	            // Check for negative score and throw exception
	            if(newScore < 0) {
	                throw new NegativeScoreException(player.get(i), newScore);
	            }
	            
	            // Set the new score if it's not negative
	            player.get(i).setScore(newScore);
	            
	        } catch(NegativeScoreException e) {
	            e.handleTienLen();
	        }
		}
	}
}
