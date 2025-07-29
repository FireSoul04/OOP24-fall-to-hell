package it.unibo.falltohell.view.impl;

import java.util.HashMap;
import java.util.Map;
/**
 * A class that manage the sounds for the app, it use the Singleton pattern to
 * ensure that this class has only one global instance.
 */
public class AudioManager {
    private static final AudioManager INSTANCE = new AudioManager();
    private final Map<String, SoundPlayerView> soundMap = new HashMap<>();
    private boolean muted = false;

    /**
     * Private constructor to prevent external instantiation.
     * Loads the available sounds.
     */
    private AudioManager() {
        this.loadSounds();
    }

    /**
     * Returns the singleton instance of AudioManager.
     *
     * @return the single instance of AudioManager
     */
    public static AudioManager getInstance() {
        return INSTANCE;
    }
    /**
     * Loads all the sounds into the sound map.
     * This method can be extended to load multiple sound effects.
     */
    private void loadSounds() {
        this.soundMap.put("Music", new SoundPlayerView("beep-boop.wav", 15));
    }
    /**
     * play the sound.
     * @param name the name of the sound to be played.
     */
    public void play(String name) {
        if (!muted && soundMap.containsKey(name)) {
            soundMap.get(name).play();
        }
    }
    /**
     * stop the sound.
     * @param name the name of the sound to be stopped.
     */
    public void stop(String name) {
        if (soundMap.containsKey(name)) {
            soundMap.get(name).stop();
        }
    }
    /**
     * pause all the sounds.
     */
    public void pauseAll() {
        if(muted){
            soundMap.values().forEach(SoundPlayerView::pause);
        }
    }
    /**
     * pause all the sounds.
     */
    public void resumeAll() {
        if (!muted) {
            soundMap.values().forEach(SoundPlayerView::resume);
        }
    }
    /**
     * mute all the sounds.
     */
    public void mute() {
        muted = true;
        pauseAll();
    }
    /**
     * unmute all the sounds
     */
    public void unmute() {
        muted = false;
        resumeAll();
    }
    /**
     * tell if the sound is muted.
     * @return {@code true} if all the sound are muted,
     * {@code false} otherwise.
     */
    public boolean isMuted() {
        return muted;
    }
}
