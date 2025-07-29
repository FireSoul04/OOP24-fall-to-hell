package it.unibo.falltohell.view.impl;

import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

import it.unibo.falltohell.view.api.AudioPlayer;
/**
 * A class that consent to manipulate the file Audio for the game.
 */
public class SoundPlayerView implements AudioPlayer{
    private final int loop;
    private final String filePath;
    private long currentFrame = 0;
    private AudioInputStream audioInputStream;
    private Clip clip;
    /**
     * Constructor for the SoundPlayer.
     * @param name the name of the audio file
     * @param loop the number of times to loop the audio 
     */
    public SoundPlayerView(final String name, final int loop) {
        this.filePath = AudioPlayer.PATH_TO_AUDIO + name;
        this.loop = loop;
        this.resetAudio();
    }
    /**
     * Resets the audio player by reloading the audio file.
     */
     private void resetAudio() {
        try {
            this.audioInputStream = AudioSystem.getAudioInputStream(new File(this.filePath).getAbsoluteFile());
            this.clip = AudioSystem.getClip();
            this.clip.open(this.audioInputStream);
            // FloatControl gainControl = (FloatControl) this.clip.getControl(FloatControl.Type.MASTER_GAIN);
            // gainControl.setValue(-10.0f);
        } catch (Exception e) {
            System.exit(0);
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
