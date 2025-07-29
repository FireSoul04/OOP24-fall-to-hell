package it.unibo.falltohell.controller.impl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * InputListener class to handle keyboard input in the game.
 * It tracks which keys are pressed and allows checking if a key is currently
 * pressed
 * or has been pressed once.
 * 
 * @author Casadei Lorenzo
 */
public class InputListener {

    private static final int MAX_KEY = 256;
    private final Map<Integer, Key> keys = new HashMap<>();
    private final Set<Key> keysPressed = new HashSet<>();
    private final KeyListener keyListener = new KeyAdapter() {
        @Override
        public void keyPressed(final KeyEvent e) {
            keysPressed.add(keys.get(e.getKeyCode()));
        }

        @Override
        public void keyReleased(final KeyEvent e) {
            keysPressed.remove(keys.get(e.getKeyCode()));
        }
    };

    /**
     * Create a controller for key pressed.
     */
    public InputListener() {
        for (int i = 0; i < MAX_KEY; i++) {
            this.keys.put(i, new Key(i));
        }
    }

    /**
     * Checks if a key is currently pressed.
     * 
     * @param keyCode the key code.
     * @return true if pressed, false otherwise
     */
    public boolean isKeyPressed(final int keyCode) {
        return this.keysPressed.contains(this.keys.get(keyCode));
    }

    /**
     * Checks if a key has been pressed once (not held down).
     * 
     * @param keyCode the key code
     * @return true if pressed once, false otherwise
     */
    public boolean isKeyPressedOnce(final int keyCode) {
        final Key key = this.keys.get(keyCode);
        boolean keyPressed = false;
        if (!key.isAlreadyPressed() && this.isKeyPressed(keyCode)) {
            key.setAlreadyPressed(true);
            keyPressed = true;
        } else if (!this.isKeyPressed(keyCode)) {
            key.setAlreadyPressed(false);
        }
        return keyPressed;
    }

    /**
     * Returns the KeyListener to be added to the view component.
     * 
     * @return the KeyListener
     */
    public KeyListener getKeyListener() {
        return this.keyListener;
    }

    private static final class Key {
        private final int keyCode;
        private boolean alreadyPressed;

        private Key(final int keyCode) {
            this.keyCode = keyCode;
        }

        public void setAlreadyPressed(final boolean alreadyPressed) {
            this.alreadyPressed = alreadyPressed;
        }

        public boolean isAlreadyPressed() {
            return this.alreadyPressed;
        }

        @Override
        public int hashCode() {
            return this.keyCode;
        }

        @Override
        public boolean equals(final Object obj) {
            return obj instanceof Key other && this.keyCode == other.keyCode;
        }

    }
}
