package it.unibo.falltohell.model.api.manager;

import it.unibo.falltohell.model.api.gameobject.movable.entity.character.Character;
import it.unibo.falltohell.model.api.listener.NoFamiliarsCallback;
import it.unibo.falltohell.model.api.gameobject.movable.FamiliarBat;
import it.unibo.falltohell.util.Vector2;

/**
 * Manages the familiars (e.g., bats) summoned by a character such as a druid.
 * <p>
 * Handles familiar creation, attack delegation, and scheduled removal after a
 * fixed duration or after attacks.
 * Ensures proper cleanup by deferring removal if the familiar is still
 * attacking.
 * </p>
 *
 * @author Sara Visani
 * @see FamiliarBat
 * @see Character
 */
public interface ManagerFamiliars {

    /**
     * Creates a new familiar linked to the specified character.
     * <p>
     * The familiar is given a timer of 5 seconds after which removal is attempted.
     * If it is currently attacking, the removal is deferred until the attack ends.
     * </p>
     *
     * @param character the character summoning the familiar
     */
    void createFamiliar(Character character);

    /**
     * Attempts to remove the familiar from the game.
     * <p>
     * If the familiar is currently idle, it is removed immediately.
     * Otherwise, the removal is deferred and handled after the current attack ends.
     * Notifies callback if no familiars remain.
     * </p>
     *
     * @param familiar the familiar to remove
     */
    void removeFamiliar(FamiliarBat familiar);

    /**
     * Delegates an attack command to the first available familiar in range.
     * <p>
     * The attack is only executed by an idle familiar that is close enough to the
     * character.
     * </p>
     *
     * @param direction the direction in which the familiar should attack
     */
    void attack(Vector2 direction);

    /**
     * Sets the callback to be notified when no familiars remain.
     *
     * @param callback the callback instance
     */
    void setNoFamiliarsCallback(NoFamiliarsCallback callback);

    /**
     * Checks whether there is at least one familiar that is both idle and within
     * attack range.
     *
     * @return {@code true} if a familiar is ready to attack; {@code false}
     *         otherwise
     */
    boolean isFree();

}
