package exception;

import javax.swing.JTextField;

public class EmptyPlayerNameException extends Exception {
	
    public EmptyPlayerNameException() {
        super("Player name cannot be empty.");
    }
    
    public void handle(JTextField name, int playerIndex) {
    	name.setText("Player " + playerIndex);
    }
}