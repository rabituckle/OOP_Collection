package Player;

import java.util.*;

public class Human extends Player{
	
	public Human(String name) {
		super(name);
	}
	@Override
	public int chooseCard() {
		System.out.print("-Turn of " + this.getName() + ": ");
		int choosen = sc.nextInt();
		return choosen;
	}
	
}
