package game;

import java.util.ArrayList;

import bot.TienLenBot;
import rule.TLMBRule;

public class TienLenMienBac extends TienLen implements Runnable {
	
	public TienLenMienBac(int mode, int numHuman, int numBots, ArrayList<String> name) {
		super(mode, numHuman, numBots, name);
		ui.getFrame().setTitle("TIEN LEN MIEN BAC");
		rule = new TLMBRule();
		this.addMouseListener(handleInput());
		thread = new Thread(this);
		bot = new TienLenBot(new TLMBRule());
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
