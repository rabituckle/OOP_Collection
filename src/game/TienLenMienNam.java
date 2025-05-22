package game;

import java.util.ArrayList;

import bot.TienLenBot;
import rule.TLMNRule;

public class TienLenMienNam extends TienLen implements Runnable{

	public TienLenMienNam(int mode, int numHuman, int numBots, ArrayList<String> name) {
		super(mode, numHuman, numBots, name);
		ui.getFrame().setTitle("TIEN LEN MIEN NAM");
		rule = new TLMNRule();
		this.addMouseListener(handleInput());
		thread = new Thread(this);
		bot = new TienLenBot(new TLMNRule());
	}
	
	@Override
	public void run() {
		while(thread != null) {
			execution();
		}
	}
	
	@Override
	public void gameStart() {
		thread.start();
	}
}
