package it.unibo.falltohell.model.impl.gameobjects.movable.entity;

import it.unibo.falltohell.model.api.GameObject;
import it.unibo.falltohell.model.api.Level;
import it.unibo.falltohell.model.api.gameobjects.Interactable;
import it.unibo.falltohell.model.api.gameobjects.movable.entity.Character;
import it.unibo.falltohell.model.api.gameobjects.movable.entity.statistic.CharacterStatistics;
import it.unibo.falltohell.model.impl.gameobjects.movable.EntityImpl;
import it.unibo.falltohell.model.impl.physics.colliders.BoxCollider;
import it.unibo.falltohell.util.Vector2;

/**
 * Base class for a character.
 * Every character has different parameters for life, attack, attack speed, speed, mana.
 *
 * @author Davide Mancini
 */
public abstract class BaseCharacter extends EntityImpl implements Character {

    // TODO: Update when statistics are used inside of entity
    private boolean canInteract;

    // TODO: It needs to use a real level where it should be added automatically
    /**
     * Base constructor for a new character.
     * @param position
     */
    public BaseCharacter(final Level level, final Vector2 position, final CharacterStatistics stats) {
        super(level, position, new BoxCollider(Vector2.zero(), stats.getDimensions()), stats);
        this.canInteract = false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update(final double deltaTime) {
        super.update(deltaTime);
        this.canInteract = false;
    }

    /**
     * {@inheritDoc}
     * Check if player is colliding with an interactable.
     */
    @Override
    public void onCollision(final GameObject other, final Vector2 direction) {
        if (other instanceof Interactable) {
            this.canInteract = true;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void interact(final Interactable interactable) {
        // TODO: Add an event (like key press) that when it happen the player will interact with the object
        if (this.canInteract) {
            interactable.interact();
        }
    }
}
