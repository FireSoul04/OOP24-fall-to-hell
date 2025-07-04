package it.unibo.falltohell.model.impl;

import it.unibo.falltohell.model.api.Drawable;

/**
 * Class that represents the sprite associated to a specific drawable object and
 * that handles information about its rendering.
 * @author Martina Malagoli
 */
public class Sprite implements Drawable {

    private boolean mirrored;
    private boolean visible;

    /**
     * Default initialization of the Sprite class without parameters.
     */
    public Sprite() {
        this.mirrored = false;
        this.visible = true;
    }

    /**
     * Initialization of the Sprite class with customized mirroring information.
     * @param mirroring tells if the Sprite should be initialized mirrored or not
     */
    public Sprite(final boolean mirroring) {
        this.mirrored = mirroring;
        this.visible = true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void mirror(final boolean mirroring) {
        this.mirrored = mirroring;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isMirrored() {
        return this.mirrored;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setVisible(final boolean visibility) {
        this.visible = visibility;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isVisible() {
        return this.visible;
    }
}
