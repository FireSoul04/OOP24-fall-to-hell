package it.unibo.falltohell.util;

import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

import it.unibo.falltohell.model.api.AudioPlayer;
/**
 * A class that implements the AudioPlayer interface to handle sound playback.
 * It uses Java's Clip and AudioInputStream for audio management.
 */
public class SoundPlayer implements AudioPlayer{
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
    public SoundPlayer(final String name, final int loop) {
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
            FloatControl gainControl = (FloatControl) this.clip.getControl(FloatControl.Type.MASTER_GAIN);
            gainControl.setValue(-10.0f);
        } catch (Exception e) {
            System.exit(0);
        }
    }
    /**
     * Plays the audio once or with loop if configured.
     */
    @Override
    public void playOnce() {
        this.clip.loop(this.loop);
    }
    /**
     * Stops the audio playback.
     */
    @Override
    public void stop() {
        this.currentFrame = 0;
        this.clip.stop();
        this.clip.close();
    }
    /**
     * Pauses the audio playback.
     */
    @Override
    public void pause() {
        this.currentFrame = this.clip.getMicrosecondPosition();
        this.clip.stop();
    }
    /**
     * Resumes the audio playback from the last paused position.
     */
    @Override
    public void resume() {
        this.clip.close();
        this.reset();
        this.clip.setMicrosecondPosition(this.currentFrame);
        this.playOnce();
    }
    /**
     * Plays the audio.
     */
    @Override
    public void play() {
        this.reset();
        this.playOnce();
    }
    /**
     * Resets the audio player to the beginning of the audio file.
     */
    @Override
    public void reset() {
        this.stop();
        this.clip.setMicrosecondPosition(0);
        this.resetAudio();
    }
    /**
     * Checks if the audio is currently playing.
     * @return true if the audio is playing, false otherwise
     */
    @Override
    public boolean alreadyPlaying() {
        return this.currentFrame > 0;
    }

}
