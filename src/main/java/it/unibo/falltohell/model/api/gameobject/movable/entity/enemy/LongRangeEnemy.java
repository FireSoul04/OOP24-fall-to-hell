package it.unibo.falltohell.model.api.gameobject.movable.entity.enemy;

/**
 * Marker interface for enemies that attack from a distance.
 * <p>
 * Used to distinguish enemies capable of ranged attacks from others.
 * Classes implementing this interface indicate that they have
 * ranged attack behavior.
 * </p>
 *
 * <p>
 * For example:
 * </p>
 * <ul>
 * <li>{@link it.unibo.falltohell.model.impl.gameobject.movable.entity.enemy.Tengu}</li>
 * <li>{@link it.unibo.falltohell.model.impl.gameobject.movable.entity.enemy.Lotawiec}</li>
 * </ul>
 *
 * @author Sara Visani
 * @see Enemy
 */
public interface LongRangeEnemy extends Enemy {

}
