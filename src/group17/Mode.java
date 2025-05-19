package group17;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class Mode extends JPanel implements KeyListener, Runnable{
	Graphics2D g2;
	private int modeNum = 0;
	private int typeGame = 0;
	private int players = 0;
	private int botsMode = 1;
	private int bots = 0, minBots, maxBots;
	private String modeState = "Mode";
	Thread gameThread;
	private GameTLMN game2;
	private GameTLMB game3;
	//private GameBaCay game1;
	
	public Mode() {
		this.setPreferredSize(new Dimension(500, 500));
		this.setDoubleBuffered(true);
		this.addKeyListener(this);
		this.setFocusable(true);
	}
	
	public int getPlayers() {
		return players;
	}
	
	public int getBots() {
		return bots;
	}
	
	public String getTypeGame() {
		if(typeGame == 0) {
			return "Ba cây";
		}
		if(typeGame == 1) {
			return "Tiến lên miền nam";
		}
		return "Tiến lên miền bắc";
	}
	
	public void game() {
		gameThread = new Thread(this);
		gameThread.start();
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		int code = e.getKeyCode();
		if(modeState.equals("Mode")) {
			if(code == KeyEvent.VK_DOWN) {
				modeNum++;
				if(modeNum > 2) {
					modeNum = 0;
				}
			}
			
			if(code == KeyEvent.VK_UP) {
				modeNum--;
				if(modeNum < 0) {
					modeNum = 2;
				}
			}
			
			if(code == KeyEvent.VK_ENTER) {
				if(modeNum != 2) {
					modeState = "TypeGameMode";
					typeGame = 0;
				}
				
				if(modeNum == 2) {
					System.exit(0);
				}
			}
		}
		else if(modeState.equals("TypeGameMode")) {
			if(code == KeyEvent.VK_DOWN) {
				typeGame++;
				if(typeGame > 3) {
					typeGame = 0;
				}
			}
			
			if(code == KeyEvent.VK_UP) {
				typeGame--;
				if(typeGame < 0) {
					typeGame = 3;
				}
			}
			
			if(code == KeyEvent.VK_ENTER) {
				if(typeGame != 3) {
					modeState = "PlayersMode";
					players = 0;
				}
				
				if(typeGame == 3) {
					modeState = "Mode";
					modeNum = 0;
				}
			}
		}
		else if(modeState.equals("PlayersMode")) {
			if(code == KeyEvent.VK_DOWN) {
				players++;
				if(players > 4) {
					players = 0;
				}
			}
			
			if(code == KeyEvent.VK_UP) {
				players--;
				if(players < 0) {
					players = 4;
				}
			}
			
			if(code == KeyEvent.VK_ENTER) {
				if(players < 3) {
					modeState = "BotsNum";
					maxBots = 3 - players;
					botsMode = 0;
					if(players == 0) {
						minBots = 1;
					}
					else {
						minBots = 0;
					}
					bots = minBots;
				}
				
				if(players == 3) {
					//add later
					getGameType();
				}
				
				if(players == 4) {
					modeState = "TypeGameMode";
					typeGame = 0;
				}
			}
		}
		else if(modeState.equals("BotsNum")) {
			if(code == KeyEvent.VK_DOWN) {
				botsMode++;
				if(botsMode > 1) {
					botsMode = 0;
				}
			}
			
			if(code == KeyEvent.VK_UP) {
				botsMode--;
				if(botsMode < 0) {
					botsMode = 1;
				}
			}
			
			if(botsMode == 0) {
				if(code == KeyEvent.VK_LEFT) {
					bots--;
					if(bots < minBots) {
						bots = minBots;
					}
				}
				if(code == KeyEvent.VK_RIGHT) {
					bots++;
					if(bots > maxBots) {
						bots = maxBots;
					}
				}
				if(code == KeyEvent.VK_ENTER) {
					//add later
					gameThread = null;
					getGameType();
				}
			}
			if(botsMode == 1 && code == KeyEvent.VK_ENTER) {
				modeState = "PlayersMode";
				players = 0;
				bots = minBots;
			}
		}
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		String background = "/images/background.jpg";
		ImageIcon image = new ImageIcon(getClass().getResource(background));
		g.drawImage(image.getImage(), 0, 0, getWidth(), getHeight(), null);
		
		g2 = (Graphics2D)g;
		if(modeState.equals("Mode")) {
			drawOptionWindow(200, 100, 300, 200);
		}
		else if(modeState.equals("TypeGameMode")) {
			drawTypeGameMode(200, 100, 300, 210);
		}
		else if(modeState.equals("PlayersMode")) {
			drawNumberPlayers(200, 100, 300, 240);
		}
		else if(modeState.equals("BotsNum")) {
			botsBox(200, 100, 300, 200);
		}
		g2.dispose();
	}
	
	public void drawOptionWindow(int x, int y, int width, int height) {
		g2.setColor(new Color(0, 0, 0, 150));
		g2.fillRoundRect(x, y, width, height, 35, 35);
		
		g2.setColor(new Color(255, 255, 255));
		g2.setStroke(new BasicStroke(5));
		g2.drawRoundRect(x+5, y+5, width-10, height-10, 25, 25);
		
		g2.setFont(getFont().deriveFont(32F));
		g2.drawString("Menu", 308, 150);
		g2.drawLine(210, 160, 490, 160);
		
		drawOption("Basic user interface", 20, 250, 200);
		if(modeNum == 0) {
			g2.drawString(">", 230, 200);
		}
		
		drawOption("Graphical user interface", 20, 250, 230);
		if(modeNum == 1) {
			g2.drawString(">", 230, 230);
		}
		
		drawOption("Quit", 20, 250, 260);
		if(modeNum == 2) {
			g2.drawString(">", 230, 260);
		}
	}
	
	public void drawTypeGameMode(int x, int y, int width, int height) {
		g2.setColor(new Color(0, 0, 0, 150));
		g2.fillRoundRect(x, y, width, height, 35, 35);
		
		g2.setColor(new Color(255, 255, 255));
		g2.setStroke(new BasicStroke(5));
		g2.drawRoundRect(x+5, y+5, width-10, height-10, 25, 25);
		
		g2.setFont(getFont().deriveFont(32F));
		g2.drawString("Games", 308, 150);
		g2.drawLine(210, 160, 490, 160);
		
		drawOption("Ba cây", 20, 250, 200);
		if(typeGame == 0) {
			g2.drawString(">", 230, 200);
		}
		
		drawOption("Tiến lên miền nam", 20, 250, 230);
		if(typeGame == 1) {
			g2.drawString(">", 230, 230);
		}
		
		drawOption("Tiến lên miền bắc", 20, 250, 260);
		if(typeGame == 2) {
			g2.drawString(">", 230, 260);
		}
		
		drawOption("Back", 20, 250, 290);
		if(typeGame == 3) {
			g2.drawString(">", 230, 290);
		}
	}
	
	public void drawNumberPlayers(int x, int y, int width, int height) {
		g2.setColor(new Color(0, 0, 0, 150));
		g2.fillRoundRect(x, y, width, height, 35, 35);
		
		g2.setColor(new Color(255, 255, 255));
		g2.setStroke(new BasicStroke(5));
		g2.drawRoundRect(x+5, y+5, width-10, height-10, 25, 25);
		
		g2.setFont(getFont().deriveFont(32F));
		g2.drawString("Players", 308, 150);
		g2.drawLine(210, 160, 490, 160);
		
		drawOption("1 player", 20, 250, 200);
		if(players == 0) {
			g2.drawString(">", 230, 200);
		}
		
		drawOption("2 players", 20, 250, 230);
		if(players == 1) {
			g2.drawString(">", 230, 230);
		}
		
		drawOption("3 players", 20, 250, 260);
		if(players == 2) {
			g2.drawString(">", 230, 260);
		}
		
		drawOption("4 players", 20, 250, 290);
		if(players == 3) {
			g2.drawString(">", 230, 290);
		}
		
		drawOption("Back", 20, 250, 320);
		if(players == 4) {
			g2.drawString(">", 230, 320);
		}
	}
	
	public void botsBox(int x, int y, int width, int height) {
		g2.setColor(new Color(0, 0, 0, 150));
		g2.fillRoundRect(x, y, width, height, 35, 35);
		
		g2.setColor(new Color(255, 255, 255));
		g2.setStroke(new BasicStroke(5));
		g2.drawRoundRect(x+5, y+5, width-10, height-10, 25, 25);
		
		drawOption("Number of bots: ", 25, 250, 180);
		g2.drawString(Integer.toString(bots), 430, 180);
		g2.drawRect(255, 200, 180, 30);
		g2.drawString("Back", 250, 270);
		g2.fillRect(255, 200, 60*bots, 30);
		if(botsMode == 0) {
			g2.drawString(">", 230, 220);
		}
		
		if(botsMode == 1) {
			g2.drawString(">", 230, 270);
		}
	}
	
	public void drawOption(String option, int size, int x, int y) {
		g2.setFont(new Font("Calibri", Font.PLAIN, size));
		g2.drawString(option, x, y);
	}
	
	public void getGameType() {
		if(typeGame == 0) {
			//game1 = new GameBaCay("Ba cây", players, bots);
		}
		else if(typeGame == 1) {
			game2 = new GameTLMN("Tiến lên miền nam", players, bots);
			game2.gameStart();
		}
		else {
			game3 = new GameTLMB("Tiến lên miền bắc", players, bots);
			game3.gameStart();
		}
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(gameThread != null) {
			repaint();
		}
	}
}