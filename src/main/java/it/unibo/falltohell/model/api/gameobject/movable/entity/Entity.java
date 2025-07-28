package it.unibo.falltohell.model.api.gameobject.movable.entity;

import it.unibo.falltohell.model.api.gameobject.movable.Movable;
import it.unibo.falltohell.model.api.manager.BuffManager;
import it.unibo.falltohell.model.api.statistic.Statistics;

/**
 * Interface representing a movable game entity, such as an enemy and character.
 * It extends {@link Movable} to inherit movement capabilities.
 * Provides access to {@link Statistics} and life management.
 *
 * @author Sara Visani
 */

public interface Entity extends Movable {

    /**
     * Gets the statistics of this entity.
     * <p>
     *
     * @return the {@link Statistics} associated with this entity
     */
    Statistics getStats();

    /**
     * Notifies the entity that it has taken damage.
     * This method is called by other entities when an attack hits.
     * <p>
     *
     * @param damage the amount of damage dealt to this entity
     */
    void setDamagedLife(double damage);

    /**
     * Checks if the entity is dead.
     * <p>
     *
     * @return {@code true} if the entity is dead; {@code false} otherwise
     */
    boolean isDead();


    /**
     * Checks if the entity is in invincible frame.
     * <p>
     *
     * @return @return {@code true} if the entity is invincible; {@code false}
     *         otherwise
     */
    boolean isInvincible();

    /**
     * 
     * @return the buffManager of this entity.
     */
    BuffManager getBuffManager();
}
