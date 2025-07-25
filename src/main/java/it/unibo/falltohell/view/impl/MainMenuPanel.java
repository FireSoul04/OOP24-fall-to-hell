package it.unibo.falltohell.view.impl;

import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
/**
 * Panel representing the main menu of the game.
 * <p>
 * Displays three buttons: "Start Game", "Settings", and "Exit".
 * Each button triggers the corresponding action provided via {@link ActionListener}.
 * The layout uses {@link GridBagLayout} to arrange the buttons vertically with spacing.
 * </p>
 * @author Casadei Lorenzo
 */
public class MainMenuPanel extends JPanel{
    /**
     * Constructs the main menu panel with the specified action listeners for each button.
     *
     * @param startListener    the action to perform when the "Start Game" button is pressed
     * @param settingsListener the action to perform when the "Settings" button is pressed
     * @param exitListener     the action to perform when the "Exit" button is pressed
     */
    public MainMenuPanel(ActionListener startListener, ActionListener settingsListener, ActionListener exitListener) {
        this.setLayout(new GridBagLayout());
        JButton startButton = new JButton("Start Game");
        JButton settingsButton = new JButton("Settings");
        JButton exitButton = new JButton("Exit");

        startButton.addActionListener(startListener);
        settingsButton.addActionListener(settingsListener);
        exitButton.addActionListener(exitListener);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        gbc.gridy = 0;
        this.add(startButton, gbc);
        gbc.gridy = 1;
        this.add(settingsButton, gbc);
        gbc.gridy = 2;
        this.add(exitButton, gbc);
    }
}
