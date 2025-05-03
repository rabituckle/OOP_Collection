package game;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import ui.MainMenu;
import ui.GameUI;
import utilities.ControlGame;
import utilities.card.Card;
import utilities.card.CardPlayingCard;
import utilities.deck.DeckPlayingCard;
import utilities.player.Label;
import utilities.player.Player;
import utilities.player.State;

public class BaCay extends ControlGame {

    private Timer gameTimer;
    private String winner;
    private boolean gameEnded = false;

    public BaCay(int mode, int numHuman, int numBots) {
        super(mode, numHuman, numBots);
        deckObj = new DeckPlayingCard();
        chooseModeGUI();
        setMaxCards(3);
        newGame();
        getPlayerName();
        GameUI.frame.add(controlPanel(), BorderLayout.EAST);
        GameUI.frame.add(this);
        GameUI.frame.setVisible(true);
        gameStart();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        GameUI.g2 = (Graphics2D) g;
        ui.drawBackground(this);
        
        for (int i = 0; i < getNumPlayers(); i++) {
            setTurn(i);
            if (getTurn() == 0 || getTurn() == 2) {
                ui.drawCardsHorizontal(this, 25);
            }
            if (getTurn() == 1 || getTurn() == 3) {
                ui.drawCardsVertical(this, 30);
            }
        }
        
        if (gameEnded && winner != null) {
            GameUI.g2.drawString(winner + " thắng!", getWidth() / 2 - 50, getHeight() / 2);
        }
    }

    @Override
    public JPanel controlPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        JButton btnNewGame = new JButton("Ván mới");
        JButton btnMainMenu = new JButton("Về menu chính");
        
        btnNewGame.addActionListener(e -> {
            newGame();
        });
        
        btnMainMenu.addActionListener(e -> {
            if (gameTimer != null) {
                gameTimer.cancel();
            }
            GameUI.frame.dispose();
            new MainMenu();
        });
        
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(btnNewGame);
        buttonPanel.add(btnMainMenu);
        panel.add(buttonPanel, BorderLayout.PAGE_START);
        
        return panel;
    }

    @Override
    public void newGame() {
        if (gameTimer != null) {
            gameTimer.cancel();
        }
        
        getPlayerCards();
        for(int i = 0; i < player.size(); i++) {
			for(int j = 0; j < 3; j++) {
				player.get(i).getCardState().add(0);
			}
		}
        
        show = 1; // Luôn hiển thị bài
        gameEnded = false;
        winner = null;
        
        for (Player p : player) {
            p.setState(State.PLAY);
        }
        
        repaint();
        gameStart();
    }

    public void gameStart() {
        // Hiển thị bài trong 5 giây
        gameTimer = new Timer();
        gameTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                determineWinner();
                gameEnded = true;
                repaint();
                endGame();
            }
        }, 5000); // 5 giây
    }
    
    public void determineWinner() {
        Player bestPlayer = null;
        int highestScore = -1;
        
        for (Player p : player) {
            int score = calculateHandValue(p);
            if (score > highestScore) {
                highestScore = score;
                bestPlayer = p;
            }
        }
        
        if (bestPlayer != null) {
            winner = bestPlayer.getName();
            bestPlayer.setState(State.WIN);
        }
    }
    
    private int calculateHandValue(Player p) {
        ArrayList<Card> hand = new ArrayList<>();
        for (int i = 0; i < p.getNumCards(); i++) {
            hand.add(p.getCard(i));
        }

        int score = 0;
        for (Card c : hand) {
            int value = c.getCost(); 
            if (value >= 11 && value <= 13) {
                score += 10; // J, Q, K tính là 10
            } else if (value == 14) {
                score += 1;  // A tính là 1
            } else {
                score += value;
            }
        }

        return score % 10;
    }

    @Override
    public boolean endGame() {
        if (gameEnded) {
            int select = JOptionPane.showOptionDialog(null, "Bạn có muốn chơi ván mới không?", winner + " thắng!",
                    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
            
            if (select == 0) {
                newGame();
            } else {
                GameUI.frame.dispose();
                new MainMenu();
            }
            return true;
        }
        return false;
    }

    @Override
    public void findFirstTurn() {
        // Không cần cho trò chơi 3 cây vì không có lượt đi
        setTurn(0);
    }

    @Override
    public void nextTurn() {
        // Không cần cho trò chơi 3 cây vì không có lượt đi
    }

	@Override
	public MouseAdapter handleInput() {
		// TODO Auto-generated method stub
		return null;
	}
}