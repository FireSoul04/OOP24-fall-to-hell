package it.unibo.falltohell.view.impl;

import java.util.HashMap;
import java.util.Map;

public class AudioManager {
    private static final AudioManager INSTANCE = new AudioManager();
    private final Map<String, SoundPlayerView> soundMap = new HashMap<>();
    private boolean muted = false;

    private AudioManager() {
        this.loadSounds();
    }

    public static AudioManager getInstance() {
        return INSTANCE;
    }

    private void loadSounds() {
        
        this.soundMap.put("Music", new SoundPlayerView("the-darkness-of-eternity.wav", 15));
        
    }

    public void play(String name) {
        if (!muted && soundMap.containsKey(name)) {
            soundMap.get(name).play();
        }
    }

    public void loop(String name) {
        if (!muted && soundMap.containsKey(name)) {
            soundMap.get(name).playInLoop();
        }
    }

    public void stop(String name) {
        if (soundMap.containsKey(name)) {
            soundMap.get(name).stop();
        }
    }

    public void pauseAll() {
        if(muted){
            soundMap.values().forEach(SoundPlayerView::pause);
        }
    }

    public void resumeAll() {
        if (!muted) {
            soundMap.values().forEach(SoundPlayerView::resume);
        }
    }

    public void mute() {
        muted = true;
        pauseAll();
    }

    public void unmute() {
        muted = false;
        resumeAll();
    }

    public boolean isMuted() {
        return muted;
    }
}
