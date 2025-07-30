package it.unibo.falltohell.test.util.debug;

import it.unibo.falltohell.test.util.debug.druid.FamiliarBatDebug;

/**
 * Debug listener used for the tests.
 * @author Sara Visani
 */
public interface AttackFinishListenerDebug {

    /**
     * Called when the attack of the given familiar is complete.
     *
     * @param familiar the familiar whose attack just ended
     */
    void onAttackFinished(FamiliarBatDebug familiar);
}
