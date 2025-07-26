package it.unibo.falltohell.model.impl.gameobject.movable.entity.character;

import it.unibo.falltohell.model.api.gameobject.weapon.Weapon;
import it.unibo.falltohell.model.impl.gameobject.movable.entity.EntityImpl;
import it.unibo.falltohell.util.Priority;
import it.unibo.falltohell.model.api.gameobject.GameObject;
import it.unibo.falltohell.model.api.level.Level;
import it.unibo.falltohell.model.impl.gameobject.block.BaseCollidableBlock;
import it.unibo.falltohell.model.api.gameobject.interactable.Interactable;
import it.unibo.falltohell.model.api.gameobject.movable.entity.character.Character;
import it.unibo.falltohell.model.api.statistic.CharacterStatistics;
import it.unibo.falltohell.model.api.manager.BuffManager;
import it.unibo.falltohell.model.impl.manager.GameEventManagerImpl;
import it.unibo.falltohell.model.impl.manager.BuffManagerImpl;
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
    private static final double JUMP_STEP = 0.06;

    private final GameEventManagerImpl<String> input;
    private final CharacterStatistics stats;
    private final BuffManager buffManager;
    private int currentJumpFrames;
    private double jumpingSpeed;
    private boolean jumping;
    private Optional<Weapon> equippedWeapon;
    private Optional<Interactable> interactingObject;

    /**
     * Base constructor for a new character.
     *
     * @param level where it belongs
     * @param position where is it located inside the level
     * @param stats of the character
     * @param fileName is the name of the image file associated to the character
     */
    public BaseCharacter(final Level level, final Vector2 position, final CharacterStatistics stats,
                         final String fileName) {
        super(level, position, stats);
        this.currentJumpFrames = 0;
        this.jumpingSpeed = this.getStats().getInitialSpeed().y();
        this.jumping = false;
        this.stats = stats;
        this.input = level.getGameEventManager();
        this.buffManager = new BuffManagerImpl(level.getTimerManager());
        this.equippedWeapon = Optional.empty();
        this.interactingObject = Optional.empty();
        this.initDrawable(Priority.LOW, fileName);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update(final double deltaTime) {
        super.update(deltaTime);
        this.move(deltaTime);
        this.jump(deltaTime);
        this.interact();
        this.attack();
        this.equippedWeapon.ifPresent(t -> t.setPosition(this.getPosition()));
    }

    /**
     * Moves the character left or right based on the direction read by the input.
     * How fast it moves depends on current speed.
     * If both direction are read at the same time, the character remains still.
     *
     * @param deltaTime difference between two frames
     */
    private void move(final double deltaTime) {
        Vector2 moveVelocity = Vector2.zero();
        if (this.input.checkCondition("MoveLeft")) {
            moveVelocity = moveVelocity.add(Vector2.left());
            this.setFacingRight(false);
        }
        if (this.input.checkCondition("MoveRight")) {
            moveVelocity = moveVelocity.add(Vector2.right());
            this.setFacingRight(true);
        }
        moveVelocity = moveVelocity.multiply(this.getStats().getSpeed().x()).multiply(deltaTime);
        this.addForce(moveVelocity);
    }

    /**
     * If the character is on ground it can jump until it reach max jump height or jump less than max height if the
     * jump event is released. The character jump height is based on its current y speed at the start of the jump.
     *
     * @param deltaTime difference between two frames
     */
    private void jump(final double deltaTime) {
        if (this.input.checkCondition("Jump") && this.isOnGround()) {
            this.jumping = true;
            this.currentJumpFrames = 1;
        }
        if (this.isOnGround()) {
            this.jumpingSpeed = this.getStats().getSpeed().y();
        }
        if (!this.input.checkCondition("Jump")) {
            this.jumping = false;
        }
        if (this.currentJumpFrames > 0 && this.currentJumpFrames < MAX_JUMP_HEIGHT) {
            // This multiplier let the character slow down when the player stop the jump event
            final double multiplier = (MAX_JUMP_HEIGHT - this.currentJumpFrames) / (double) MAX_JUMP_HEIGHT;
            final double corrector = JUMP_STEP * (this.jumping ? 1.0 : multiplier);
            final Vector2 jumpVelocity = new Vector2(
                0.0,
                2 * (currentJumpFrames - MAX_JUMP_HEIGHT) * this.jumpingSpeed * corrector * deltaTime
            );
            this.addForce(jumpVelocity);
            this.currentJumpFrames++;
        }
    }

    /**
     * {@inheritDoc}
     * Notify if the character is on ground and check if player is colliding with an interactable.
     * If the character is inside a BaseBlock because of gravity, this method will move the character up to the floor level.
     */
    @Override
    public void onCollision(final GameObject other, final Vector2 direction) {
        super.onCollision(other, direction);
        if (other instanceof BaseCollidableBlock && direction.equals(Vector2.down())) {
            this.currentJumpFrames = 0;
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
        super.onCollisionExit(other, direction);
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
     */
    @Override
    public void attack() {
        this.equippedWeapon.ifPresent(w -> {
            if (this.input.checkCondition("NormalAttack")) {
                w.attack();
            }
        });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Weapon> getEquippedWeapon() {
        return equippedWeapon;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void equipWeapon(final Weapon weapon) {
        this.equippedWeapon = Optional.of(weapon);
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

    /**
     * @return if the event jump is true
     */
    protected boolean isJumping() {
        return this.jumping;
    }

    /**
     * Let the character jump again.
     */
    protected void resetJump() {
        this.currentJumpFrames = 1;
        this.resetGravity();
        this.jumping = true;
    }
}
