package it.unibo.falltohell.view.impl;

import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Color;
import java.awt.Image;
import java.awt.GridBagLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Graphics;

import it.unibo.falltohell.controller.impl.ImageControllerImpl;

import java.awt.event.ActionListener;
/**
 * Main menu panel of the game, extends {@code JPanel}, it use {@code GridBagLayout} to create 
 * two {@code JButton} on the center of the screen, it requires two {@code ActionListener}
 * for the buttons.
 * it also display a background and the title of the game.
 */
public class MainMenuPanel extends JPanel {
    private final Image background;

    public MainMenuPanel(final ActionListener startListener, final ActionListener exitListener) {
        this.setLayout(new GridBagLayout());

        this.background = new ImageControllerImpl().loadImage("background.png");

        final JButton startButton = new JButton("Start Game");
        final JButton exitButton = new JButton("Exit");

        final Color buttonBackground = new Color(30, 30, 30);
        final Color buttonText = Color.WHITE;
        final Font buttonFont = new Font("Arial", Font.BOLD, 18);
        final JLabel titleLabel = new JLabel("Fall To Hell");

        for (JButton b : new JButton[]{startButton, exitButton}) {
            b.setForeground(buttonText);
            b.setFont(buttonFont);
            b.setFocusPainted(false);
            b.setBackground(buttonBackground);
            b.setOpaque(true);
        }

        startButton.addActionListener(startListener);
        exitButton.addActionListener(exitListener);

        titleLabel.setForeground(Color.darkGray);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 48));
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
    /**
     * {@inheritDoc}
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(background,0, 0,getWidth(),getHeight(), null);
    }
}
