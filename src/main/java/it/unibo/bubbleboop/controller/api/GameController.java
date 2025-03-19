package it.unibo.bubbleboop.controller.api;

public interface GameController {
    
    /**
     * Game loop, runs at MAX_UPDATES per seconds and handles the rendering and lets the game work on multiple platforms at the same speed.
     */
    void run();
}
