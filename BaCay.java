package game;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingWorker;

import enums.ModeGUI;
import enums.State;
import enums.TypeGame;
import rule.BaCayRule;
import utilities.MainMenu;
import ui.GameUI;
import utilities.ControlGame;
import utilities.card.Card;
import utilities.deck.DeckPlayingCard;
import utilities.player.*;

public class BaCay extends ControlGame {

    private String winner;
    private boolean gameEnded = false;
    private AtomicInteger betsPlaced = new AtomicInteger(0);
    private int totalPlayers;
    private boolean isFirstGame = true;

    public BaCay(ModeGUI modeGUI, ArrayList<Human> humans, ArrayList<Bot> bots, int numBots) {
        super(modeGUI, humans, bots, numBots);
        deckObj = new DeckPlayingCard();
        setMaxCards(3);
        totalPlayers = getNumPlayers();
        newGame();
        BaCayRule rule = new BaCayRule();
        GameUI.frame.setTitle("BA CÂY");
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

        // Vị trí khác nhau cho mỗi người chơi để tránh chồng lấp
        for (int i = 0; i < getNumPlayers(); i++) {
            setTurn(i);
            if (getTurn() == 0) { // Người chơi trên cùng
                ui.drawHidingStateHorizontalBaCay(this, 25);
            }
            else if (getTurn() == 1) { // Người chơi bên phải
                ui.drawHidingStateVerticalBaCay(this, getWidth() - 150);
            }
            else if (getTurn() == 2) { // Người chơi dưới cùng
                ui.drawHidingStateHorizontalBaCay(this, getHeight() - 150);
            }
            else if (getTurn() == 3) { // Người chơi bên trái
                ui.drawHidingStateVerticalBaCay(this, 30);
            }
        }

        if (gameEnded && winner != null) {
            // Hiển thị bài của tất cả người chơi khi game kết thúc
            for (int i = 0; i < getNumPlayers(); i++) {
                setTurn(i);
                if (getTurn() == 0) { // Người chơi trên cùng
                    ui.drawCardsHorizontal(this, 25);
                }
                else if (getTurn() == 1) { // Người chơi bên phải
                    ui.drawCardsVertical(this, getWidth() - 150);
                }
                else if (getTurn() == 2) { // Người chơi dưới cùng
                    ui.drawCardsHorizontal(this, getHeight() - 150);
                }
                else if (getTurn() == 3) { // Người chơi bên trái
                    ui.drawCardsVertical(this, 30);
                }
            }
            
            // Hiển thị người thắng
            GameUI.g2.setFont(new Font("Arial", Font.BOLD, 30));
            GameUI.g2.setColor(Color.YELLOW);
            String winText = winner + " thắng!";
            int textWidth = GameUI.g2.getFontMetrics().stringWidth(winText);
            
            // Vẽ nền cho text
            GameUI.g2.setColor(new Color(0, 0, 0, 180));
            GameUI.g2.fillRoundRect(getWidth()/2 - textWidth/2 - 20, getHeight()/2 - 20, textWidth + 40, 50, 15, 15);
            
            // Vẽ border
            GameUI.g2.setColor(new Color(255, 215, 0));
            GameUI.g2.setStroke(new BasicStroke(3));
            GameUI.g2.drawRoundRect(getWidth()/2 - textWidth/2 - 20, getHeight()/2 - 20, textWidth + 40, 50, 15, 15);
            
            // Vẽ text
            GameUI.g2.setColor(Color.WHITE);
            GameUI.g2.drawString(winText, getWidth()/2 - textWidth/2, getHeight()/2 + 10);
        }
    }

    @Override
    public JPanel controlPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        JButton btnNewGame = new JButton("Ván mới");
        JButton btnMainMenu = new JButton("Về menu chính");

        btnNewGame.addActionListener(e -> newGame());

        btnMainMenu.addActionListener(e -> {
            GameUI.frame.dispose();
            new MainMenu();
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(btnNewGame);
        buttonPanel.add(btnMainMenu);
        panel.add(buttonPanel, BorderLayout.PAGE_START);

        panel.add(createBettingPanel(), BorderLayout.CENTER);

        return panel;
    }

    private JPanel createBettingPanel() {
        JPanel bettingPanel = new JPanel();
        bettingPanel.setLayout(new BoxLayout(bettingPanel, BoxLayout.Y_AXIS));

        for (int i = 0; i < this.getNumPlayers(); i++) {
            this.setTurn(i);
            Player player = this.getPlayer();
            int maxScore = player.getScore(TypeGame.BA_CAY);

            JLabel label = new JLabel(player.getName() + ": ");
            SpinnerNumberModel model = new SpinnerNumberModel(0, 0, maxScore, 100); // bước 10
            JSpinner spinner = new JSpinner(model);

            JButton btnConfirm = new JButton("OK");
            btnConfirm.addActionListener(e -> {
                int value = (int) spinner.getValue();
                player.setBet(value);
                spinner.setEnabled(false);
                btnConfirm.setEnabled(false);
                
                // Tăng số người đã đặt cược
                int currentBets = betsPlaced.incrementAndGet();
                
                // Kiểm tra xem tất cả người chơi đã đặt cược chưa
                if (currentBets >= totalPlayers) {
                    // Tất cả đã đặt cược, hiển thị kết quả sau 5 giây
                    showResultsAfterDelay();
                }
            });

            JPanel playerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            playerPanel.add(label);
            playerPanel.add(spinner);
            playerPanel.add(btnConfirm);

            bettingPanel.add(playerPanel);
        }

        JPanel wrapperPanel = new JPanel(new BorderLayout());
        wrapperPanel.setBorder(BorderFactory.createTitledBorder("Bet Information"));
        wrapperPanel.add(bettingPanel, BorderLayout.CENTER);

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
                determineWinner();
                gameEnded = true;
                repaint();
            }
        };
        
        worker.execute();
    }

    public void init() {
    	for(int i = 0; i < players.size(); i++) {
            players.get(i).setBet(1000);
        }
        
    }
    @Override
    public void newGame() {
        getPlayerCards();
        for(int i = 0; i < players.size(); i++) {
            for(int j = 0; j < 3; j++) {
                players.get(i).getCardState().add(0);
            }
        }
        
        gameEnded = false;
        winner = null;
        betsPlaced.set(0); // Reset số người đã đặt cược

        for (Player p : players) {
            p.setState(State.PLAY);
        }

        repaint();
        gameStart();
    }

    public void gameStart() {
        // Không cần timer nữa, chỉ cần reset trạng thái
        for (Player p : players) {
            p.setState(State.PLAY);
        }
    }

    public void determineWinner() {
        Player bestPlayer = null;
        int highestScore = -1;

        for (Player p : players) {
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
                score += 10; 
            } else if (value == 14) {
                score += 1; 
            } else {
                score += value;
            }
        }

        return score % 10;
    }

    @Override
    public void findFirstTurn() {
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

    @Override
    public boolean endGame() {
        // TODO Auto-generated method stub
        return false;
    }
}