package it.unibo.falltohell.view.api;
/**
 * Interface for manipulate a track audio.
 */
public interface AudioPlayer {
    /**
     * the path to the sounds directory.
     */
    String PATH_TO_AUDIO = "/sounds/";
    /**
     * Plays the audio once or with loop if configured.
     */
    void playInLoop();
    /**
     * Stops the audio playback.
     */
    void stop();
    /**
     * Pauses the audio playback.
     */
    void pause();
    /**
     * Resumes the audio playback from the last paused position.
     */
    void resume();
    /**
     * Plays the audio.
     */
    void play();
    /**
     * Resets the audio player to the beginning of the audio file.
     */
    void reset();
    /**
     * Checks if the audio is currently playing.
     * @return true if the audio is playing, false otherwise
     */
    boolean alreadyPlaying();
}
