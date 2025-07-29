package it.unibo.falltohell;

import it.unibo.falltohell.controller.api.GameController;
import it.unibo.falltohell.controller.impl.GameControllerImpl;
import it.unibo.falltohell.view.impl.MainMenuPanel;

public class Start {
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
