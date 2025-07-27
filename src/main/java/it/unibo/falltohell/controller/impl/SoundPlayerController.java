package it.unibo.falltohell.controller.impl;

import it.unibo.falltohell.util.SoundPlayer;
import it.unibo.falltohell.view.impl.SoundPlayerView;

public class SoundPlayerController {
    private final SoundPlayerView view;
    private final SoundPlayer player;

    public SoundPlayerController(final SoundPlayerView view, final SoundPlayer player) {
        this.view = view;
        this.player = player;
    }

    public void play() {
        this.player.play();
    }

    public void onPauseGame() {
        this.player.pause();
    }

    public void onResumeGame() {
        this.player.resume();
    }

    public void onStopGame() {
        this.player.stop();
    }

    public void reset() {
        this.player.reset();
    }
}
