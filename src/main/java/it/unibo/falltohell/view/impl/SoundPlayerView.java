package it.unibo.falltohell.view.impl;

import it.unibo.falltohell.model.api.AudioPlayer;

public class SoundPlayerView {
    private final AudioPlayer player;
    /**
     * Constructor for the SoundPlayerView.
     * @param player the audio player to be controlled by this view
     */
    public SoundPlayerView(final AudioPlayer player) {
        this.player = player;
    }

    /**
     * Plays the sound once or with loop if configured.
     */
    public void play() {
        player.play();
    }

    /**
     * Stops the sound.
     */
    public void stop() {
        player.stop();
    }
    /**
     * pause the sound.
     */
    public void pause() {
        player.pause();
    }
    /**
     * resume the sound.
     */
    public void resume() {
        player.resume();
    }
    /**
     * Resets the sound player.
     */
    public void reset() {
        player.reset();
    }
}
