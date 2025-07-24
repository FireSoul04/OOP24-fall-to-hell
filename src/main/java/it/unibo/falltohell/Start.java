package it.unibo.falltohell;

import it.unibo.falltohell.controller.api.GameController;
import it.unibo.falltohell.controller.impl.GameControllerImpl;

public class Start {
    public static void main(final String[] args) {
        final GameController fallToHell = new GameControllerImpl();
        fallToHell.run();
    }
}
