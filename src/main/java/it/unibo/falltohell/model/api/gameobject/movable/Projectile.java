package it.unibo.falltohell.model.api.gameobject.movable;

/**
 * Represents a projectile in the game that can move and hit with other game objects.
 * This interface extends the {@link Movable} interface and adds methods for handling
 * hit status and collision with other game objects.
 *
 * @author Casadei Lorenzo
 */
public interface Projectile extends Movable {

    /**
     * Returns whether the projectile has hit something.
     *
     * @return {@code true} if the projectile has hit, {@code false} otherwise
     */
    boolean isHit();

    /**
     * Sets the hit status of the projectile.
     *
     * @param hit {@code true} if the projectile has hit something, {@code false}
     *            otherwise
     */
    void setHit(boolean hit);

}
