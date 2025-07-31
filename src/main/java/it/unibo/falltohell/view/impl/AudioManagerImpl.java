package it.unibo.falltohell.view.impl;

import java.util.HashMap;
import java.util.Map;

import it.unibo.falltohell.view.api.AudioManager;
/**
 * A class that manage the sounds for the app, it use the Singleton pattern to
 * ensure that this class has only one global instance.
 * @author Casadei Lorenzo.
 */
public final class AudioManagerImpl implements AudioManager {
    private static final AudioManagerImpl INSTANCE = new AudioManagerImpl();
    private static final int NUMBER_OF_LOOP = 15;
    private final Map<String, SoundPlayerView> soundMap = new HashMap<>();
    private boolean muted;
    /**
     * Private constructor to prevent external instantiation.
     * Loads the available sounds.
     */
    private AudioManagerImpl() {
        this.loadSounds();
        this.muted = false;
    }

    /**
     * Returns the singleton instance of AudioManager.
     *
     * @return the single instance of AudioManager.
     */
    public static AudioManagerImpl getInstance() {
        return INSTANCE;
    }

    /**
     * Loads all the sounds into the sound map.
     * This method can be extended to load multiple sound effects.
     */
    private void loadSounds() {
        this.soundMap.put("Music", new SoundPlayerView("the-darkness-of-eternity.wav", NUMBER_OF_LOOP));
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public void play(final String name) {
        if (!muted && soundMap.containsKey(name)) {
            soundMap.get(name).play();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void stop(final String name) {
        if (soundMap.containsKey(name)) {
            soundMap.get(name).stop();
        }
    }
    /**
     * pause all the sounds.
     */
    private void pauseAll() {
        if (isMuted()) {
            soundMap.values().forEach(SoundPlayerView::pause);
        }
    }
    /**
     * resume all the sounds.
     */
    private void resumeAll() {
        if (!isMuted()) {
            soundMap.values().forEach(SoundPlayerView::resume);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void mute() {
        muted = true;
        pauseAll();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void unmute() {
        muted = false;
        resumeAll();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isMuted() {
        return muted;
    }
}
