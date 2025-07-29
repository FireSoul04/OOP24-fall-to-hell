package it.unibo.falltohell.view.impl;

import javax.swing.*;

import it.unibo.falltohell.controller.impl.ImageControllerImpl;

import java.awt.*;
import java.awt.event.ActionListener;

public class MainMenuPanel extends JPanel {

    private final Image background;

    public MainMenuPanel(final ActionListener startListener, final ActionListener exitListener) {
        this.setLayout(new GridBagLayout());

        
        this.background = new ImageControllerImpl().loadImage("background.png");

        final JButton startButton = new JButton("Start Game");
        final JButton exitButton = new JButton("Exit");

        
        Color buttonBackground = new Color(30, 30, 30);
        Color buttonText = Color.WHITE;
        Font buttonFont = new Font("Arial", Font.BOLD, 18);
        JLabel titleLabel = new JLabel("Fall To Hell");
        

        for (JButton b : new JButton[]{startButton, exitButton}) {
            
            b.setForeground(buttonText);
            b.setFont(buttonFont);
            b.setFocusPainted(false);
            b.setBackground(buttonBackground);
            b.setOpaque(true);
        }

        startButton.addActionListener(startListener);
        exitButton.addActionListener(exitListener);

        titleLabel.setForeground(Color.RED);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 48));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        final GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        gbc.gridy = 0;
        this.add(titleLabel, gbc);
        gbc.gridy = 1;
        this.add(startButton, gbc);
        gbc.gridy = 2;
        this.add(exitButton, gbc);

        this.setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(background,0, 0,getWidth(),getHeight(), null);
    }
}
