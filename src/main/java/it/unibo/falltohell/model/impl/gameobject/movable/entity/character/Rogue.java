package it.unibo.falltohell.model.impl.gameobject.movable.entity.character;

import it.unibo.falltohell.model.api.gameobject.GameObject;
import it.unibo.falltohell.model.api.gameobject.movable.entity.enemy.Enemy;
import it.unibo.falltohell.model.api.level.Level;
import it.unibo.falltohell.model.api.ability.active.SpecialActiveAbility;
import it.unibo.falltohell.model.api.ability.passive.StatisticPassiveAbility;
import it.unibo.falltohell.model.api.manager.TimerManager;
import it.unibo.falltohell.model.api.statistic.CharacterStatistics;
import it.unibo.falltohell.model.api.buff.Buff;
import it.unibo.falltohell.model.impl.factory.AbilityFactoryImpl;
import it.unibo.falltohell.model.impl.ability.active.ThrowKnifeAbility;
import it.unibo.falltohell.model.impl.buff.SpeedBuff;
import it.unibo.falltohell.model.impl.gameobject.movable.projectile.BaseEnemyProjectile;
import it.unibo.falltohell.model.impl.gameobject.weapons.Dagger;
import it.unibo.falltohell.model.impl.factory.StatisticFactoryImpl;
import it.unibo.falltohell.model.impl.timer.CustomTimerImpl;
import it.unibo.falltohell.util.Dimensions;
import it.unibo.falltohell.util.Vector2;

/**
 * Character representing a rogue.
 * It is fast, can attack at short range and has not too much defense.
 * It has the ability to throw knifes.
 */
public class Rogue extends BaseCharacter {

    private static final double LIFE = 10;
    private static final double ATTACK = 10;
    private static final double MANA = 10;
    private static final double ATTACK_SPEED = 10;
    private static final Vector2 SPEED = new Vector2(2.0, 1.5);
    private static final CharacterStatistics STATS = new StatisticFactoryImpl()
        .createCharacterStatistic(LIFE, ATTACK, SPEED, new Dimensions(20, 25), MANA, ATTACK_SPEED);

    private static final long PASSIVE_COOLDOWN_TIME = 2000;

    private final StatisticPassiveAbility evadeAbility;
    private final SpecialActiveAbility knifeAbility;
    private boolean canDoubleJump;
    private boolean canUsePassive;

    /**
     * Creates a rogue.
     *
     * @param level where it belongs
     * @param position where is it in the level
     */
    public Rogue(final Level level, final Vector2 position) {
        super(level, position, STATS, "rogue.png");
        this.canDoubleJump = false;
        this.canUsePassive = true;
        this.equipWeapon(new Dagger(this));
        this.evadeAbility = new AbilityFactoryImpl()
            .createPassiveAbility(this, character -> {
                if (this.canUsePassive) {
                    final TimerManager tm = this.getLevel().getTimerManager();
                    final String passiveCooldownTimerName = "cooldown";
                    final Buff speedBuff = new SpeedBuff(STATS, 0.5);
                    this.getBuffManager().addBuff(speedBuff);
                    this.canUsePassive = false;
                    if (!tm.searchTimer(passiveCooldownTimerName)) {
                        tm.addTimer(
                            passiveCooldownTimerName,
                            new CustomTimerImpl(PASSIVE_COOLDOWN_TIME, () -> this.canUsePassive = true)
                        );
                    } else {
                        tm.restartTimer(passiveCooldownTimerName);
                    }
                }
            });
        this.knifeAbility = new ThrowKnifeAbility(this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update(final double deltaTime) {
        super.update(deltaTime);
        this.doubleJump();
        if (this.getLevel().getGameEventManager().checkCondition("ActiveAbility")) {
            this.knifeAbility.activate();
        }
    }

    private void doubleJump() {
        if (this.getLevel().getGameEventManager().checkCondition("Jump") && !this.isJumping() && this.canDoubleJump) {
            this.resetJump();
            this.canDoubleJump = false;
        }
        if (this.isOnGround()) {
            this.canDoubleJump = true;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CharacterID getCharacterID() {
        return CharacterID.ROGUE;
    }

    /**
     * {@inheritDoc}
     * Active passive ability on take damage.
     */
    @Override
    public void setDamagedLife(final double damage) {
        super.setDamagedLife(damage);
    }

    /**
     * {@inheritDoc}
     * Activate passive ability when rogue get hit by enemy or enemy projectile.
     */
    @Override
    public void onCollision(final GameObject other, final Vector2 direction) {
        super.onCollision(other, direction);
        if (other instanceof Enemy || other instanceof BaseEnemyProjectile) {
            this.evadeAbility.carryOut();
        }
    }
}
