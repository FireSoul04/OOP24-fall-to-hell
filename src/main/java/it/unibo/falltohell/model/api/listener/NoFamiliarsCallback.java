package it.unibo.falltohell.model.api.listener;

/**
 * Callback interface to notify when there are no more familiars left.
 *
 * Used by ManagerFamiliars to inform interested classes (e.g., Druid)
 * that all familiars have been removed or are inactive.
 *
 * @author Sara Visani
 */
public interface NoFamiliarsCallback {

    /**
     * Called when there are no familiars remaining.
     */
    void onNoFamiliarsLeft();
}
