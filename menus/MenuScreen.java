package ui.menus;

import javax.swing.*;

import enums.ModeGUI;
import enums.TypeGame;
import utilities.data.HumanDatabase;
import utilities.player.Bot;
import utilities.player.Human;
import utilities.player.Player;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.geom.RoundRectangle2D;
import java.util.ArrayList;

public abstract class MenuScreen {
	protected HumanDatabase db;
    protected JFrame mainFrame;
    protected static ModeGUI modeGUI;
    protected static TypeGame typeGame;
    protected static ArrayList<Human> humans = new ArrayList<>();
    protected static ArrayList<Player> players = new ArrayList<>();
    protected static ArrayList<Bot> bots = new ArrayList<>();
    protected static int minBots, maxBots, numHumans;

    protected static final int MAX_PLAYERS = 4;
    private static final int BUTTON_WIDTH = 250;
    private static final int BUTTON_HEIGHT = 50;
    protected static final int SPACING = 15;

    private JPanel createImageBackgroundPanel() {
        Image finalBackgroundImage = new ImageIcon(getClass().getResource("/assets/images/backgrounds/menu_background.png")).getImage();

        JPanel panel = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (finalBackgroundImage != null) {
                    g.drawImage(finalBackgroundImage, 0, 0, getWidth(), getHeight(), this);
                }

                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                RoundRectangle2D roundedRectangle = new RoundRectangle2D.Float(
                        10, 10, getWidth() - 20, getHeight() - 20, 30, 30
                );
                g2d.setColor(new Color(160, 90, 200, 150));
                g2d.draw(roundedRectangle);

                g2d.dispose();
            }
        };

        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        panel.setOpaque(false);
        return panel;
    }

    protected JButton createStyledButton(String text, int width, int height) {
        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Draw button background - light gray to dark gray gradient
                GradientPaint gradient = new GradientPaint(
                        0, 0, new Color(230, 230, 230, 200),
                        0, getHeight(), new Color(120, 120, 120, 200)
                );
                g2d.setPaint(gradient);

                // Create rounded rectangle for button
                RoundRectangle2D roundedRectangle = new RoundRectangle2D.Float(
                        0, 0, getWidth() - 1, getHeight() - 1, 25, 25
                );
                g2d.fill(roundedRectangle);

                // Draw button border
                g2d.setColor(new Color(150, 150, 150));
                g2d.draw(roundedRectangle);

                // Draw text
                g2d.setColor(new Color(60, 60, 60));
                g2d.setFont(getFont());

                // Center text
                g2d.setFont(new Font("Arial", Font.BOLD, 18));
                g2d.drawString(text,
                        (getWidth() - g2d.getFontMetrics().stringWidth(text)) / 2,
                        (getHeight() + g2d.getFontMetrics().getAscent() - g2d.getFontMetrics().getDescent()) / 2);

                g2d.dispose();
            }
        };

        // Configure button properties
        button.setFont(new Font("Arial", Font.BOLD, 18));
        button.setPreferredSize(new Dimension(width, height));
        button.setMaximumSize(new Dimension(width, height));
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setAlignmentX(JButton.CENTER_ALIGNMENT);

        return button;
    }

    protected JPanel createBasePanel(String title) {
        mainFrame = new JFrame("Card Game - " + title);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create purple gradient background panel
        JPanel mainPanel = createImageBackgroundPanel();

        // Title
        JLabel titleLabel = new JLabel(title.toUpperCase(), SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 32));
        titleLabel.setForeground(Color.WHITE);
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        return mainPanel;
    }

    static JPanel createButtonPanel() {
        // Create button panel with vertical layout
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setOpaque(false);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));
        return buttonPanel;
    }

    protected void addButtonWithAction(JPanel panel, String text, ActionListener action) {
        JButton button = createStyledButton(text, BUTTON_WIDTH, BUTTON_HEIGHT);
        button.addActionListener(action);
        panel.add(button);
        panel.add(Box.createRigidArea(new Dimension(0, SPACING)));
    }

    protected void setupAndShowFrame(JPanel mainPanel, JPanel buttonPanel, int width, int height) {
        mainPanel.add(buttonPanel, BorderLayout.CENTER);
        mainFrame.setContentPane(mainPanel);
        mainFrame.setSize(width, height);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setResizable(false);
        mainFrame.setVisible(true);
    }
    

}
