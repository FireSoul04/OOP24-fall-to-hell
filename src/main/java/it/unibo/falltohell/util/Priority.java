package it.unibo.falltohell.util;

/**
 * Priority of the drawable object when it must be rendered.
 */
public enum Priority {

    /**
     * Is the priority given to GUI elements.
     */
    GUI,
    /**
     * Is the priority given to blocks.
     */
    HIGH,
    /**
     * Is the priority given to enemies, to weapons and to projectiles.
     */
    MEDIUM,
    /**
     * Is the priority given to the character.
     */
    LOW,
    /**
     * Is the priority given to the save point, to the character changer,
     * to items and to drops.
     */
    VERY_LOW,
    /**
     * Is the priority given to the background.
     */
    BACKGROUND
}
