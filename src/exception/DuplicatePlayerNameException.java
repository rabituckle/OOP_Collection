package exception;

import javax.swing.JOptionPane;
import javax.swing.JTextField;

import ui.menu.PlayerNameScreen;

public class DuplicatePlayerNameException extends Exception {
	
    public DuplicatePlayerNameException() {
        super("Player names must be unique!");
    }
    
    public void handle(JTextField name, int j) {
        // Make the name unique by appending a number
    	try{
            String newName = name.getText() + "_" + j;
            if(newName.length() > PlayerNameScreen.MAX_LEN_NAME) {
            	throw new PlayerNameTooLongException(PlayerNameScreen.MAX_LEN_NAME);
            }
            JOptionPane.showMessageDialog(
            		null,
                    "Player names must be unique!",
                    "Invalid Name",
                    JOptionPane.WARNING_MESSAGE
            );
            name.setText(newName);
            	
    	} catch(PlayerNameTooLongException e) {
            JOptionPane.showMessageDialog(
            		null,
       				"Please re-enter player " + j + "'s name! ",
       		        "Invalid Name",
       		        JOptionPane.WARNING_MESSAGE
            );
            name.setText("Player " + j);
    	}
    }
}