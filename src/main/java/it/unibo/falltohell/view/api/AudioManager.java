package it.unibo.falltohell.view.api;
/**
 * An interface for the AudioManager. Consent to manipulate
 * the sounds of this application.
 * @author Casadei Lorenzo
 */
public interface AudioManager {
    /**
     * play the sound.
     * @param name the name of the sound to be played.
     */
    void play(String name);
    /**
     * stop the sound.
     * @param name the name of the sound to be stopped.
     */
    void stop(String name);

    /**
     * mute all the sounds.
     */
    void mute();

    /**
     * unmute all the sounds.
     */
    void unmute();

    /**
     * tell if the sound is muted.
     * @return {@code true} if all the sound are muted,
     * {@code false} otherwise.
     */
    boolean isMuted();

}
