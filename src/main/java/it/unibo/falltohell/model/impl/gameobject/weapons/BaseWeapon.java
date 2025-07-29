package it.unibo.falltohell.model.impl.gameobject.weapons;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import it.unibo.falltohell.model.api.manager.TimerManager;
import it.unibo.falltohell.model.api.gameobject.movable.entity.character.Character;
import it.unibo.falltohell.model.api.statistic.CharacterStatistics;
import it.unibo.falltohell.model.api.gameobject.weapon.Weapon;
import it.unibo.falltohell.model.api.physics.Collider;
import it.unibo.falltohell.model.impl.timer.CustomTimerImpl;
import it.unibo.falltohell.model.impl.gameobject.GameObjectImpl;
import it.unibo.falltohell.model.impl.physics.BoxCollider;
import it.unibo.falltohell.util.Dimensions;
import it.unibo.falltohell.util.Priority;
import it.unibo.falltohell.util.Vector2;

import java.util.Optional;

/**
 * Base class for all weapons in the game.
 *
 * <p>
 * This class provides default behavior for weapons that can be attached to a
 * {@link Character}, including attack handling with a cooldown mechanism and
 * automatic positioning based on the owner's location.
 * </p>
 *
 * <p>
 * Weapons are {@link GameObjectImpl game objects} and implement the
 * {@link Weapon} interface.
 * </p>
 *
 * @author Davide Mancini
 */
public abstract class BaseWeapon extends GameObjectImpl implements Weapon {

    private static final long MINIMUM_ATTACK_TIME = 100;

    private final Character owner;
    private final Vector2 offset;
    private final long cooldownTime;
    private boolean attacking;

    /**
     * Creates an abstract weapon.
     *
     * @param owner        of the weapon
     * @param collider     of the weapon if it has one
     * @param cooldownTime time to elapse between every attack
     * @param fileName     of the weapon's drawable
     * @param offset       where to set the position based on the owner's position
     */
    @SuppressFBWarnings(
        value = "EI_EXPOSE_REP2",
        justification = "The weapon follows the owner by taking its position"
    )
    public BaseWeapon(final Character owner, final Optional<Collider> collider,
            final long cooldownTime, final String fileName, final Vector2 offset) {
        super(owner.getLevel(), owner.getPosition(), collider.orElse(new BoxCollider(new Dimensions(0, 0))));
        this.owner = owner;
        this.offset = offset;
        this.cooldownTime = cooldownTime;
        this.attacking = false;
        this.initDrawable(Priority.MEDIUM, fileName);
    }

    /**
     * {@inheritDoc}
     * Updates the position and the facing direction based on the owner's.
     */
    @Override
    public void update() {
        this.getDrawable().ifPresent(t -> {
            t.mirror(!this.getOwner().isFacingRight());
            t.setVisible(this.attacking);
        });
        final Vector2 offset = new Vector2(this.offset.x() * (this.owner.isFacingRight() ? 1.0 : -1.0), 0);
        this.setPosition(this.owner.getPosition().add(offset));
    }

    /**
     * {@inheritDoc}
     * The owner can attack every time interval based on a cooldown time.
     */
    @Override
    public void attack() {
        if (!this.attacking) {
            this.attacking = true;
            this.onAttack();
            final String name = "weapon-cooldown" + this.hashCode();
            final TimerManager tm = this.getLevel().getTimerManager();
            final CharacterStatistics stats = (CharacterStatistics) this.owner.getStats();
            final double reduceTimeMultiplier = 1 / stats.getAttackSpeed();
            final long attackCooldownTime = Math.max(MINIMUM_ATTACK_TIME,
                    (long) (this.cooldownTime * reduceTimeMultiplier));
            tm.restartIfPresent(name, new CustomTimerImpl(attackCooldownTime, () -> this.attacking = false));
        }
    }

    /**
     * @return if the owner is attacking
     */
    protected boolean isAttacking() {
        return this.attacking;
    }

    /**
     * @return owner of the weapon
     */
    protected Character getOwner() {
        return this.owner;
    }

    /**
     * Any action to do on attack.
     */
    protected void onAttack() {
        // Default: do nothing
    }
}
