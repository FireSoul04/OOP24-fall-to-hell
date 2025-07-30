package it.unibo.falltohell.controller.impl;

import it.unibo.falltohell.controller.api.AudioController;
import it.unibo.falltohell.view.impl.AudioManagerImpl;
/**
 * A simple class that provides a controller for the AudioManager.
 */
public class AudioControllerImpl implements AudioController {
    private final AudioManagerImpl audioManager;
    /**
     * The constructor for the AudioController, it takes the single instance of the AudioManager.
     */
    public AudioControllerImpl() {
        this.audioManager = AudioManagerImpl.getInstance();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void play(final String name) {
        this.audioManager.play(name);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void pause(final String name) {
        this.audioManager.stop(name);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void mute() {
        this.audioManager.mute();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void unmute() {
        this.audioManager.unmute();
    }
}
