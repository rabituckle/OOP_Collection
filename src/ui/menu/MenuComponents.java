package ui.menu;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.RoundRectangle2D;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class MenuComponents {
	
	public static JPanel createPurpleGradientPanel() {
        JPanel panel = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                // Create purple gradient background
                GradientPaint gradient = new GradientPaint(
                    0, 0, new Color(194, 19, 194), // Light purple
                    0, getHeight(), new Color(70, 27, 147)  // Dark purple
                );
                g2d.setPaint(gradient);
                
                // Draw rounded rectangle for the panel
                RoundRectangle2D roundedRectangle = new RoundRectangle2D.Float(
                    10, 10, getWidth() - 20, getHeight() - 20, 30, 30
                );
                g2d.fill(roundedRectangle);
                
                // Draw border
                g2d.setColor(new Color(160, 90, 200));
                g2d.draw(roundedRectangle);
                
                g2d.dispose();
            }
        };
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        panel.setOpaque(false);
        return panel;
    }
	
	public static JButton createStyledButton(String text, int x, int y) {
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
        button.setPreferredSize(new Dimension(x, y));
        button.setMaximumSize(new Dimension(x, y));
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setAlignmentX(JButton.CENTER_ALIGNMENT);
        
        return button;
    }
	
	public static JLabel createTitleLabel(String text) {
        JLabel titleLabel = new JLabel(text, SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 32));
        titleLabel.setForeground(Color.WHITE);
        return titleLabel;
    }
	
	public static JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setOpaque(false);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));
        return buttonPanel;
    }
	
	public static Component createButtonSpacing() {
        return Box.createRigidArea(new Dimension(0, 15));
    }
}
