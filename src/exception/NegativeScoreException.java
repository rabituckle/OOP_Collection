package exception;

import utilities.player.Player;

public class NegativeScoreException extends Exception {
	
	private Player player;
	private int score;
	
	public NegativeScoreException(Player player, int Score) {
		this.player = player;
		this.score = score;
	}
	
	public void handleTienLen() {
		player.setScore(0);
	}
	
	public void handleBaCay() {
		player.setScore(0);
	}
}
