package it.unibo.falltohell;

import it.unibo.falltohell.controller.api.GameController;
import it.unibo.falltohell.controller.impl.GameControllerImpl;
import it.unibo.falltohell.view.impl.MainMenuPanel;
/**
 * Class for starting the application.
 */
public final class Start {
    /**
     * Function called at the start of the application.
     * @param args unused
     */
    public static void main(final String[] args) {
        final GameController fallToHell = new GameControllerImpl();
        final MainMenuPanel menu = new MainMenuPanel(
            e -> {
                fallToHell.getView().showGame();
                fallToHell.getView().requestFocusOnWindow();
                new Thread(fallToHell :: run).start();
                }, 
            e -> {
                System.exit(0);
            });
        fallToHell.getView().showMenu(menu);
    }
}
