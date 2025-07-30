package it.unibo.falltohell.controller.api;
/**
 * An interface for the AudioControllerImpl class.
 */
public interface AudioController {
    /**
     * play the sound with the given name.
     * @param name of the audio.
     */
    void play(String name);
    /**
     * pause the sound with the given name.
     * @param name of the audio.
     */
    void pause(String name);
    /**
     * mute all the sound of the game.
     */
    void mute();
    /**
     * unmute all the sounds in the game.
     */
    void unmute();
}
