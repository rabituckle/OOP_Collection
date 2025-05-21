package group17;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Game extends JPanel implements ActionListener{
	protected JFrame window = new JFrame();
	protected int height = 670, width = 970;
	private Color background = new Color(4, 191, 29);
	protected int bots;
	protected int players;
	protected int playersNum;
	protected int cardHeight = 140;
	protected int cardWidth = 89;
	protected int disX = 30;
	protected int disY = 30;
	protected ArrayList<Card>[] playerCard;
	protected ArrayList<Card> deck;
	protected Card host = new Card();
	protected int maxCards;
	protected int turn = 0, show = 0;
	protected Graphics2D g2;
	protected Map<Card, Rectangle>[] mapCard;
	protected ArrayList<Integer>[] cardState;
	protected ArrayList<Rectangle> btn = new ArrayList<Rectangle>();
	
	public Game(String title, int players, int bots) {
		this.bots = bots;
		this.players = players + 1;
		playersNum = this.bots + this.players;
		window.setTitle(title);
		window.setSize(width, height);
		window.setLocationRelativeTo(null);
		window.setResizable(false);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setVisible(true);
		this.setBackground(background);
		deck = host.createDeck();
		getButtonShow();
		window.add(this);
	}
	
	public ImageIcon getImageCard(String image) {
		String filepath = "/images/" + image + ".png";
		return new ImageIcon(getClass().getResource(filepath));
	}
	
	public void paintCardsVertical(ArrayList<Card> deck, int turn, int x, int y, int w, int h) {
		for(int i = 0; i < deck.size(); i++) {
			if(cardState[turn].get(i) == 1) {
				g2.drawImage(getImageCard(deck.get(i).getValue()).getImage(), x - 20, y+i*disY, w, h, null);
				if(i < deck.size() - 1) {
					mapCard[turn].put(playerCard[turn].get(i), new Rectangle(x - 20, y+i*disY, w, disY));
				}
				else {
					mapCard[turn].put(playerCard[turn].get(i), new Rectangle(x - 20, y+i*disY, w, h));
				}
			}
			else{
				g2.drawImage(getImageCard(deck.get(i).getValue()).getImage(), x, y+i*disY, w, h, null);
				if(i < deck.size() - 1) {
					mapCard[turn].put(playerCard[turn].get(i), new Rectangle(x, y+i*disY, w, disY));
				}
				else {
					mapCard[turn].put(playerCard[turn].get(i), new Rectangle(x, y+i*disY, w, h));
				}
			}
		}
	}
	
	public void paintCardsHorizontal(ArrayList<Card> deck, int turn, int x, int y, int w, int h) {
		for(int i = 0; i < deck.size(); i++) {
			if(cardState[turn].get(i) == 1) {
				g2.drawImage(getImageCard(deck.get(i).getValue()).getImage(), x+i*disX, y - 20, w, h, null);
				if(i < deck.size() - 1) {
					mapCard[turn].put(playerCard[turn].get(i), new Rectangle(x+i*disX, y - 20, disX, h));
				}
				else {
					mapCard[turn].put(playerCard[turn].get(i), new Rectangle(x+i*disX, y - 20, w, h));
				}
			}
			else{
				g2.drawImage(getImageCard(deck.get(i).getValue()).getImage(), x+i*disX, y, w, h, null);
				if(i < deck.size() - 1) {
					mapCard[turn].put(playerCard[turn].get(i), new Rectangle(x+i*disX, y, disX, h));
				}
				else {
					mapCard[turn].put(playerCard[turn].get(i), new Rectangle(x+i*disX, y, w, h));
				}
			}
		}
	}
	
	public void getButtonShow() {
		if(players > 0) {
			btn.add(new Rectangle(445, 80, 80, 30));
		}
		if(players > 1) {
			btn.add(new Rectangle(770, 225, 80, 30));
		}
		if(players > 2) {
			btn.add(new Rectangle(445, 505, 80, 30));
		}
		if(players == 4) {
			btn.add(new Rectangle(35, 225, 80, 30));
		}
	}
	
	public void setCardButton() {
		cardState = new ArrayList[players];
		mapCard = new HashMap[players];
		for(int i = 0; i < players; i++) {
			mapCard[i] = new HashMap<Card, Rectangle>();
			cardState[i] = new ArrayList<Integer>();
		}
		for(int i = 0; i < maxCards; i++) {
			for(int j = 1; j <= players; j++) {
				cardState[j-1].add(0);
			}
		}
	}
	
	public void drawPlayerPanelHorizontal(int cardsNum, int x, int y, int w, int h, String player) {
		drawButton(x, y, w, h);
		g2.drawImage(getImageCard("cardback").getImage(), x+10, y+15, cardWidth, cardHeight, null);
		g2.setFont(getFont().deriveFont(28F));
		int length = (int)g2.getFontMetrics().getStringBounds(player, g2).getWidth();
		g2.drawString(player, x+(w+cardWidth+10)/2-length/2, y+40);
		drawButton(x+125, y+60, 80, 30, "show", 20);
		g2.setFont(getFont().deriveFont(32F));
		g2.drawString(Integer.toString(cardsNum), x+150, y+145);
	}
	
	public void drawPlayerPanelVertical(int cardsNum, int x, int y, int w, int h, String player) {
		drawButton(x, y, w, h);
		g2.drawImage(getImageCard("cardback").getImage(), x+14, y+120, cardWidth, cardHeight, null);
		g2.setFont(getFont().deriveFont(28F));
		int length = (int)g2.getFontMetrics().getStringBounds(player, g2).getWidth();
		g2.drawString(player, w/2-length/2+x, y+30);
		drawButton(x+20, y+45, 80, 30, "show", 20);
		g2.setFont(getFont().deriveFont(32F));
		g2.drawString(Integer.toString(cardsNum), x+45, y+110);
	}
	
	public void drawButton(int x, int y, int w, int h) {
		g2.setColor(new Color(0, 0, 0, 130));
		g2.fillRoundRect(x, y, w, h, 35, 35);
		
		g2.setColor(new Color(255, 255, 255));
		g2.setStroke(new BasicStroke(5));
		g2.drawRoundRect(x, y, w, h, 25, 25);
	}
	
	public void drawButton(int x, int y, int w, int h, String btn, float size) {
		g2.setColor(new Color(0, 0, 0, 130));
		g2.fillRoundRect(x, y, w, h, 0, 0);
		
		g2.setColor(new Color(255, 255, 255));
		g2.setStroke(new BasicStroke(3));
		g2.drawRoundRect(x, y, w, h, 0, 0);
		
		g2.setFont(getFont().deriveFont(size));
		g2.drawString(btn, x+18, y+21);
	}
	
	public void drawStatePlaying() {
		for(int i = 0; i < players; i++) {
			String player = "Player " + (i+1);
			if(i == 0 && turn != i) {
				drawPlayerPanelHorizontal(playerCard[i].size(), 320, 20, 250, 175, player);
			}
			else if(i == 1 && turn != i) {
				drawPlayerPanelVertical(playerCard[i].size(), 750, 180, 120, 270, player);
			}
			else if(i == 2 && turn != i) {
				drawPlayerPanelHorizontal(playerCard[i].size(), 320, height - 225, 250, 175, player);
			}
			else if(i == 3 && turn != i) {
				drawPlayerPanelVertical(playerCard[i].size(), 15, 180, 120, 270, player);
			}
		}
		
		for(int i = players; i < playersNum; i++) {
			String player = "Bot " + (i - players + 1);
			if(i == 1) {
				drawPlayerPanelVertical(playerCard[i].size(), 750, 180, 120, 270, player);
			}
			else if(i == 2) {
				drawPlayerPanelHorizontal(playerCard[i].size(), 320, height - 225, 250, 175, player);
			}
			else if(i == 3) {
				drawPlayerPanelVertical(playerCard[i].size(), 15, 180, 120, 270, player);
			}
		}
	}
	
	public void drawStartState() {
		for(int i = 0; i < players; i++) {
			String player = "Player " + (i+1);
			if(i == 0) {
				drawPlayerPanelHorizontal(playerCard[i].size(), 320, 20, 250, 175, player);
			}
			else if(i == 1) {
				drawPlayerPanelVertical(playerCard[i].size(), 750, 180, 120, 270, player);
			}
			else if(i == 2) {
				drawPlayerPanelHorizontal(playerCard[i].size(), 320, height - 225, 250, 175, player);
			}
			else if(i == 3) {
				drawPlayerPanelVertical(playerCard[i].size(), 15, 180, 120, 270, player);
			}
		}
		
		for(int i = players; i < playersNum; i++) {
			String player = "Bot " + (i - players + 1);
			if(i == 1) {
				drawPlayerPanelVertical(playerCard[i].size(), 750, 180, 120, 270, player);
			}
			else if(i == 2) {
				drawPlayerPanelHorizontal(playerCard[i].size(), 320, height - 225, 250, 175, player);
			}
			else if(i == 3) {
				drawPlayerPanelVertical(playerCard[i].size(), 15, 180, 120, 270, player);
			}
		}
	}
	
	public void drawTurn(float size) {
		if(turn < players) {
			String player = "Turn: Player " + (turn + 1);
			g2.setColor(Color.magenta);
			g2.setFont(getFont().deriveFont(size));
			g2.drawString(player, 20, 30);
		}
		else {
			String player = "Turn: Bot " + (turn - players + 1);
			g2.setColor(Color.magenta);
			g2.setFont(getFont().deriveFont(size));
			g2.drawString(player, 20, 30);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
}