package it.unibo.falltohell.model.impl.gameobjects.movable.entity;

import it.unibo.falltohell.controller.api.ImageController;
import it.unibo.falltohell.controller.impl.ImageControllerImpl;
import it.unibo.falltohell.model.api.Drawable;
import it.unibo.falltohell.model.api.GameObject;
import it.unibo.falltohell.model.api.Level;
import it.unibo.falltohell.model.api.gameobjects.Block;
import it.unibo.falltohell.model.api.gameobjects.Interactable;
import it.unibo.falltohell.model.api.gameobjects.movable.entity.Character;
import it.unibo.falltohell.model.api.gameobjects.movable.entity.statistic.CharacterStatistics;
import it.unibo.falltohell.model.api.gameobjects.movable.entity.statistic.buff.BuffManager;
import it.unibo.falltohell.model.impl.GameEventManager;
import it.unibo.falltohell.model.impl.Sprite;
import it.unibo.falltohell.model.impl.gameobjects.movable.EntityImpl;
import it.unibo.falltohell.model.impl.gameobjects.movable.entity.statistics.buff.BuffManagerImpl;
import it.unibo.falltohell.model.impl.physics.colliders.BoxCollider;
import it.unibo.falltohell.util.Vector2;

import java.io.IOException;
import java.util.Optional;

/**
 * Base class for a character.
 * Every character has different parameters for life, attack, attack speed, speed, mana.
 *
 * @author Davide Mancini
 */
public abstract class BaseCharacter extends EntityImpl implements Character {

    private static final int MAX_JUMP_HEIGHT = 15;
    private static final Vector2 JUMP_ACCELERATION = new Vector2(0.0, -0.0625);
    private static final Vector2 GRAVITY_STEP = new Vector2(0.0, 0.0625);

    private final GameEventManager<String> input;
    private final CharacterStatistics stats;
    private final BuffManager buffManager;
    private Vector2 jumpVelocity;
    private Vector2 gravity;
    private int currentJumpHeight;
    private boolean onGround;
    private Optional<Interactable> interactingObject;

    /**
     * Base constructor for a new character.
     *
     * @param level
     * @param position
     * @param stats
     */
    public BaseCharacter(final Level level, final Vector2 position, final CharacterStatistics stats) {
        super(level, position, new BoxCollider(Vector2.zero(), stats.getDimensions()), stats);
        this.onGround = false;
        this.currentJumpHeight = 0;
        this.jumpVelocity = Vector2.zero();
        this.gravity = Vector2.zero();
        this.stats = stats;
        this.input = level.getGameEventManager();
        this.buffManager = new BuffManagerImpl(level.getTimerManager());
        this.interactingObject = Optional.empty();
    }

    /**
     * Moves the character left or right based on the direction read by the input.
     * How fast it moves depends on current speed.
     * If both direction are read at the same time, the character remains still.
     *
     * @param deltaTime difference between two frames
     */
    private void move(final double deltaTime) {
        Vector2 velocity = Vector2.zero();
        if (this.input.checkCondition("MoveLeft")) {
            velocity = velocity.add(Vector2.left());
        }
        if (this.input.checkCondition("MoveRight")) {
            velocity = velocity.add(Vector2.right());
        }
        velocity = velocity.multiply(this.getStats().getSpeed().x()).multiply(deltaTime);
        this.setPosition(this.getPosition().add(velocity));
    }

    /**
     * If the character is on ground it can jump until it reach max jump height or jump less than max height if the
     * jump key is released.
     *
     * @param deltaTime difference between two frames
     */
    private void jump(final double deltaTime) {
        if (this.input.checkCondition("Jump")
            && (this.onGround || this.currentJumpHeight > 0 && this.currentJumpHeight < MAX_JUMP_HEIGHT)
        ) {
            this.currentJumpHeight++;
            this.onGround = false;
            this.jumpVelocity = this.jumpVelocity.add(JUMP_ACCELERATION.multiply(MAX_JUMP_HEIGHT - this.currentJumpHeight).multiply(deltaTime));
            this.setPosition(this.getPosition().add(this.jumpVelocity));
        } else {
            this.jumpVelocity = Vector2.zero();
        }
        if (!this.input.checkCondition("Jump")) {
            this.currentJumpHeight = 0;
        }
    }

    /**
     * Apply gravity to the character every frame.
     *
     * @param deltaTime difference between two frames
     */
    private void applyGravity(final double deltaTime) {
        if (!this.onGround) {
            this.gravity = this.gravity.add(GRAVITY_STEP.multiply(deltaTime));
            this.setPosition(this.getPosition().add(this.gravity));
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update(final double deltaTime) {
        this.move(deltaTime);
        this.jump(deltaTime);
        this.applyGravity(deltaTime);
    }

    /**
     * {@inheritDoc}
     * Notify if the character is on ground and check if player is colliding with an interactable.
     */
    @Override
    public void onCollision(final GameObject other, final Vector2 direction) {
        if (other instanceof Block && direction.equals(Vector2.up())) {
            this.currentJumpHeight = 0;
            this.onGround = true;
        }
        if (other instanceof Interactable interactable) {
            this.interactingObject = Optional.of(interactable);
        }
    }

    /**
     * {@inheritDoc}
     * Notify if the character is leaving the ground and check if player is leaving an interactable.
     */
    @Override
    public void onCollisionExit(final GameObject other, final Vector2 direction) {
        if (other instanceof Block && direction.equals(Vector2.up())) {
            this.onGround = false;
        }
        if (other instanceof Interactable) {
            this.interactingObject = Optional.empty();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void interact() {
        if (this.input.checkCondition("Interact")) {
            this.interactingObject.ifPresent(i -> i.interact(this));
        }
    }

    /**
     * {@inheritDoc}
     * If character has a temporary life buff, the buff take the damage instead of the character until it reaches zero.
     * When temporary life is consumed, the character takes the damage not absorbed by the temporary life.
     */
    @Override
    public void setDamagedLife(final double damage) {
        final double remainingTemporaryLife = this.stats.getTemporaryLife() - damage;
        if (remainingTemporaryLife == 0) {
            this.stats.setTemporaryLife(0);
        } else if (remainingTemporaryLife > 0) {
            this.stats.subTemporaryLife(damage);
        } else {
            this.stats.setTemporaryLife(0);
            super.setDamagedLife(-remainingTemporaryLife);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BuffManager getBuffManager() {
        return this.buffManager;
    }
}
