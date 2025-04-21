package games;

import java.util.*;

import Player.*;
import cards.*;
import rules.*;

public abstract class Game {
	protected List<Player> players;
	protected Queue<Player> turnQueue;
	protected Deck deck;
	protected ICardRule rule;
	protected Card prevCard;
	
	public Game(List<Player> players) {
		this.players = players;
		this.deck = new Deck();
		
	}
	
	public abstract void initGame();
	public abstract Player chooseStartPlayer();
	public abstract void dealCard(int num);
	public abstract void handleGame();
	public abstract void resetState();
	public abstract void resetRound(Player lastActivePlayer);

}
