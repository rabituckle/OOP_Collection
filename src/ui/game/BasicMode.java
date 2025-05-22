package ui.game;

import javax.swing.*;

import game.ControlGame;

import java.awt.*;
import java.util.ArrayList;

import utilities.card.Card;

public class BasicMode extends GameUI {
    
    //fix kích thước
    private static final int CARD_SIZE = 42; //size of square ~ a card 
    private static final int SPACING = 2; //space between 2 cards
    private Color backgroundColor = new Color(0, 130, 0); //green rgb background

    //constructor 
    public BasicMode() {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.setSize(WIDTH_WINDOW, HEIGHT_WINDOW);
    }

    @Override
    public void drawBackground(ControlGame control) {
    	//create the color for BG and size of the window (using g2 - extend from GameUI - to draw)
        player = control.getPlayer();
        g2.setColor(backgroundColor);
        g2.fillRect(0, 0, control.getWidth(), control.getHeight());
    }

    @Override
    public void drawCardsVertical(ControlGame control, int x) {
        player = control.getPlayer();
        mapCard = player.getMapCard();
        cardState = player.getCardState();
        turn = control.getTurn();
        
        //highlight current player
        drawPlayerHighlight(control);
        
        //left/right margin then we set the start position of x => center
        if (turn == 1) {
            x = control.getWidth() - CARD_SIZE - x - 20; 
        } else if (turn == 3) {
            x = x + 20; 
        }
        
        //compute the start position of y => center
        int totalHeight = player.getNumCards() * CARD_SIZE;
        int y = control.getHeight() / 2 - totalHeight / 2;
        
        // position y of each card
        for (int i = 0; i < player.getCards().size(); i++) {
            int cardY = y + i * CARD_SIZE;
            
            //draw card square
            if (cardState.get(i) == 1) { 
            	//selected card
                g2.setColor(new Color(200, 200, 255));
                g2.fillRect(x, cardY, CARD_SIZE, CARD_SIZE);
                g2.setColor(Color.BLUE);
                g2.drawRect(x, cardY, CARD_SIZE, CARD_SIZE);
                
                //set the position x - y for each card
                mapCard.put(player.getCard(i), new Rectangle(x, cardY, CARD_SIZE, CARD_SIZE));
                
                //draw card value
                g2.setColor(Color.BLACK);
                String cardValue = player.getCard(i).getValue();
                drawCenteredString(g2, cardValue, x, cardY, CARD_SIZE, CARD_SIZE);
            } else {
                //unselected cards
                g2.setColor(Color.WHITE);
                g2.fillRect(x, cardY, CARD_SIZE, CARD_SIZE);
                g2.setColor(Color.BLACK);
                g2.drawRect(x, cardY, CARD_SIZE, CARD_SIZE);
                
                //set the position x - y for each card
                mapCard.put(player.getCard(i), new Rectangle(x, cardY, CARD_SIZE, CARD_SIZE));
                
                //draw card value
                String cardValue = player.getCard(i).getValue();
                drawCenteredString(g2, cardValue, x, cardY, CARD_SIZE, CARD_SIZE);
            }
        }
    }

    @Override
    public void drawCardsHorizontal(ControlGame control, int y) {
        player = control.getPlayer();
        mapCard = player.getMapCard();
        cardState = player.getCardState();
        turn = control.getTurn();
        
        //highlight current player
        drawPlayerHighlight(control);
        
        //left/right margin then we set the start position of y => center
        if (turn == 0) {
            y = y + 20; 
        } else if (turn == 2) {
            y = control.getHeight() - CARD_SIZE - y - 20; 
        }
        
      //compute the start position of x => center
        int totalWidth = player.getNumCards() * CARD_SIZE;
        int x = control.getWidth() / 2 - totalWidth / 2;
        
        for (int i = 0; i < player.getCards().size(); i++) {
        	// position x of each card
            int cardX = x + i * CARD_SIZE;
            
            //draw card square
            if (cardState.get(i) == 1) {
                //selected cards
                g2.setColor(new Color(200, 200, 255));
                g2.fillRect(cardX, y, CARD_SIZE, CARD_SIZE);
                g2.setColor(Color.BLUE);
                g2.drawRect(cardX, y, CARD_SIZE, CARD_SIZE);
                
                //set the position x - y for each card
                mapCard.put(player.getCard(i), new Rectangle(cardX, y, CARD_SIZE, CARD_SIZE));
                
                //draw card value
                g2.setColor(Color.BLACK);
                String cardValue = player.getCard(i).getValue();
                drawCenteredString(g2, cardValue, cardX, y, CARD_SIZE, CARD_SIZE);
            } else {
                //unselected cards
                g2.setColor(Color.WHITE);
                g2.fillRect(cardX, y, CARD_SIZE, CARD_SIZE);
                g2.setColor(Color.BLACK);
                g2.drawRect(cardX, y, CARD_SIZE, CARD_SIZE);
                
                //set the position x - y for each card
                mapCard.put(player.getCard(i), new Rectangle(cardX, y, CARD_SIZE, CARD_SIZE));
                
                //draw card value
                String cardValue = player.getCard(i).getValue();
                drawCenteredString(g2, cardValue, cardX, y, CARD_SIZE, CARD_SIZE);
            }
        }
    }

    @Override
    public void drawChosenCards(ArrayList<Card> chosenCard, ControlGame control) {
        //compute the start position of x and y => center
        int totalWidth = chosenCard.size() * CARD_SIZE;
        int x = control.getWidth() / 2 - totalWidth / 2;
        int y = control.getHeight() / 2 - CARD_SIZE / 2;
        
        for (int i = 0; i < chosenCard.size(); i++) {
        	//position x of each card
            int cardX = x + i * CARD_SIZE;
            
            //draw played cards in the center
            g2.setColor(new Color(240, 240, 240));
            g2.fillRect(cardX, y, CARD_SIZE, CARD_SIZE);
            g2.setColor(Color.BLACK);
            g2.drawRect(cardX, y, CARD_SIZE, CARD_SIZE);
            
            //draw card value
            String cardValue = chosenCard.get(i).getValue();
            drawCenteredString(g2, cardValue, cardX, y, CARD_SIZE, CARD_SIZE);
        }
    }

    @Override
    public void drawHidingStateVertical(ControlGame control, int x) {
        player = control.getPlayer();
        int numCards = player.getNumCards();
        
        //compute start position => the cards are centered
        int totalHeight = numCards * (CARD_SIZE + SPACING) - SPACING;
        int y = control.getHeight() / 2 - totalHeight / 2 + 20;
        
        //draw player name 
        g2.setColor(Color.WHITE);
        g2.setFont(getFont().deriveFont(20F));
        String playerName = player.getName();
        g2.drawString(playerName, x - 10, y - 5);
        
        //card back
        for (int i = 0; i < numCards; i++) {
            drawSimpleCardBack(x, y + i * (CARD_SIZE + SPACING), CARD_SIZE, CARD_SIZE);
        }
    }

    @Override
    public void drawHidingStateHorizontal(ControlGame control, int y) {
        player = control.getPlayer();
        int numCards = player.getNumCards();
        
        //compute total width needed for all cards
        int totalWidth = numCards * (CARD_SIZE + SPACING) - SPACING;
        int x = control.getWidth() / 2 - totalWidth / 2;
        
        //draw player name 
        g2.setColor(Color.WHITE);
        g2.setFont(getFont().deriveFont(20F));
        String playerName = player.getName();
        int nameWidth = (int)g2.getFontMetrics().stringWidth(playerName);
        g2.drawString(playerName, x + (totalWidth - nameWidth) / 2, y);
        
        //card back
        for (int i = 0; i < numCards; i++) {
            drawSimpleCardBack(x + i * (CARD_SIZE + SPACING), y + 10, CARD_SIZE, CARD_SIZE);
        }
    }

    @Override
    public void drawHidingState(ControlGame control, int x, int y) {
        int savedTurn = control.getTurn();
        int numPlayers = control.getNumPlayers();
        
        //draw all players' hidden states
        if (numPlayers >= 1) {
            control.setTurn(0);
            drawHidingStateHorizontal(control, y);
        }
        if (numPlayers >= 2) {
            control.setTurn(1);
            drawHidingStateVertical(control, control.getWidth() - x - CARD_SIZE);
        }
        if (numPlayers >= 3) {
            control.setTurn(2);
            drawHidingStateHorizontal(control, control.getHeight() - y - CARD_SIZE);
        }
        if (numPlayers == 4) {
            control.setTurn(3);
            drawHidingStateVertical(control, x);
        }
        
        //restore original turn
        control.setTurn(savedTurn);
    }

    @Override
    public void drawStatePlaying(ControlGame control, int x, int y) {
        turn = control.getTurn();
        int savedTurn = turn;
        
        //draw all players except the current one
        for (int i = 0; i < control.getNumPlayers(); i++) {
            control.setTurn(i);
            if (i == 0 && turn != i) {
                drawHidingStateHorizontal(control, y);
            } else if (i == 1 && turn != i) {
                drawHidingStateVertical(control, control.getWidth() - x - CARD_SIZE);
            } else if (i == 2 && turn != i) {
                drawHidingStateHorizontal(control, control.getHeight() - y - CARD_SIZE);
            } else if (i == 3 && turn != i) {
                drawHidingStateVertical(control, x);
            }
        }
        
        //restore original turn
        control.setTurn(savedTurn);
    }

    //draw highlight for current player
    public void drawPlayerHighlight(ControlGame control) {
        player = control.getPlayer();
        turn = control.getTurn();
        
        g2.setColor(new Color(255, 255, 0, 100)); // Light yellow, semi-transparent
        
        int x, y, width, height;
        switch(turn) {
            case 0: // Top player
                width = player.getNumCards() * CARD_SIZE + 20;
                x = control.getWidth() / 2 - width / 2;
                y = 40; // Moved down to make room for name
                height = CARD_SIZE + 10;
                break;
            case 1: // Right player
                x = control.getWidth() - CARD_SIZE - 60; // Moved left to make room for name
                height = player.getNumCards() * CARD_SIZE + 20;
                y = control.getHeight() / 2 - height / 2;
                width = CARD_SIZE + 20;
                break;
            case 2: // Bottom player
                width = player.getNumCards() * CARD_SIZE + 20;
                x = control.getWidth() / 2 - width / 2;
                y = control.getHeight() - CARD_SIZE - 50; // Moved up to make room for name
                height = CARD_SIZE + 10;
                break;
            case 3: // Left player
                x = 40; // Moved right to make room for name
                height = player.getNumCards() * CARD_SIZE + 20;
                y = control.getHeight() / 2 - height / 2;
                width = CARD_SIZE + 20;
                break;
            default:
                return;
        }
        
        //draw highlight rectangle
        g2.fillRoundRect(x, y, width, height, 15, 15);
        
        //draw border
        g2.setColor(Color.YELLOW);
        g2.setStroke(new BasicStroke(3));
        g2.drawRoundRect(x, y, width, height, 15, 15);
    }
    
    //card back
    private void drawSimpleCardBack(int x, int y, int width, int height) {
        //draw the card bg
        g2.setColor(new Color(200, 200, 200));//grey
        g2.fillRect(x, y, width, height);
        
        //draw an "X" in the card
        g2.setColor(Color.BLACK);
        g2.setStroke(new BasicStroke(2));
        g2.drawLine(x + 5, y + 5, x + width - 5, y + height - 5);
        g2.drawLine(x + width - 5, y + 5, x + 5, y + height - 5);
        
        //draw border around the card
        g2.setColor(Color.BLACK);
        g2.setStroke(new BasicStroke(1));
        g2.drawRect(x, y, width, height);
    }
    
    //text in card method
    private void drawCenteredString(Graphics2D g, String text, int x, int y, int width, int height) {
        FontMetrics metrics = g.getFontMetrics(g.getFont());
        int textX = x + (width - metrics.stringWidth(text)) / 2;
        int textY = y + ((height - metrics.getHeight()) / 2) + metrics.getAscent();
        g.drawString(text, textX, textY);
    }

	@Override
	public void drawTurn(ControlGame control) {
		// TODO Auto-generated method stub
		//draw the in4 of current player
		player = control.getPlayer();
        g2.setColor(Color.WHITE);
        g2.setFont(new Font("Times New Roman", Font.BOLD, 20));
        g2.drawString("Current turn: " + player.getName(), 18, 18);
	}

	@Override
	public void drawChips(ControlGame control, int x, int y) {
		// TODO Auto-generated method stub
		int savedTurn = control.getTurn();
        int numPlayers = control.getNumPlayers();
        
        //draw all players' hidden states
        if (numPlayers >= 1) {
            control.setTurn(0);
            int w = control.getWidth()/2 - CHIP_SIZE/2;
            drawChip(control, w, y + CARD_SIZE + 25);
        }
        if (numPlayers >= 2) {
            control.setTurn(1);
            int h = control.getHeight()/2 - CHIP_SIZE/2;
            drawChip(control, control.getWidth() - x - CARD_SIZE - CHIP_SIZE - 38, h + 15);
        }
        if (numPlayers >= 3) {
            control.setTurn(2);
            int w = control.getWidth()/2 - CHIP_SIZE/2;
            drawChip(control, w, control.getHeight() - y - CHIP_SIZE - CARD_SIZE - 25);
        }
        if (numPlayers == 4) {
            control.setTurn(3);
            int h = control.getHeight()/2 - CHIP_SIZE/2;
            drawChip(control, x + CARD_SIZE + 38, h + 15);
        }
        
        //restore original turn
        control.setTurn(savedTurn);
	}
}