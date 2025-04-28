package game;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Map;

import rule.TLMNRule;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import BotAI.TLMNBot;
import ui.GraphicalMode;
import utilities.ControlGame;
import utilities.card.Card;
import utilities.card.CardPlayingCard;
import utilities.player.Label;
import utilities.player.Player;
import utilities.player.State;

public class TienLenMienNam extends ControlGame implements Runnable{

	private JButton btnPass = new JButton ("Pass");
	private JButton btnShow = new JButton("Show");
	private int numWinner = 0;
	private int numPass = 0;
	private boolean winnerAtPresentRound;
	private int count = -1;
	private String firstTurn;
	private Thread thread;
	private int numChosen = 0;
	private TLMNRule rule = new TLMNRule();
	private TLMNBot bot = new TLMNBot();

	public TienLenMienNam(int mode, int numHuman, int numBots) {
		super(mode, numHuman, numBots);
		maxCards = 13;
		newGame();
		getPlayerName();
		GraphicalMode.frame.setTitle("Tien len mien nam");
		GraphicalMode.frame.add(controlPanel(), BorderLayout.EAST);
		GraphicalMode.frame.add(this);
		GraphicalMode.frame.setVisible(true);
		this.addMouseListener(handleInput());
		thread = new Thread(this);
	}

	@Override
	public MouseAdapter handleInput() {
		return new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Player hand = player.get(turn);
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
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		GraphicalMode.g2 = (Graphics2D) g;
        // Vẽ hình nền
        GraphicalMode.g2.drawImage(ui.backgroundImage, 0, 0, getWidth(), getHeight(), null);
        // Hiển thị thông tin về người chơi hiện tại
        GraphicalMode.g2.setColor(Color.WHITE);  // Thay đổi màu chữ để thấy rõ trên nền
        GraphicalMode.g2.setFont(new Font("Arial", Font.BOLD, 20));
        GraphicalMode.g2.drawString("Current turn: " + player.get(turn).getName(), 20, 20);
        drawChosenCards(previousCard, this);
        if(show == 0) {
        	drawHidingState(this, 25, 25);
        }
        else {
        	drawStatePlaying(this, 25, 25);
        	if(turn == 0 || turn == 2) {
        		drawCardsHorizontal(this, 25);
        	}
        	if(turn == 1 || turn == 3) {
        		drawCardsVertical(this, 30);
        	}
        }
	}
	
	public void newRound() {
		for(int i = 0; i <= 3; i++) {
			int idx = (turn + i) % getNumPlayers();
			if(player.get(idx).getState() != State.WIN) {
				player.get(idx).setState(State.PLAY);
			}
		}
		previousCard.clear();
		numPass = 0;
	}
	
	public void setFunctionButtonPlay() {
		btnPlay.addActionListener(e -> {
			previousCard = new ArrayList<Card>(chosenCard);
			deleteChosenCards();
			nextTurn();
			btnPlay.setEnabled(false);
			numChosen = 0;
		});
	}
	
	public void setFunctionButtonPass() {
		btnPass.addActionListener(e -> {
			player.get(turn).setState(State.PASS);
			numPass++;
			nextTurn();
			btnPass.setEnabled(false);
		});
	}
	
	public void setFunctionButtonShow() {
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
	
	@Override
	public JPanel controlPanel() {
		JPanel panel = new JPanel(new GridLayout(3, 1, 5, 5));
		setFunctionButtonPlay();
		setFunctionButtonPass();
		setFunctionButtonShow();
		btnPlay.setEnabled(false);
		btnPass.setEnabled(false);
		btnShow.setEnabled(true);
		panel.add(btnShow);
		panel.add(btnPlay);
		panel.add(btnPass);
		JPanel board = new JPanel();
		board.add(panel, BorderLayout.PAGE_START);
		return board;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(thread != null) {
			if(turn > 1) {
				System.out.print(getPlayer().getName() + " " + turn + " ");
				System.out.println(getPlayer().getCards());
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
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
				//nextTurn();
			}
			repaint();
		}
	}
	
	public void gameStart() {
		thread.start();
	}

	@Override
	public void newGame() {
		// TODO Auto-generated method stub
		getPlayerCards();
		initialize();
		for(int i = 0; i < getNumPlayers(); i++) {
			player.get(i).setName(getName(i));
		}
		if(firstTurn != null) {
			findFirstTurn();
		}
		show = 0;
		numChosen = 0;
		numWinner = 0;
		chosenCard.clear();
		newRound();
		btnShow.setEnabled(true);
	}
	
	@Override
	public void findFirstTurn() {
		for(int i = 0; i < getNumPlayers(); i++) {
			if(player.get(i).getName().equals(firstTurn)) {
				turn = i;
			}
		}
	}
	
	@Override
	public void nextTurn() {
		checkWin();
		if(endGame()) {
			return;
		}
		if(winnerAtPresentRound == true) {
			count++;
			if(count == getNumPlayers() - numWinner) {
				winnerAtPresentRound = false;
				count = -1;
			}
		}
		if(getNumPlayers() == numWinner + numPass) {
			do {
				turn = (turn + 1) % getNumPlayers();
			}while(player.get(turn).getState() != State.PASS);
			newRound();
		}
		
		else if(!winnerAtPresentRound && getNumPlayers() == numWinner + numPass + 1) {
			do {
				turn = (turn + 1) % getNumPlayers();
			}while(player.get(turn).getState() != State.PLAY);
			newRound();
		}
		
		else {
			do {
				turn = (turn + 1) % getNumPlayers();
			}while(player.get(turn).getState() != State.PLAY);
		}
		
		show = 0;
		if(player.get(turn).getLabel() == Label.HUMAN) {
			btnShow.setEnabled(true);
		}
		else {
			btnShow.setEnabled(false);
		}
	}

	public void checkWin() {
		// TODO Auto-generated method stub
		if(player.get(turn).getNumCards() == 0) {
			player.get(turn).setState(State.WIN);
			numWinner ++;
			if(numWinner == 1) {
				firstTurn = player.get(turn).getName();
			}
			winnerAtPresentRound = true;
			count = -1;
		}
	}

	@Override
	public boolean endGame() {
		if(numWinner == getNumPlayers() - 1) {
			int select = JOptionPane.showOptionDialog(null, "Do you want to have a new game", null,
					JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,
								null, null);
			if(select == 0) {
				newGame();
			}
			else if(select == 1) {
				System.exit(0);
			}
			return true;
		}
		
		return false;
	}
}
