package it.unibo.falltohell.model.api.gameobject.movable.entity.enemy;

import it.unibo.falltohell.model.api.gameobject.movable.entity.character.Character;
import it.unibo.falltohell.model.api.gameobject.movable.entity.Entity;

/**
 * Represents a game enemy that extends {@link Entity}.
 * <p>
 * An {@code Enemy} can follow or target a {@link Character}, such as the
 * player.
 * This interface defines behavior for dynamically setting the character it
 * should interact with.
 *
 * @see Entity
 * @see Character
 * @author Sara Visani
 */

public interface Enemy extends Entity {
}
