package it.unibo.falltohell;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import it.unibo.falltohell.controller.api.GameController;
import it.unibo.falltohell.controller.impl.GameControllerImpl;
import it.unibo.falltohell.view.impl.MainMenuPanel;
/**
 * Class for starting the application.
 */
final class Start {
    
    private Start(){

    }
    /**
     * Function called at the start of the application.
     * @param args unused
     */
    @SuppressFBWarnings(
            value = "DM_EXIT",
            justification = "If the exit button is pressed the application must be shut down"
    )
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
