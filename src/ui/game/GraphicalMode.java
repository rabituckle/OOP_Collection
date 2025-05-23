package ui.game;

import javax.swing.*;

import game.ControlGame;

import java.awt.*;
import java.util.ArrayList;

import utilities.card.Card;

public class GraphicalMode extends GameUI {
	
    private static final int RAISE_HEIGHT = HEIGHT_CARD/7; // Vertical raise amount
    private static final int SHIFT_HORIZONTAL = WIDTH_CARD/3; // Horizontal shift amount
    private Image backgroundImage;

    public GraphicalMode() {
    	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	frame.setLayout(new BorderLayout());
        frame.setSize(WIDTH_WINDOW, HEIGHT_WINDOW);
    	backgroundImage = new ImageIcon(getClass().getResource("/images/table_background.jpg")).getImage();
    }
    
    public ImageIcon getImageCard(String image) {
        String filepath = "/images/" + image + ".png";
        return new ImageIcon(getClass().getResource(filepath));
    }
    
    @Override
	public void drawBackground(ControlGame control) {
		// TODO Auto-generated method stub
		player = control.getPlayer();
		g2.drawImage(backgroundImage, 0, 0, control.getWidth(), control.getHeight(), null);
	}
    
    @Override
	public void drawCardsVertical(ControlGame control, int x) {
    	player = control.getPlayer();
    	mapCard = player.getMapCard();
    	cardState = player.getCardState();
    	drawPlayerHighlight(control);
    	if(turn == 1) {
    		x = control.getWidth() - WIDTH_CARD - x;
    	}
    	int y = (player.getNumCards() - 1)*OFFSET_Y + HEIGHT_CARD;
		y = control.getHeight()/2 - y/2;
		for(int i = 0; i < player.getCards().size(); i++) {
			if(cardState.get(i) == 1) {
				g2.drawImage(getImageCard(player.getCard(i).getValue()).getImage(), x + (turn - 2)*SHIFT_HORIZONTAL, y+i*OFFSET_Y, WIDTH_CARD, HEIGHT_CARD, null);
				if(i < player.getCards().size() - 1) {
					mapCard.put(player.getCard(i), new Rectangle(x + (turn - 2)*SHIFT_HORIZONTAL, y+i*OFFSET_Y, WIDTH_CARD, OFFSET_Y));
				}
				else {
					mapCard.put(player.getCard(i), new Rectangle(x + (turn - 2)*SHIFT_HORIZONTAL, y+i*OFFSET_Y, WIDTH_CARD, HEIGHT_CARD));
				}
			}
			else{
				g2.drawImage(getImageCard(player.getCard(i).getValue()).getImage(), x, y+i*OFFSET_Y, WIDTH_CARD, HEIGHT_CARD, null);
				if(i < player.getCards().size() - 1) {
					mapCard.put(player.getCard(i), new Rectangle(x, y+i*OFFSET_Y, WIDTH_CARD, OFFSET_Y));
				}
				else {
					mapCard.put(player.getCard(i), new Rectangle(x, y+i*OFFSET_Y, WIDTH_CARD, HEIGHT_CARD));
				}
			}
		}
	}

	@Override
	public void drawCardsHorizontal(ControlGame control, int y) {
		player = control.getPlayer();
    	mapCard = player.getMapCard();
    	cardState = player.getCardState();
		drawPlayerHighlight(control);
		if(turn == 2) {
			y = control.getHeight() - HEIGHT_CARD - y;
		}
		int x = (player.getNumCards() - 1)*OFFSET_X + WIDTH_CARD;
		x = control.getWidth()/2 - x/2;
		for(int i = 0; i < player.getCards().size(); i++) {
			if(cardState.get(i) == 1) {
				g2.drawImage(getImageCard(player.getCard(i).getValue()).getImage(), x+i*OFFSET_X, y - RAISE_HEIGHT, WIDTH_CARD, HEIGHT_CARD, null);
				if(i < player.getCards().size() - 1) {
					mapCard.put(player.getCard(i), new Rectangle(x+i*OFFSET_X, y - RAISE_HEIGHT, OFFSET_X, HEIGHT_CARD));
				}
				else {
					mapCard.put(player.getCard(i), new Rectangle(x+i*OFFSET_X, y - RAISE_HEIGHT, WIDTH_CARD, HEIGHT_CARD));
				}
			}
			else{
				g2.drawImage(getImageCard(player.getCard(i).getValue()).getImage(), x+i*OFFSET_X, y, WIDTH_CARD, HEIGHT_CARD, null);
				if(i < player.getCards().size() - 1) {
					mapCard.put(player.getCard(i), new Rectangle(x+i*OFFSET_X, y, OFFSET_X, HEIGHT_CARD));
				}
				else {
					mapCard.put(player.getCard(i), new Rectangle(x+i*OFFSET_X, y, WIDTH_CARD, HEIGHT_CARD));
				}
			}
		}
	}

	@Override
	public void drawChosenCards(ArrayList<Card> chosenCard, ControlGame control) {
		int x = (chosenCard.size() - 1)*OFFSET_X + WIDTH_CARD;
		x = control.getWidth()/2 - x/2;
		int y = HEIGHT_CARD;
		y = control.getHeight()/2 - y/2;
		for(int i = 0; i < chosenCard.size(); i++) {
			g2.drawImage(getImageCard(chosenCard.get(i).getValue()).getImage(), x+i*OFFSET_X, y, WIDTH_CARD, HEIGHT_CARD, null);
		}
	}

	@Override
	public void drawHidingStateVertical(ControlGame control, int x) {
		player = control.getPlayer();
		// Length of player's name
		int length = (int)g2.getFontMetrics(getFont().deriveFont(35F)).stringWidth(player.getName());
		int w = (WIDTH_CARD > length ? WIDTH_CARD : length) + 50;
		int h = 210;
		int y = control.getHeight()/2 - h/2;
		g2.setColor(new Color(0, 0, 0, 130));
		g2.fillRoundRect(x, y, w, h, 35, 35);
		g2.setColor(new Color(255, 255, 255));
		g2.setStroke(new BasicStroke(5));
		g2.drawRoundRect(x, y, w, h, 25, 25);
		// Draw player's name
		g2.setFont(getFont().deriveFont(35F));
		g2.drawString(player.getName(), (w-length)/2+x, y+35);
		// Draw back card
		g2.drawImage(getImageCard("cardback").getImage(), x+w/2-WIDTH_CARD/2, y+45, WIDTH_CARD, HEIGHT_CARD, null);
		// Draw the number of cards remaining
		g2.setFont(getFont().deriveFont(28F));
		length = (int)g2.getFontMetrics(getFont().deriveFont(28F)).stringWidth(player.getNumCards()+"");
		g2.drawString(player.getNumCards()+"", w/2-length/2+x, y+h/2);
	}

	@Override
	public void drawHidingStateHorizontal(ControlGame control, int y) {
		player = control.getPlayer();
		// length of player's name
		int length = (int)g2.getFontMetrics(getFont().deriveFont(35F)).stringWidth(player.getName());
		int w = length + WIDTH_CARD + 60;
		int h = HEIGHT_CARD + 30;
		int x = control.getWidth()/2 - w/2;
		g2.setColor(new Color(0, 0, 0, 130));
		g2.fillRoundRect(x, y, w, h, 35, 35);
		g2.setColor(new Color(255, 255, 255));
		g2.setStroke(new BasicStroke(5));
		g2.drawRoundRect(x, y, w, h, 25, 25);
		// Draw player's name
		g2.setFont(getFont().deriveFont(35F));
		g2.drawString(player.getName(), (w+15+WIDTH_CARD-length)/2+x, y+h/2);
		// Draw back card
		g2.drawImage(getImageCard("cardback").getImage(), x+15, y+(h-HEIGHT_CARD)/2, WIDTH_CARD, HEIGHT_CARD, null);
		// Draw the number of cards remaining
		g2.setFont(getFont().deriveFont(28F));
		length = (int)g2.getFontMetrics(getFont().deriveFont(28F)).stringWidth(player.getNumCards()+"");
		g2.drawString(player.getNumCards()+"", WIDTH_CARD/2-length/2+x+15, y+h/2);
	}
	
	public void drawHidingState(ControlGame control, int x, int y) {
		int savedTurn = control.getTurn();
		int numPlayers = control.getNumPlayers();
        if(numPlayers >= 1) {
        	control.setTurn(0);
        	drawHidingStateHorizontal(control, y);
        }
        if(numPlayers >= 2) {
        	control.setTurn(1);
        	player = control.getPlayer();
        	int w = (int)g2.getFontMetrics(getFont().deriveFont(35F)).stringWidth(player.getName());
        	w = (WIDTH_CARD > w ? WIDTH_CARD : w) + 75;
        	drawHidingStateVertical(control, control.getWidth()-w);
        }
        if(numPlayers >= 3) {
        	control.setTurn(2);
        	drawHidingStateHorizontal(control, control.getHeight()-195);
        }
        if(numPlayers == 4){
        	control.setTurn(3);
        	drawHidingStateVertical(control, x);
        }
        control.setTurn(savedTurn);
	}
	
	public void drawStatePlaying(ControlGame control, int x, int y) {
		turn = control.getTurn();
		int savedTurn = turn;
		for(int i = 0; i < control.getNumPlayers(); i++) {
			control.setTurn(i);
			if(i == 0 && turn != i) {
				drawHidingStateHorizontal(control, y);
			}
			else if(i == 1 && turn != i) {
				player = control.getPlayer();
				int w = (int)g2.getFontMetrics(getFont().deriveFont(35F)).stringWidth(player.getName());
		        w = (WIDTH_CARD > w ? WIDTH_CARD : w) + 75;
		        drawHidingStateVertical(control, control.getWidth()-w);
			}
			else if(i == 2 && turn != i) {
				drawHidingStateHorizontal(control, control.getHeight()-195);
			}
			else if(i == 3 && turn != i) {
				drawHidingStateVertical(control, x);
			}
		}
		control.setTurn(savedTurn);
	}
	
    // Phương thức vẽ highlight cho người chơi hiện tại
    public void drawPlayerHighlight(ControlGame control) {
    	player  = control.getPlayer();
    	turn = control.getTurn();
        g2.setColor(new Color(255, 255, 0, 100)); // Màu vàng nhạt, semi-transparent
        
        int x, y, width, height;
        switch(turn) {
            case 0: // Người chơi trên cùng
            	x = (player.getNumCards() - 1)*OFFSET_X + WIDTH_CARD;
        		x = control.getWidth()/2 - x/2 - 25;
                y = 20;
                width = (player.getNumCards() - 1)*OFFSET_X + WIDTH_CARD + 50;
                height = HEIGHT_CARD + 10;
                break;
            case 1: // Người chơi bên trái
            	x = control.getWidth() - WIDTH_CARD - 40;
                y = (player.getNumCards() - 1)*OFFSET_Y + HEIGHT_CARD;
        		y = control.getHeight()/2 - y/2 - 25;
                width = WIDTH_CARD + 20;
                height = (player.getNumCards() - 1)*OFFSET_Y + HEIGHT_CARD + 50;
                break;
            case 2: // Người chơi dưới cùng
            	x = (player.getNumCards() - 1)*OFFSET_X + WIDTH_CARD;
        		x = control.getWidth()/2 - x/2 - 25;
                y = control.getHeight() - HEIGHT_CARD - 30;
                width = (player.getNumCards() - 1)*OFFSET_X + WIDTH_CARD + 50;
                height = HEIGHT_CARD + 10;
                break;
            case 3: // Người chơi dưới cùng
                x = 20;
                y = (player.getNumCards() - 1)*OFFSET_Y + HEIGHT_CARD;
        		y = control.getHeight()/2 - y/2 - 25;
                width = WIDTH_CARD + 20;
                height = (player.getNumCards() - 1)*OFFSET_Y + HEIGHT_CARD + 50;
                break;
            default:
                return;
        }
        
        // Vẽ hình chữ nhật highlight
        g2.fillRoundRect(x, y, width, height, 20, 20);
        
        // Vẽ viền cho highlight
        g2.setColor(Color.YELLOW);
        g2.setStroke(new BasicStroke(3));
        g2.drawRoundRect(x, y, width, height, 20, 20);
    }
	
	@Override
	public void drawTurn(ControlGame control) {
		// Hiển thị thông tin về người chơi hiện tại
		player = control.getPlayer();
	    g2.setColor(Color.WHITE);
	    g2.setFont(new Font("Arial", Font.BOLD, 20));
	    g2.drawString("Current turn: " + player.getName(), 20, 20);
	}

	@Override
	public void drawChips(ControlGame control, int x, int y) {
		// TODO Auto-generated method stub
		int savedTurn = control.getTurn();
		int numPlayers = control.getNumPlayers();
        if(numPlayers >= 1) {
        	control.setTurn(0);
        	player = control.getPlayer();
        	int length = (int)GameUI.g2.getFontMetrics(getFont().deriveFont(35F)).stringWidth(player.getName());
    		int w = length + GameUI.WIDTH_CARD + 60;
        	w = control.getWidth()/2 + w/2;
            drawChip(control, w + 5, y);
        }
        if(numPlayers >= 2) {
        	control.setTurn(1);
        	player = control.getPlayer();
        	int w = control.getWidth() - x;
    		int h = 210;
    		h = control.getHeight()/2 + h/2;
            drawChip(control, w - CHIP_SIZE, h + 5);
        }
        if(numPlayers >= 3) {
        	control.setTurn(2);
        	player = control.getPlayer();
        	int length = (int)GameUI.g2.getFontMetrics(getFont().deriveFont(35F)).stringWidth(player.getName());
    		int w = length + GameUI.WIDTH_CARD + 60;
        	w = control.getWidth()/2 - w/2;
        	int h = control.getHeight() - y;
            drawChip(control, w - CHIP_SIZE - 5, h - CHIP_SIZE);
        }
        if(numPlayers == 4){
        	control.setTurn(3);
        	player = control.getPlayer();
    		int h = 210;
    		h = control.getHeight()/2 - h/2;
            drawChip(control, x, h - CHIP_SIZE - 5);
        }
        control.setTurn(savedTurn);
	}
}
