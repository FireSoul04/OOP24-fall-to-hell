package it.unibo.falltohell.controller.impl;

import it.unibo.falltohell.controller.api.AudioController;
import it.unibo.falltohell.view.impl.AudioManager;
/**
 * A simple class that provides a controller for the AudioManager.
 */
public class AudioControllerImpl implements AudioController{
    private final AudioManager audioManager;
    /**
     * The constructor for the AudioController, it takes the single instance of the AudioManager.
     */
    public AudioControllerImpl() {
        this.audioManager = AudioManager.getInstance();
    }

    /**
     * {@inheritDoc}
     */
    public void play(final String name) {
        this.audioManager.play(name);
    }

    /**
     * {@inheritDoc}
     */
    public void pause(final String name) {
        this.audioManager.stop(name);
    }

    /**
     * {@inheritDoc}
     */
    public void mute() {
        this.audioManager.mute();
    }

    /**
     * {@inheritDoc}
     */
    public void unmute() {
        this.audioManager.unmute();
    }
}
