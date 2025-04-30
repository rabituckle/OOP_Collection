package game;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Map;

import rule.TienLenRule;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import BotAI.TLMNBot;
import ui.MainMenu;
import ui.GameUI;
import utilities.ControlGame;
import utilities.card.Card;
import utilities.deck.DeckPlayingCard;
import utilities.player.Label;
import utilities.player.Player;
import utilities.player.State;

public abstract class TienLen extends ControlGame{

	private JButton btnPass = new JButton ("Pass");
	private JButton btnShow = new JButton("Show");
	private int numWinner = 0;
	private int numPass = 0;
	private int newWinTurn;
	private boolean winnerAtPresentRound;
	private int count = -1;
	private String firstTurn;
	private int numChosen = 0;
	protected TienLenRule rule;
	protected TLMNBot bot = new TLMNBot();

	public TienLen(int mode, int numHuman, int numBots) {
		super(mode, numHuman, numBots);
		deckObj = new DeckPlayingCard();
		chooseModeGUI();
		setMaxCards(13);
		newGame();
		getPlayerName();
		GameUI.frame.add(controlPanel(), BorderLayout.EAST);
		GameUI.frame.add(this);
		GameUI.frame.setVisible(true);
	}

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
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		GameUI.g2 = (Graphics2D) g;
        ui.drawBackground(this);
        ui.drawChosenCards(previousCard, this);
        if(show == 0) {
        	ui.drawHidingState(this, 25, 25);
        }
        else {
        	ui.drawStatePlaying(this, 25, 25);
        	if(getTurn() == 0 || getTurn() == 2) {
        		ui.drawCardsHorizontal(this, 25);
        	}
        	if(getTurn() == 1 || getTurn() == 3) {
        		ui.drawCardsVertical(this, 30);
        	}
        }
	}
	
	public void newRound() {
		for(int i = 0; i <= 3; i++) {
			int idx = (getTurn() + i) % getNumPlayers();
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
			getPlayer().setState(State.PASS);
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
	public void newGame() {
		// TODO Auto-generated method stub
		getPlayerCards();
		initialize();
		if(firstTurn != null) {
			for(int i = 0; i < getNumPlayers(); i++) {
				player.get(i).setName(getName(i));
			}
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
				setTurn(i);
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
			setTurn(newWinTurn);
			newRound();
			do {
				setTurn((getTurn() + 1) % getNumPlayers());
			}while(getPlayer().getState() != State.PLAY);
		}
		
		else if(!winnerAtPresentRound && getNumPlayers() == numWinner + numPass + 1) {
			do {
				setTurn((getTurn() + 1) % getNumPlayers());
			}while(getPlayer().getState() != State.PLAY);
			newRound();
		}
		
		else {
			do {
				setTurn((getTurn() + 1) % getNumPlayers());
			}while(getPlayer().getState() != State.PLAY);
		}
		
		show = 0;
		if(getPlayer().getLabel() == Label.HUMAN) {
			btnShow.setEnabled(true);
		}
		else {
			btnShow.setEnabled(false);
		}
	}

	public void checkWin() {
		// TODO Auto-generated method stub
		if(getPlayer().getNumCards() == 0) {
			getPlayer().setState(State.WIN);
			numWinner ++;
			newWinTurn = getTurn();
			if(numWinner == 1) {
				firstTurn = getPlayer().getName();
			}
			winnerAtPresentRound = true;
			count = -1;
		}
	}

	@Override
	public boolean endGame() {
		if(numWinner == getNumPlayers() - 1) {
			int select = JOptionPane.showOptionDialog(null, "Do you want to have a new game", firstTurn + " won!",
					JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,
								null, null);
			if(select == 0) {
				newGame();
			}
			else if(select == 1) {
				GameUI.frame.dispose();
				new MainMenu();
			}
			return true;
		}
		
		return false;
	}
	
	public void increaseNumPass() {
		numPass++;
	}
	
	public abstract void gameStart();
}
