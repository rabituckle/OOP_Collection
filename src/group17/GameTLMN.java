package group17;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class GameTLMN extends Game implements MouseListener, Runnable{
	private Thread thread;
	private ArrayList<Card> chosenCard = new ArrayList<Card>();
	private ArrayList<Card> pre = new ArrayList<Card>();
	private JButton btnPlay = new JButton("Play");
	private JButton btnSkip = new JButton("SKip");
//	private JButton btnHide = new JButton("Hide");
	private int numChosen = 0;
	private int numOfSkip = 0;
	private boolean[] skip = new boolean[4];
	private BotAITLMN bot = new BotAITLMN();
	private ArrayList<Card> botPlay = new ArrayList<Card>();

	public GameTLMN(String title, int players, int bots) {
		// TODO Auto-generated constructor stub
		super(title, players, bots);
		maxCards = 13;
		newGame(maxCards, playersNum);
		setCardButton();
		this.addMouseListener(this);
		window.add(setPanelPlay(), BorderLayout.EAST);
	}
	
	public JPanel setPanelPlay() {
		JPanel panel = new JPanel(new GridLayout(3, 1, 5, 5));
		btnPlay.setActionCommand("Play");
		btnPlay.addActionListener(this);
		btnPlay.setEnabled(false);
		
		btnSkip.setActionCommand("Skip");
		btnSkip.addActionListener(this);
		btnSkip.setEnabled(false);

//		btnHide.setActionCommand("Hide");
//		btnHide.addActionListener(this);
		panel.add(btnPlay);
		panel.add(btnSkip);
//		panel.add(btnHide);
		JPanel board = new JPanel();
		board.add(panel, BorderLayout.PAGE_START);
		return board;
	}
	
	public void newGame(int maxCards, int playersNum) {
		deck = host.shuffleDeck(deck);
		playerCard = host.getPlayerCard(deck, playersNum, maxCards);
		setCardButton();
		newRound();
		turn = 0;
		show = 0;
		numChosen = 0;
		chosenCard.clear();
		pre.clear();
	}
	
	public void newRound() {
		for(int i = 0; i < playersNum; i++) {
			skip[i] = false;
		}
	}
	
	public boolean checkEndGame() {
		if(playerCard[turn].size() == 0) {
			String winner;
			if(turn < players) {
				winner = "Player " + (turn + 1) + " wins!";
			}
			else {
				winner = "Bot " + (turn + 1 - players) + " wins!";
			}
			int select = JOptionPane.showOptionDialog(null, "Do you want to have a new game", winner,
					JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,
								null, null);
			if(select == 0) {
				newGame(maxCards, playersNum);
			}
			else if(select == 1) {
				window.dispatchEvent(new WindowEvent(window, WindowEvent.WINDOW_CLOSING));
			}
			return true;
		}
		return false;
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g2 = (Graphics2D) g;
		
		drawTurn(30);
		if(show == 0) {
			drawChosenCards();
			drawStartState();
			//btnHide.setEnabled(false);
		}
		else if(show == 1) {
			if(turn == 0) {
				drawChosenCards();
				paintCardsHorizontal(playerCard[0], 0, width/2-(12*disX+cardWidth)/2, 25, cardWidth, cardHeight);
				drawStatePlaying();
			}
			else if(turn == 1 && turn <= players) {
				drawChosenCards();
				paintCardsVertical(playerCard[1], 1, width - (cardWidth + 95), height/2-(12*disY+cardHeight)/2, cardWidth, cardHeight);
				drawStatePlaying();
			}
			else if(turn == 2 && turn <= players) {
				drawChosenCards();
				paintCardsHorizontal(playerCard[2], 2, width/2-(12*disX+cardWidth)/2, height - (cardHeight+50), cardWidth, cardHeight);
				drawStatePlaying();
			}
			else if(turn == 3 && turn <= players) {
				drawChosenCards();
				paintCardsVertical(playerCard[3], 3, 30, height/2-(12*disY+cardHeight)/2, cardWidth, cardHeight);
				drawStatePlaying();
			}
		}
	}
	
	public void drawChosenCards() {
		int x = width/2 - ((pre.size()-1)*disX + cardWidth)/2 - 50;
		int y = height/2 - cardHeight/2 - 20;
		for(int i = 0; i < pre.size(); i++) {
			g2.drawImage(getImageCard(pre.get(i).getValue()).getImage(), x+disX*i, y, cardWidth, cardHeight, null);
		}
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		if(turn < players && btn.get(turn).contains(e.getPoint()) && show == 0) {
			show = 1;
			if(pre.size() != 0) {
				btnSkip.setEnabled(true);
			}
			else {
				btnSkip.setEnabled(false);
			}
		}
		else if(show == 1) {
			for(int i = 0; i < playerCard[turn].size(); i++) {
				if(mapCard[turn].get(playerCard[turn].get(i)).contains(e.getPoint())) {
					if(cardState[turn].get(i) == 0) {
						numChosen++;
						cardState[turn].set(i, 1);
						
//						if(turn == 0 || turn == 2) {
//							mapCard[turn].get(playerCard[turn].get(i)).translate(0, -20);
//						}
//						else if(turn == 1 || turn == 3) {
//							mapCard[turn].get(playerCard[turn].get(i)).translate(-20, 0);
//						}
					}
					else if(cardState[turn].get(i) == 1) {
						numChosen--;
						cardState[turn].set(i, 0);
//						if(turn == 0 || turn == 2) {
//							mapCard[turn].get(playerCard[turn].get(i)).translate(0, 20);
//						}
//						else if(turn == 1 || turn == 3) {
//							mapCard[turn].get(playerCard[turn].get(i)).translate(20, 0);
//						}
					}
				}
			}
			if(numChosen == 0) {
				btnPlay.setEnabled(false);
				btnSkip.setEnabled(true);
			}
			else if(numChosen > 0) {
				int i = 0;
				chosenCard.clear();
				while(i < playerCard[turn].size()) {
					if(cardState[turn].get(i) == 1) {
						chosenCard.add(playerCard[turn].get(i));
					}
					i++;
				}
				//System.out.println(chosenCard);
				rule();
				btnSkip.setEnabled(false);
			}
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {}

	@Override
	public void mouseReleased(MouseEvent e) {}

	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(thread != null) {  
			if(turn >= players) {
				System.out.println(turn);
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				botPlay = bot.engineCard(playerCard[turn], pre);
				System.out.println(turn);
				if(botPlay == null) {
					skip[turn] = true;
					numOfSkip++;
					do{
						turn = (turn + 1) % playersNum;
					}while(skip[turn]);
					if(numOfSkip == playersNum - 1) {
						newRound();
						pre.clear();
						numOfSkip = 0;
					}
				}
				else {
					pre = botPlay;
					repaint();
					if(!checkEndGame()) {
						do{
							turn = (turn + 1) % playersNum;
						}while(skip[turn]);
						numChosen = 0;
					}
				}
			}
			else{
				repaint();
			}
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		String cmd = e.getActionCommand();
		if(cmd.equals("Play")) {
			//btnHide.setEnabled(true);
			int i = 0;
			pre = new ArrayList<Card>(chosenCard);
			if(chosenCard.size() != 0) {
				chosenCard.clear();
			}
			while(i < playerCard[turn].size()) {
				if(cardState[turn].get(i) == 1) {
					cardState[turn].remove(i);
					playerCard[turn].remove(i);
				}
				else {
					i++;
				}
			}
			btnPlay.setEnabled(false);
			show = 0;
			if(!checkEndGame()) {
				do{
					turn = (turn + 1) % playersNum;
				}while(skip[turn]);
				numChosen = 0;
			}
		}
		else if(cmd.equals("Skip")) {
			//btnHide.setEnabled(true);
			skip[turn] = true;
			numOfSkip++;
			do{
				turn = (turn + 1) % playersNum;
			}while(skip[turn]);
			if(numOfSkip == playersNum - 1) {
				newRound();
				pre.clear();
				numOfSkip = 0;
			}
			show = 0;
			btnSkip.setEnabled(false);
		}
	}
	
	public void gameStart() {
		thread = new Thread(this);
		thread.start();
	}
	
	public void rule() {
		if(singleCard()) {
			btnPlay.setEnabled(true);
		}
		else if(doubleCard()) {
			btnPlay.setEnabled(true);
		}
		else if(tripleCard()) {
			btnPlay.setEnabled(true);
		}
		else if(sequenceCard()) {
			btnPlay.setEnabled(true);
		}
		else if(fourSeasons()) {
			btnPlay.setEnabled(true);
		}
		else if(consecutivePairs()) {
			btnPlay.setEnabled(true);
		}
		else {
			btnPlay.setEnabled(false);
		}
	}
	
	public boolean singleCard() {
		if(pre.size() == 0 && chosenCard.size() == 1) {
			return true;
		}
		if(pre.size() == 1) {
			if(chosenCard.size() == 1 && pre.get(0).compareTo(chosenCard.get(0)) < 0) {
				return true;
			}
			else if(pre.get(0).getCost() == 15) {
				// Tu quy
				if(chosenCard.size() == 4) {
					for(int i = 0; i < 3; i++) {
						if(chosenCard.get(i).getCost() != chosenCard.get(i+1).getCost()) {
							return false;
						}
					}
					return true;
				}
				// Doi thong
				else if(chosenCard.size() >= 6 && chosenCard.size() % 2 == 0) {
					if(chosenCard.get(0).getCost() >= 13) {
						return false;
					}
					for(int i = 0; i < chosenCard.size() - 3; i+=2) {
						if(chosenCard.get(i).getCost() != chosenCard.get(i+1).getCost()) {
							return false;
						}
						if(chosenCard.get(i).getCost() + 1 != chosenCard.get(i+2).getCost()) {
							return false;
						}
					}
					if(chosenCard.get(chosenCard.size()-2).getCost() != chosenCard.get(chosenCard.size()-1).getCost()) {
						return false;
					}
					return true;
				}
			}
		}
		return false;
	}
	
	public boolean doubleCard() {
		if(pre.size() == 0 && chosenCard.size() == 2) {
			if(chosenCard.get(0).getCost() == chosenCard.get(1).getCost()) {
				return true;
			}
		}
		if(pre.size() == 2 && chosenCard.size() == 2) {
			if(chosenCard.get(0).getCost() == chosenCard.get(1).getCost()) {
				if(pre.get(1).compareTo(chosenCard.get(1)) < 0) {
					return true;
				}
			}
		}
		// tu quy chat doi 2
		if(pre.size() == 2 && chosenCard.size() == 4) {
			for(int i = 0; i < 3; i++) {
				if(chosenCard.get(i).getCost() != chosenCard.get(i+1).getCost()) {
					return false;
				}
			}
			return true;
		}
		
		// 5 doi thong chat doi 2
		if(pre.size() == 2 && chosenCard.size() == 10) {
			for(int i = 1; i < 8; i+=2) {
				if(chosenCard.get(i).getCost() != chosenCard.get(i-1).getCost()) {
					return false;
				}
				if(chosenCard.get(i).getCost() + 1 != chosenCard.get(i+1).getCost()) {
					return false;
				}
			}
			if(chosenCard.get(8).getCost() != chosenCard.get(9).getCost()) {
				return false;
			}
			return true;
		}
		return false;
	}
	
	public boolean tripleCard() {
		if(pre.size() == 0 && chosenCard.size() == 3) {
			if(chosenCard.get(0).getCost() == chosenCard.get(1).getCost() &&  chosenCard.get(1).getCost() == chosenCard.get(2).getCost()) {
				return true;
			}
		}
		if(pre.size() == 3 && chosenCard.size() == 3) {
			if(chosenCard.get(0).getCost() == chosenCard.get(1).getCost() && chosenCard.get(1).getCost() == chosenCard.get(2).getCost()) {
				if(pre.get(2).getCost() < chosenCard.get(2).getCost()) {
					return true;
				}
			}
		}
		return false;
	}
	
	public boolean sequenceCard() {
		int size = chosenCard.size();
		if(pre.size() == 0 && size >= 3 && chosenCard.get(size-1).getCost() < 15) {
			for(int i = 0; i < chosenCard.size()-1; i++) {
				if(chosenCard.get(i).getCost() + 1 != chosenCard.get(i+1).getCost()){
					return false;
				}
			}
			return true;
		}
		
		if(pre.size() >= 3 && size == pre.size()) {
			for(int i = 0; i < chosenCard.size()-1; i++) {
				if(chosenCard.get(i).getCost() + 1 != chosenCard.get(i+1).getCost()){
					return false;
				}
			}
			if(pre.get(pre.size()-1).compareTo(chosenCard.get(size-1)) < 0) {
				return true;
			}
		}
		return false;
	}
	
	public boolean fourSeasons() {
		if(pre.size() == 0 && chosenCard.size() == 4) {
			for(int i = 0; i < 3; i++) {
				if(chosenCard.get(i).getCost() != chosenCard.get(i+1).getCost()) {
					return false;
				}
			}
			return true;
		}
		//tu quy
		if(pre.size() == 4 && chosenCard.size() == 4) {
			for(int i = 0; i < 3; i++) {
				if(chosenCard.get(i).getCost() != chosenCard.get(i+1).getCost()) {
					return false;
				}
			}
			if(pre.get(0).getCost() < chosenCard.get(0).getCost()) {
				return true;
			}
		}
		// 5 doi thong
		if(pre.size() == 4 && chosenCard.size() == 10) {
			for(int i = 1; i < 8; i+=2) {
				if(chosenCard.get(i).getCost() != chosenCard.get(i-1).getCost()) {
					return false;
				}
				if(chosenCard.get(i).getCost() + 1 != chosenCard.get(i+1).getCost()) {
					return false;
				}
			}
			if(chosenCard.get(8).getCost() != chosenCard.get(9).getCost()) {
				return false;
			}
			return true;
		}
		return false;
	}
	
	public boolean consecutivePairs() {
		int len = chosenCard.size();
		if(pre.size() == 0 && len >= 6 && len % 2 == 0 && chosenCard.get(len - 1).getCost() < 15) {
			for(int i = 1; i < len-2; i+=2) {
				if(chosenCard.get(i).getCost() != chosenCard.get(i-1).getCost()) {
					return false;
				}
				if(chosenCard.get(i).getCost() + 1 != chosenCard.get(i+1).getCost()) {
					return false;
				}
			}
			if(chosenCard.get(len-2).getCost() != chosenCard.get(len-1).getCost()) {
				return false;
			}
			return true;
		}
		// doi thong chat doi thong
		if(pre.size() >= 6 && len >= 6 && len % 2 ==0) {
			for(int i = 1; i < len-2; i+=2) {
				if(chosenCard.get(i).getCost() != chosenCard.get(i-1).getCost()) {
					return false;
				}
				if(chosenCard.get(i).getCost() + 1 != chosenCard.get(i+1).getCost()) {
					return false;
				}
			}
			if(chosenCard.get(len-2).getCost() != chosenCard.get(len-1).getCost()) {
				return false;
			}
			if(pre.get(pre.size()-1).compareTo(chosenCard.get(len-1)) < 0) {
				return true;
			}
		}
		
		//tu quy chat 3 doi thong den 4 doi thong
		if(pre.size() <= 8 && pre.size() >= 6 && len == 4) {
			for(int i = 0; i < 3; i++) {
				if(chosenCard.get(i).getCost() != chosenCard.get(i+1).getCost()) {
					return false;
				}
			}
			return true;
		}
		return false;
	}
}