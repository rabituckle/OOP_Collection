package group17;

import java.awt.Color;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Main extends JPanel{
	private Mode mode = new Mode();
	private JFrame window = new JFrame();
	private int width = 700, height = 500;
	
	public Main() {
		window.setSize(width, height);
		window.setTitle("Playing Card");
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setLocationRelativeTo(null);
		window.setResizable(false);
		window.setVisible(true);
	}
	
	public static void main(String[] args) {
		Main frame = new Main();
		frame.window.add(frame.mode);
		frame.mode.game();
	}
}