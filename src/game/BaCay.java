package game;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingWorker;
import javax.swing.border.EmptyBorder;

import bot.BaCayBot;
import exception.NegativeScoreException;
import rule.BaCayRule;
import ui.game.GameUI;
import ui.menu.GameConfiguration;
import utilities.deck.DeckBaCay;
import utilities.player.Label;
import utilities.player.Player;

public class BaCay extends ControlGame implements Runnable {

	private String winner;
    private boolean gameEnded = false;
    private JButton btnNewGame = new JButton("New game");
    private JButton btnMainMenu = new JButton("Home");
    private JButton[] btnConfirm;
    private JSpinner[] spinner;
    private BaCayRule rule = new BaCayRule();
    private Thread thread;
    private BaCayBot bot = new BaCayBot();
    private int currentBets = 0;
    private boolean botAction = true;

    public BaCay(int mode, int numHuman, int numBots, ArrayList<String> name) {
        super(mode, numHuman, numBots, name);
        btnConfirm = new JButton[getNumPlayers()];
        spinner = new JSpinner[getNumPlayers()];
        deckObj = new DeckBaCay();
        chooseModeGUI();
        setMaxCards(3);
        initialize();
        newGame();
        thread = new Thread(this);
        ui.getFrame().setTitle("BA CAY");
        ui.getFrame().add(controlPanel(), BorderLayout.EAST);
		ui.getFrame().add(this);
		ui.getFrame().setLocationRelativeTo(null);
		ui.getFrame().setVisible(true);
    }
    
    @Override
	public MouseAdapter handleInput() {
		// TODO Auto-generated method stub
		return null;
	}
    
    @Override
    public JPanel controlPanel() {
    	JPanel panel = new JPanel(new BorderLayout());

        btnNewGame.setEnabled(false);
        btnMainMenu.setEnabled(false);

        btnNewGame.addActionListener(e -> {
        	newGame();
        });

        btnMainMenu.addActionListener(e -> {
            ui.getFrame().dispose();
            new GameConfiguration().startMenuSystem();
        });

        JPanel buttonPanel = new JPanel(new GridLayout(2, 1, 5, 5));
        buttonPanel.add(btnNewGame);
        buttonPanel.add(btnMainMenu);
        panel.add(buttonPanel, BorderLayout.PAGE_START);
        panel.setBorder(new EmptyBorder(5, 20, 5, 20));
        panel.add(createBettingPanel(), BorderLayout.CENTER);

        return panel;
    }
    
    @Override
    public void newGame() {
    	getPlayerCards();
        btnNewGame.setEnabled(false);
        btnMainMenu.setEnabled(false);
        currentBets = 0; // Reset số người đã đặt cược
        for(int i = 0; i < getNumPlayers(); i++) {
			if(winner != null) {
				if(player.get(i).getLabel() != Label.BOT) {
	            	btnConfirm[i].setEnabled(true);
	            	spinner[i].setEnabled(true);
	            }
			}
		}
        gameEnded = false;
        winner = null;
        botAction = true;
        repaint();
    }

    @Override
    public void endGame() {
        
    }

    @Override
    public void nextTurn() {
        // Không cần cho trò chơi 3 cây vì không có lượt đi
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        GameUI.g2 = (Graphics2D) g;
        ui.drawBackground(this);
        if (!gameEnded) {
        	ui.drawHidingState(this, 25, 25);
        }
        else if (gameEnded && winner != null) {
            // Hiển thị bài của tất cả người chơi khi game kết thúc
            for (int i = 0; i < getNumPlayers(); i++) {
                setTurn(i);
                if (getTurn() == 0) { // Người chơi trên cùng
                    ui.drawCardsHorizontal(this, 25);
                }
                else if (getTurn() == 1) { // Người chơi bên phải
                    ui.drawCardsVertical(this, 30);
                }
                else if (getTurn() == 2) { // Người chơi dưới cùng
                    ui.drawCardsHorizontal(this, 25);
                }
                else if (getTurn() == 3) { // Người chơi bên trái
                    ui.drawCardsVertical(this, 30);
                }
            }
            ui.drawWinner(this, winner);
        }
        // Draw chip
        ui.drawChips(this, 25, 25);
    }
    
    public void initialize() {
    	super.initialize();
    	// Initialize for cardState
    	if(winner == null) {
    		for(Player p : player) {
            	p.setScore(800);
            }
    	}
    }
    
    private JPanel createBettingPanel() {
        JPanel bettingPanel = new JPanel();
        bettingPanel.setLayout(new BoxLayout(bettingPanel, BoxLayout.Y_AXIS));

        for (int i = 0; i < this.getNumPlayers(); i++) {
            this.setTurn(i);
            Player player = this.getPlayer();
            int maxScore = 1000;

            JLabel label = new JLabel(player.getName() + ": ");
            SpinnerNumberModel model = new SpinnerNumberModel(10, 10, maxScore, 10); // bước 10
            spinner[i] = new JSpinner(model);
            int j = i;
            JButton btnConfirm = new JButton("OK");
            btnConfirm.addActionListener(e -> {
                spinner[j].setEnabled(false);
                btnConfirm.setEnabled(false);
                
                // Tăng số người đã đặt cược
                currentBets++;
                
                // Kiểm tra xem tất cả người chơi đã đặt cược chưa
                if (currentBets >= getNumPlayers()) {
                    // Tất cả đã đặt cược, hiển thị kết quả sau 5 giây
                    showResultsAfterDelay();
                }
            });
            if(player.getLabel() == Label.BOT) {
            	btnConfirm.setEnabled(false);
            	spinner[i].setEnabled(false);
            }
            this.btnConfirm[i] = btnConfirm;

            JPanel playerPanel = new JPanel();
            playerPanel.setLayout(new GridLayout(1, 2, 4, 0));
            playerPanel.add(label);
            playerPanel.add(spinner[i]);
            playerPanel.add(btnConfirm);

            bettingPanel.add(playerPanel);
            bettingPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        }

        JPanel wrapperPanel = new JPanel(new BorderLayout());
        wrapperPanel.setBorder(BorderFactory.createTitledBorder("Bet Information"));
        wrapperPanel.add(bettingPanel, BorderLayout.NORTH);

        return wrapperPanel;
    }
    
    private void showResultsAfterDelay() {
    	// Sử dụng SwingWorker để không block EDT
        SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
                Thread.sleep(3000);
                return null;
            }
            
            @Override
            protected void done() {
            	whoWinner();
                gameEnded = true;
                btnNewGame.setEnabled(true);
                btnMainMenu.setEnabled(true);
                repaint();
            }
        };
        
        worker.execute();
    }

    public void gameStart() {
    	thread.start();
    }
    
    private void whoWinner() {
    	int winner = rule.getWinner(player);
    	this.winner = player.get(winner).getName();
    	
    	// Change score of players
    	for(int i = 0; i < getNumPlayers(); i++) {
    		int coef = -1;
    		if(i == winner) {
    			coef = 1;
    		}
    		int score = player.get(i).getScore() + coef*(int)spinner[i].getValue();
    		try {
    			if(score < 0) {
        			throw new NegativeScoreException(player.get(i), score);
        		}
        		player.get(i).setScore(score);
    		} catch(NegativeScoreException e) {
    			e.handleBaCay();
    		}
    	}
    }

	@Override
	public void run() {
	    while(thread != null) {
	        // Check if bot action is requested
	        if(botAction) {
	            for(int i = 0; i < getNumPlayers(); i++) {
	                if(player.get(i).getLabel() == Label.BOT) {
	                    spinner[i].setValue(bot.evaluateScore(player.get(i)));
	                    currentBets++;
	                }
	            }
	            botAction = false;
	        }
	        
	        // Add a small sleep to prevent CPU hogging
	        try {
	            Thread.sleep(100);
	        } catch (InterruptedException e) {
	            e.printStackTrace();
	        }
	    }
	}
}