package ui.menus;

import javax.swing.*;

import utilities.MainMenu;

import java.awt.event.ActionEvent;

public class RegisterMenuScreen extends MenuScreen {

    public void show() {
        JPanel mainPanel = createBasePanel("Register Menu");
        JPanel buttonPanel = createButtonPanel();

        JLabel usernameLabel = new JLabel("Username:");
        JTextField usernameField = new JTextField(20);
        JLabel passwordLabel = new JLabel("Password:");
        JPasswordField passwordField = new JPasswordField(20);
        JLabel confirmPasswordLabel = new JLabel("Confirm Password:");
        JPasswordField confirmPasswordField = new JPasswordField(20);

        mainPanel.add(usernameLabel);
        mainPanel.add(usernameField);
        mainPanel.add(passwordLabel);
        mainPanel.add(passwordField);
        mainPanel.add(confirmPasswordLabel);
        mainPanel.add(confirmPasswordField);

        addButtonWithAction(buttonPanel, "Registor", e -> {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());
            String confirmPassword = new String(confirmPasswordField.getPassword());

            // Kiểm tra thông tin đăng ký
            if (password.equals(confirmPassword)) {
                if (registerNewUser(username, password)) {
                    mainFrame.dispose();
                    JOptionPane.showMessageDialog(mainFrame, "Registration successful! Please log in.", "Success", JOptionPane.INFORMATION_MESSAGE);
                    new MainMenuScreen().show();
                } else {
                    JOptionPane.showMessageDialog(mainFrame, "Username already exists.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(mainFrame, "Passwords do not match.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        addButtonWithAction(buttonPanel, "BACK", e -> {
            mainFrame.dispose();
            new MainMenuScreen().show();
        });

        addButtonWithAction(buttonPanel, "EXIT", e -> System.exit(0));

        setupAndShowFrame(mainPanel, buttonPanel, 400, 350);
    }

    // Đăng ký người dùng mới
    private boolean registerNewUser(String username, String password) {
        // Placeholder cho đăng ký người dùng
        // Bạn có thể thay thế bằng logic lưu username, password vào file hoặc database
        // Kiểm tra nếu username đã tồn tại
        return !username.equals("admin"); // Ví dụ: tránh đăng ký với "admin"
    }
}
