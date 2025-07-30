package it.unibo.falltohell.model.api.gameobject.movable;

import it.unibo.falltohell.model.impl.gameobject.movable.entity.character.Druid;
import it.unibo.falltohell.util.Vector2;

/**
 * Represents a movable bat familiar that can attack in specific directions
 * and interact with its owning {@link Druid}.
 *
 * @author Sara Visani
 */
public interface FamiliarBat extends Movable {

    /**
     * Starts an attack in the given direction.
     * <p>
     * The number of attacks (hits) is determined randomly with weighted
     * probabilities.
     *
     * @param direction the normalized direction vector for the attack
     */
    void attack(Vector2 direction);

    /**
     * Checks whether the FamiliarBat is currently idle (not attacking).
     *
     * @return {@code true} if idle; {@code false} if attacking
     */
    boolean isIdle();

    /**
     * Checks whether the bat is close enough to the character to be considered in
     * range for attack.
     *
     * @return {@code true} if within attack range; {@code false} otherwise
     */
    boolean isInAttackRange();

    /**
     * Clears the attack finish listener reference.
     * Should be called when the FamiliarBat is removed.
     */
    void clearListener();

    /**
     * Returns the unique name of the FamiliarBat, used for timer identification.
     *
     * @return the unique name of the bat
     */
    String getName();

    /**
     * Returns the character that owns this FamiliarBat, cast as a {@link Druid}.
     *
     * @return the owning druid character
     */
    Druid getCharacter();

}
