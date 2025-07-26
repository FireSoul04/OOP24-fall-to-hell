package it.unibo.falltohell.model.impl.gameobjects.movable.entity.character;

import it.unibo.falltohell.model.api.Level;
import it.unibo.falltohell.model.api.abilities.active.SpecialActiveAbility;
import it.unibo.falltohell.model.api.abilities.passive.StatisticPassiveAbility;
import it.unibo.falltohell.model.api.gameobjects.movable.entity.statistic.CharacterStatistics;
import it.unibo.falltohell.model.api.gameobjects.movable.entity.statistic.buff.Buff;
import it.unibo.falltohell.model.impl.abilities.AbilityFactoryImpl;
import it.unibo.falltohell.model.impl.abilities.active.ThrowKnifeAbility;
import it.unibo.falltohell.model.impl.gameobjects.movable.entity.statistics.buff.SpeedBuff;
import it.unibo.falltohell.model.impl.gameobjects.movable.entity.weapons.Dagger;
import it.unibo.falltohell.model.impl.gameobjects.movable.entity.BaseCharacter;
import it.unibo.falltohell.model.impl.gameobjects.movable.entity.StatisticFactoryImpl;
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

    private final StatisticPassiveAbility evadeAbility;
    private final SpecialActiveAbility knifeAbility;
    private boolean canDoubleJump;

    /**
     * Creates a rogue.
     *
     * @param level where it belongs
     * @param position where is it in the level
     */
    public Rogue(final Level level, final Vector2 position) {
        super(level, position, STATS, "rogue.png");
        this.canDoubleJump = false;
        this.equipWeapon(new Dagger(this));
        this.evadeAbility = new AbilityFactoryImpl()
            .createPassiveAbility(this, character -> {
                final Buff speedBuff = new SpeedBuff(STATS, 0.5);
                this.getBuffManager().addBuff(speedBuff);
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
    public void setDamagedLife(double damage) {
        super.setDamagedLife(damage);
        this.evadeAbility.carryOut();
    }
}
