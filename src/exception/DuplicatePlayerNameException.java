package exception;

import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class DuplicatePlayerNameException extends Exception {
	
    public DuplicatePlayerNameException() {
        super("Player names must be unique!");
    }
    
    public void handle(JTextField name, int j) {
    	JOptionPane.showMessageDialog(
                null,
                "Player names must be unique!",
                "Invalid Name",
                JOptionPane.WARNING_MESSAGE
            );
            
            // Make the name unique by appending a number
            name.setText(name.getText() + "_" + j);
    }
}