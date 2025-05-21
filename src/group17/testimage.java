package group17;

import java.util.ArrayList;

import game.BaCay;
import game.TienLen;
import game.TienLenMienNam;

public class testimage {
	public static void main(String[] args) {
		// Start the application with main menu screen
		ArrayList<String> name = new ArrayList<String>();
		name.add("Vu");
		name.add("Huy");
		name.add("Dung");
		name.add("Viet");
    	TienLenMienNam game = new TienLenMienNam(0, 4, 0, name);
    	game.gameStart();
	}
}