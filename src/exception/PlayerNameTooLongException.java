package exception;

import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class PlayerNameTooLongException extends Exception {
	
	public PlayerNameTooLongException(int maxLength) {
		super("Player name cannot exceed " + maxLength + " characters.");
	}
	
	public void handle(JTextField name, int maxLength) {
		 JOptionPane.showMessageDialog(
				 null,
				 "Player name cannot exceed " + maxLength + " characters.",
		         "Invalid Name",
		         JOptionPane.WARNING_MESSAGE
		 );
		 name.setText(name.getText().substring(0, maxLength));
	}
}
