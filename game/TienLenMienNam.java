package game;

import rule.TLMNRule;
import ui.GameUI;
import utilities.player.Label;
import utilities.player.State;

public class TienLenMienNam extends TienLen implements Runnable{
	
	private Thread thread;

	public TienLenMienNam(int mode, int numHuman, int numBots) {
		super(mode, numHuman, numBots);
		GameUI.frame.setTitle("Tien len mien nam");
		rule = new TLMNRule();
		this.addMouseListener(handleInput());
		thread = new Thread(this);
	}
	
	@Override
	public void run() {
		// The main game loop
		while(thread != null) {
			repaint();
			// Only handle bot actions in this thread
			if(getPlayer().getLabel() == Label.BOT) {
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				botCards = bot.engineCard(getPlayer().getCards(), previousCard);
				if(botCards == null) {
					getPlayer().setState(State.PASS);
					increaseNumPass();
				}
				else {
					previousCard = botCards;
				}
				repaint();
				nextTurn();
			} 
			
			// Add a small sleep to prevent high CPU usage
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	@Override
	public void gameStart() {
		thread.start();
	}
}
