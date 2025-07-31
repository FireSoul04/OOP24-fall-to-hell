package it.unibo.falltohell.view.impl;

import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

import it.unibo.falltohell.view.api.AudioPlayer;
/**
 * A class that consent to manipulate the file Audio for the game.
 */
public final class SoundPlayerView implements AudioPlayer {
    private static final Logger LOGGER = Logger.getLogger(SoundPlayerView.class.getName());
    private static final float DEFAULT_VOLUME = -10.0f;
    private final int loop;
    private final String filePath;
    private long currentFrame;
    private Clip clip;
    /**
     * Constructor for the SoundPlayer.
     * @param name the name of the audio file
     * @param loop the number of times to loop the audio 
     */
    public SoundPlayerView(final String name, final int loop) {
        this.filePath = PATH_TO_AUDIO + name;
        this.loop = loop;
        this.currentFrame = 0;
        this.resetAudio();
    }
    /**
     * Resets the audio player by reloading the audio file.
     */
     private void resetAudio() {
        try {
            final AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(
                Objects.requireNonNull(this.getClass().getResource(this.filePath))
            );
            this.clip = AudioSystem.getClip();
            this.clip.open(audioInputStream);
            final FloatControl gainControl = (FloatControl) this.clip.getControl(FloatControl.Type.MASTER_GAIN);
            gainControl.setValue(DEFAULT_VOLUME);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            LOGGER.log(Level.WARNING, "An error has occured while trying to open this file: " + this.filePath);
        }
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public void playInLoop() {
        this.clip.loop(this.loop);
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public void stop() {
        this.currentFrame = 0;
        this.clip.stop();
        this.clip.close();
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public void pause() {
        this.currentFrame = this.clip.getMicrosecondPosition();
        this.clip.stop();
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public void resume() {
        this.clip.close();
        this.reset();
        this.clip.setMicrosecondPosition(this.currentFrame);
        this.playInLoop();
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public void play() {
        this.reset();
        this.playInLoop();
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public void reset() {
        this.stop();
        this.clip.setMicrosecondPosition(0);
        this.resetAudio();
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean alreadyPlaying() {
        return this.currentFrame > 0;
    }
}
