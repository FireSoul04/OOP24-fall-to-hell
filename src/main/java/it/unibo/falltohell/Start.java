package it.unibo.falltohell;

import it.unibo.falltohell.controller.impl.GameControllerImpl;
/**
 * Class for starting the application.
 */
final class Start {
    private Start() {

    }
    /**
     * Function called at the start of the application.
     * @param args unused
     */
    public static void main(final String[] args) {
        new GameControllerImpl();
    }
}
