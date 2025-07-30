package it.unibo.falltohell.view.api;

public interface AudioManager {

    /**
     * play the sound.
     * @param name the name of the sound to be played.
     */
    void play(final String name);
    /**
     * stop the sound.
     * @param name the name of the sound to be stopped.
     */
    void stop(final String name);
    /**
     * pause all the sounds.
     */
    void pauseAll();

    /**
     * pause all the sounds.
     */
    void resumeAll();

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
