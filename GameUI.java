package ui;

import java.util.ArrayList;
import utilities.Card;
import utilities.ControlGame;

public interface GameUI {
	
	public static final int WIDTH_WINDOW = 1000;
	public static final int HEIGHT_WINDOW = 650;
	public static final int WIDTH_CARD = 89;
	public static final int HEIGHT_CARD = 140;
	public static final int OFFSET_X = WIDTH_CARD/3;
    public static final int OFFSET_Y = HEIGHT_CARD/5;
	
	public void drawCardsVertical(ControlGame control, int x);
	public void drawCardsHorizontal(ControlGame control, int y);
	public void drawChosenCards(ArrayList<Card> chosenCard, ControlGame control);
	public void drawHidingStateVertical(ControlGame control, int x);
	public void drawHidingStateHorizontal(ControlGame control, int y);
}
