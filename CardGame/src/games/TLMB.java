package games;

import java.util.*;

import Player.*;
import cards.Card;
import cards.Rank;
import cards.Suit;
import rules.*;

public class TLMB extends Game{

	public TLMB(List<Player> players) {
		super(players);
		rule = new TLMBRule();
		this.initGame();
		this.handleGame();
	}
	
	@Override
	public void initGame() {
		System.out.println(this.deck.toString());
		dealCard(4);
		chooseStartPlayer();
		showGame();
	}
	
	public void dealCard(int num) {
		for (int i = 0; i < num; ++i) {
			for (Player p : this.players) {
				p.drawCard(deck);
			}
		}
	}

	public Player chooseStartPlayer() {
		//default:
		resetRound(players.get(0));
		return players.get(0);
	}
	
	public void showGame() {
		for (Player p : this.players) {
			//p.drawCard(this.deck);
			p.showHand();
		}
	}
	
	@Override
	public void handleGame() {
	    boolean isFirstTurn = true;

	    while (true) {
	        int notWin = 0;
	        int notPass = 0;
	        Player activePlayer = null;

	        for (Player p : turnQueue) {
	            if (p.getState() != State.WIN) {
	                notWin++;
	                if (p.getState() == State.PLAY) {
	                    notPass++;
	                    if (activePlayer == null) activePlayer = p;
	                }
	            }
	        }

	        if (notWin <= 1) {
	            System.out.println("Game Over!");
	            break;
	        }

	        if (notPass <= 1) {
	        	resetState();
	            resetRound(activePlayer);
	            isFirstTurn = true;
	            continue;
	        }

	        Player curr = turnQueue.poll();
	        if (curr.getState() == State.PLAY) {
	            prevCard = curr.playCard(rule, isFirstTurn, prevCard);
	            isFirstTurn = false;

	            if (curr.checkWin()) {
	                curr.setState(State.WIN);
	                System.out.println("Player " + curr.getName() + " wins!");
	                activePlayer = turnQueue.peek();
	                resetState();
		            resetRound(activePlayer);
	            }
	        }

	        if (curr.getState() != State.WIN) {
	        	turnQueue.offer(curr);
	        }
	    }
	}
	
	public void resetRound(Player first) {
	    Queue<Player> newQueue = new LinkedList<>();
	    prevCard = new Card(Rank.ZERO, Suit.ZERO);

	    int startIndex = -1;
	    int size = players.size();

	    for (int i = 0; i < size; i++) {
	        if (players.get(i) == first) {
	            startIndex = i;
	            break;
	        }
	    }

	    for (int i = 0; i < size; i++) {
	        Player current = players.get((startIndex + i) % size);
	        if (current.getState() == State.PLAY) {
	            newQueue.offer(current);
	        }
	    }

	    turnQueue = newQueue;
	    System.out.println("Reset all players to PLAY state. Start new turn!");
	}

	public void resetState() {
		for (Player p : turnQueue) {
	        if (p.getState() != State.WIN) {
	            p.setState(State.PLAY);
	        }
	    }
	}


	
	
}
