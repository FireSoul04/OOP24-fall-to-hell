package it.unibo.falltohell.controller.impl;


import it.unibo.falltohell.view.impl.SoundPlayerView;

public class SoundPlayerController {
    private final SoundPlayerView view;
    

    public SoundPlayerController(final SoundPlayerView view) {
        this.view = view;
    }

    public void play() {
        this.view.play();
    }

    public void onPauseGame() {
        this.view.pause();
    }

    public void onResumeGame() {
        this.view.resume();
    }

    public void onStopGame() {
        this.view.stop();
    }

    public void reset() {
        this.view.reset();
    }
}
