package it.unibo.falltohell.model.impl.gameobjects.movable.entity;

import it.unibo.falltohell.model.api.GameObject;
import it.unibo.falltohell.model.api.Level;
import it.unibo.falltohell.model.impl.gameobjects.block.BaseBlock;
import it.unibo.falltohell.model.api.gameobjects.Interactable;
import it.unibo.falltohell.model.api.gameobjects.movable.entity.Character;
import it.unibo.falltohell.model.api.gameobjects.movable.entity.statistic.CharacterStatistics;
import it.unibo.falltohell.model.api.gameobjects.movable.entity.statistic.buff.BuffManager;
import it.unibo.falltohell.model.impl.GameEventManager;
import it.unibo.falltohell.model.impl.gameobjects.movable.EntityImpl;
import it.unibo.falltohell.model.impl.gameobjects.movable.entity.statistics.buff.BuffManagerImpl;
import it.unibo.falltohell.util.Vector2;

import java.util.Optional;

/**
 * Base class for a character.
 * Every character has different parameters for life, attack, attack speed, speed, mana.
 *
 * @author Davide Mancini
 */
public abstract class BaseCharacter extends EntityImpl implements Character {

    private static final int MAX_JUMP_HEIGHT = 30;
    private static final Vector2 GRAVITY_STEP = new Vector2(0.0, 0.06);

    private final GameEventManager<String> input;
    private final CharacterStatistics stats;
    private final BuffManager buffManager;
    private Vector2 jumpVelocity;
    private Vector2 gravity;
    private int currentJumpFrames;
    private double jumpingSpeed;
    private boolean onGround;
    private boolean jumping;
    private Optional<Interactable> interactingObject;

    /**
     * Base constructor for a new character.
     *
     * @param level where it belongs
     * @param position where is it located inside the level
     * @param stats of the character
     */
    public BaseCharacter(final Level level, final Vector2 position, final CharacterStatistics stats) {
        super(level, position, stats);
        this.onGround = false;
        this.currentJumpFrames = 0;
        this.jumpingSpeed = this.getStats().getInitialSpeed().y();
        this.jumping = false;
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
     * jump event is released. The character jump height is based on its current y speed at the start of the jump.
     *
     * @param deltaTime difference between two frames
     */
    private void jump(final double deltaTime) {
        if (this.input.checkCondition("Jump") && this.onGround) {
            this.onGround = false;
            this.jumping = true;
            this.currentJumpFrames = 1;
        }
        if (this.onGround) {
            this.jumpingSpeed = this.getStats().getSpeed().y();
        }
        if (!this.input.checkCondition("Jump")) {
            this.jumping = false;
        }
        if (this.currentJumpFrames > 0 && this.currentJumpFrames < MAX_JUMP_HEIGHT) {
            // This multiplier let the character slow down when the player stop the jump event
            final double multiplier = (MAX_JUMP_HEIGHT - this.currentJumpFrames) / (double) MAX_JUMP_HEIGHT;
            final double corrector = GRAVITY_STEP.y() * (this.jumping ? 1.0 : multiplier);
            this.jumpVelocity = new Vector2(
                0.0,
                2 * (currentJumpFrames - MAX_JUMP_HEIGHT) * this.jumpingSpeed * corrector * deltaTime
            );
            this.setPosition(this.getPosition().add(this.jumpVelocity));
            this.currentJumpFrames++;
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
     * If the character is inside a BaseBlock because of gravity, this method will move the character up to the floor level.
     */
    @Override
    public void onCollision(final GameObject other, final Vector2 direction) {
        if (other instanceof BaseBlock && direction.equals(Vector2.down())) {
            this.currentJumpFrames = 0;
            this.onGround = true;
            this.gravity = Vector2.zero();
            this.moveUpToFloor(other);
        }
        if (other instanceof Interactable interactable) {
            this.interactingObject = Optional.of(interactable);
        }
    }

    /**
     * Move the character up until it reaches the floor's height.
     * @param other block colliding with
     */
    private void moveUpToFloor(final GameObject other) {
        final double distance = this.getPosition().subtract(other.getPosition()).y();
        final double thisHeight = this.getCollider().size().height();
        final double otherHeight = other.getCollider().size().height();
        final double idealDistance = (thisHeight + otherHeight) / 2;
        // Range of values for the y that the character needs to be to reach floor level
        final double eps = 1 + (distance / thisHeight);
        if (Math.abs(Math.abs(distance) - idealDistance) > eps) {
            this.setPosition(this.getPosition().subtract(new Vector2(0, eps)));
        }
    }

    /**
     * {@inheritDoc}
     * Notify if the character is leaving the ground and check if player is leaving an interactable.
     */
    @Override
    public void onCollisionExit(final GameObject other, final Vector2 direction) {
        if (other instanceof BaseBlock && direction.equals(Vector2.down())) {
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
