package bot;

import rule.BaCayRule;
import utilities.player.Player;

public class BaCayBot {
	
	private BaCayRule rule = new BaCayRule();
	
	public int evaluateScore(Player p) {
		int score = rule.calculateValue(p);
		score = 10*(score/2);
		if(score == 0) {
			score = 10;
		}
		return score;
	}
}
