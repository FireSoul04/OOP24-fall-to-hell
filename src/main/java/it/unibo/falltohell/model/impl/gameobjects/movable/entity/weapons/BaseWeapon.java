package it.unibo.falltohell.model.impl.gameobjects.movable.entity.weapons;

import it.unibo.falltohell.model.api.CustomTimer;
import it.unibo.falltohell.model.api.TimerManager;
import it.unibo.falltohell.model.api.gameobjects.movable.entity.Character;
import it.unibo.falltohell.model.api.gameobjects.movable.entity.statistic.CharacterStatistics;
import it.unibo.falltohell.model.api.gameobjects.movable.entity.weapons.Weapon;
import it.unibo.falltohell.model.api.physics.Collider;
import it.unibo.falltohell.model.impl.CustomTimerImpl;
import it.unibo.falltohell.model.impl.GameObjectImpl;
import it.unibo.falltohell.util.Priority;

public abstract class BaseWeapon extends GameObjectImpl implements Weapon {

    private final Character owner;
    private final long cooldownTime;
    private boolean attacking;

    public BaseWeapon(final Character owner, final Collider collider,
                      final long cooldownTime, final String fileName) {
        super(owner.getLevel(), owner.getPosition(), collider);
        this.owner = owner;
        this.cooldownTime = cooldownTime;
        this.attacking = false;
        this.initDrawable(Priority.MEDIUM, fileName);
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
            if (!tm.searchTimer(name)) {
                final CharacterStatistics stats = (CharacterStatistics) this.owner.getStats();
                final double reduceTimeMultiplier = stats.getInitialAttackSpeed() / stats.getAttackSpeed();
                final long attackCooldownTime = (long) (this.cooldownTime * reduceTimeMultiplier);
                final CustomTimer attackCooldown = new CustomTimerImpl(attackCooldownTime, () -> this.attacking = false);
                tm.addTimer(name, attackCooldown);
            } else {
                tm.restartTimer(name);
            }
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
