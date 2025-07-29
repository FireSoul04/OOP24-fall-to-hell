package it.unibo.falltohell.model.api.listener;

import it.unibo.falltohell.model.api.gameobject.movable.FamiliarBat;

/**
 * Functional interface for handling the end of a familiar's attack.
 * <p>
 * This listener is triggered when a {@link FamiliarBat} finishes its attack,
 * allowing deferred cleanup or other follow-up logic (e.g., conditional
 * removal).
 * </p>
 *
 * @author Sara Visani
 * @see FamiliarBat
 */
public interface AttackFinishListener {

    /**
     * Called when the attack of the given familiar is complete.
     *
     * @param familiar the familiar whose attack just ended
     */
    void onAttackFinished(FamiliarBat familiar);
}
