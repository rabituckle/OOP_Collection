package ui.game;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

import game.ControlGame;
import utilities.card.Card;
import utilities.player.Player;

public abstract class GameUI extends JPanel {
	
	public static final int WIDTH_WINDOW = 1050;
	public static final int HEIGHT_WINDOW = 650;
    public static final int WIDTH_CARD = 89;
	public static final int HEIGHT_CARD = 140;
	public static final int OFFSET_X = WIDTH_CARD/3;
    public static final int OFFSET_Y = HEIGHT_CARD/5;
    public static final int CHIP_SIZE = 60;
	public static Graphics2D g2;
	protected JFrame frame = new JFrame();
	protected Map<Card, Rectangle> mapCard; //map:1 rectangle ~ 1 card
    protected ArrayList<Integer> cardState; //state of card 
    protected Player player; //current player
    protected int turn;// current turn
	
    public abstract void drawBackground(ControlGame control);
	public abstract void drawCardsVertical(ControlGame control, int x);
	public abstract void drawCardsHorizontal(ControlGame control, int y);
	public abstract void drawChosenCards(ArrayList<Card> chosenCard, ControlGame control);
	public abstract void drawHidingStateVertical(ControlGame control, int x);
	public abstract void drawHidingStateHorizontal(ControlGame control, int y);
	public abstract void drawHidingState(ControlGame control, int i, int j);
	public abstract void drawStatePlaying(ControlGame control, int i, int j);
	public abstract void drawTurn(ControlGame control);
	public abstract void drawChips(ControlGame control, int x, int y);
	
	public void drawWinner(ControlGame control, String winner) {
		// Hiển thị người thắng
        g2.setFont(new Font("Arial", Font.BOLD, 30));
        g2.setColor(Color.YELLOW);
        String winText = winner + " won";
        int textWidth = g2.getFontMetrics().stringWidth(winText);
        
        // Vẽ nền cho text
        g2.setColor(new Color(0, 0, 0, 180));
        g2.fillRoundRect(control.getWidth()/2 - textWidth/2 - 20, control.getHeight()/2 - 20, textWidth + 40, 50, 15, 15);
        
        // Vẽ border
        g2.setColor(new Color(255, 215, 0));
        g2.setStroke(new BasicStroke(3));
        g2.drawRoundRect(control.getWidth()/2 - textWidth/2 - 20, control.getHeight()/2 - 20, textWidth + 40, 50, 15, 15);
        
        // Vẽ text
        g2.setColor(Color.WHITE);
        g2.drawString(winText, control.getWidth()/2 - textWidth/2, control.getHeight()/2 + 10);
	}
	
	public void drawChip(ControlGame control, int x, int y) {
		player = control.getPlayer();
        ImageIcon chip =  new ImageIcon(getClass().getResource("/images/chip.png"));
        g2.drawImage(chip.getImage(), x, y, CHIP_SIZE, CHIP_SIZE, null);
        
        // Vẽ số tiền cược
	    g2.setColor(Color.WHITE);
	    g2.setFont(new Font("Arial", Font.BOLD, 18));
	    String betAmount = Integer.toString(player.getScore());
	    int betWidth = g2.getFontMetrics().stringWidth(betAmount);
	    g2.drawString(betAmount, x + CHIP_SIZE/2 - betWidth/2, y + CHIP_SIZE/2 + 6);
	}
	
	public JFrame getFrame() {
		return frame;
	}
}
