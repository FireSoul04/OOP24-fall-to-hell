package it.unibo.bubbleboop.view.impl;

import javax.swing.JFrame;

import it.unibo.bubbleboop.view.GameWindow;

public class GameWindowImpl implements GameWindow {

    private JFrame mainFrame;

    private int width;
    private int height;

    public GameWindowImpl(final int width, final int height) {
        this.width = width;
        this.height = height;
        this.init();
    }

    private void init() {
        this.mainFrame = new JFrame("Bubble Boop");
        this.mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.mainFrame.setSize(width, height);
        this.mainFrame.setVisible(true);
        this.mainFrame.pack();
    }

    public void render() {

    }

    public void clear() {
        
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
