package ui;

import java.awt.Graphics2D;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

import game.TienLenMienNam;
import utilities.ControlGame;
import utilities.card.Card;

public abstract class GameUI extends JPanel {
	
	public static final int WIDTH_WINDOW = 1000;
	public static final int HEIGHT_WINDOW = 650;
    public static final int WIDTH_CARD = 89;
	public static final int HEIGHT_CARD = 140;
	public static final int OFFSET_X = WIDTH_CARD/3;
    public static final int OFFSET_Y = HEIGHT_CARD/5;
    public static JFrame frame = new JFrame();
	public static Graphics2D g2;
	
    public abstract void drawBackground(ControlGame control);
	public abstract void drawCardsVertical(ControlGame control, int x);
	public abstract void drawCardsHorizontal(ControlGame control, int y);
	public abstract void drawChosenCards(ArrayList<Card> chosenCard, ControlGame control);
	public abstract void drawHidingStateVertical(ControlGame control, int x);
	public abstract void drawHidingStateHorizontal(ControlGame control, int y);
	public abstract void drawHidingState(ControlGame control, int i, int j);
	public abstract void drawStatePlaying(ControlGame control, int i, int j);
}
