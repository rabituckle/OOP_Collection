package ui.menu;

import javax.swing.JFrame;

public abstract class MenuScreen {
	
	protected JFrame frame;
	protected GameConfiguration gameConfig;
	protected MenuNavigator navigator;
	
	public MenuScreen(GameConfiguration gameConfig, MenuNavigator navigator) {
		this.gameConfig = gameConfig;
		this.navigator = navigator;
	}
	
	public abstract void show();
	
	public void close() {
		if(frame != null) {
			frame.dispose();
			frame = null;
		}
	}
	
	protected JFrame createFrame(String title, int width, int height) {
		JFrame frame = new JFrame(title);
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    frame.setSize(width, height);
	    frame.setResizable(false);
	    frame.setLocationRelativeTo(null);
	    return frame;
	}
}
