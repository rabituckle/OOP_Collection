package game;

import java.util.ArrayList;

import BotAI.TLMBBot;
import rule.TLMBRule;
import utilities.player.Label;
import utilities.player.State;

public class TienLenMienBac extends TienLen implements Runnable {

	private Thread thread;
	private TLMBBot bot = new TLMBBot();
	
	public TienLenMienBac(int mode, int numHuman, int numBots, ArrayList<String> name) {
		super(mode, numHuman, numBots, name);
		ui.getFrame().setTitle("TIEN LEN MIEN BAC");
		rule = new TLMBRule();
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
