package it.unibo.falltohell.model.impl.gameobject.movable.entity.character;

import it.unibo.falltohell.model.api.ability.active.SpecialActiveAbility;
import it.unibo.falltohell.model.api.level.Level;
import it.unibo.falltohell.model.api.ability.passive.StatisticPassiveAbility;
import it.unibo.falltohell.model.api.manager.TimerManager;
import it.unibo.falltohell.model.api.statistic.CharacterStatistics;
import it.unibo.falltohell.model.api.gameobject.weapon.Weapon;
import it.unibo.falltohell.model.impl.ability.active.BlastAbility;
import it.unibo.falltohell.model.impl.ability.active.HealAbility;
import it.unibo.falltohell.model.impl.timer.CustomTimerImpl;
import it.unibo.falltohell.model.impl.factory.AbilityFactoryImpl;
import it.unibo.falltohell.model.impl.factory.StatisticFactoryImpl;
import it.unibo.falltohell.model.impl.gameobject.weapons.Staff;
import it.unibo.falltohell.model.impl.gameobject.weapons.Tome;
import it.unibo.falltohell.util.Dimensions;
import it.unibo.falltohell.util.Vector2;

/**
 * Class that represents the caster character.
 * @author Martina Malagoli
 */
public class Caster extends BaseCharacter {

    private static final double LIFE = 50;
    private static final double ATTACK = 20;
    private static final double ATTACK_SPEED = 8;
    private static final Vector2 SPEED = new Vector2(2, 2);
    private static final double MANA = 25;
    private static final double AMOUNT_MANA_NORMAL_ATTACK = 1;
    private static final double AMOUNT_MANA_RECHARGED = 2;
    private static final long COOLDOWN_MANA_RECHARGE = 5000;
    private static final CharacterStatistics STATISTICS = new StatisticFactoryImpl()
            .createCharacterStatistic(LIFE, ATTACK, SPEED, new Dimensions(20,20), MANA, ATTACK_SPEED);

    private final Weapon staff;
    private final Weapon tome;

    private final SpecialActiveAbility healing;
    private final SpecialActiveAbility blast;

    /**
     * Initialization of the Caster class.
     *
     * @param level where the caster actually is
     * @param position of the caster in the level
     */
    public Caster(final Level level, final Vector2 position) {
        super(level, position, STATISTICS, "caster.png");
        this.staff = new Staff(this);
        this.tome = new Tome(this);
        this.equipWeapon(tome);
        final TimerManager timerManager = this.getLevel().getTimerManager();
        final String timerName = "mana_recharge";
        final StatisticPassiveAbility manaRecharge = new AbilityFactoryImpl().createPassiveAbility(this,
            character -> {
                if (!timerManager.searchTimer(timerName)) {
                    timerManager.addTimer(
                            timerName, new CustomTimerImpl(COOLDOWN_MANA_RECHARGE,
                                    () -> {
                                        final CharacterStatistics statistics = (CharacterStatistics) character.getStats();
                                        statistics.addMana(AMOUNT_MANA_RECHARGED);
                                        timerManager.restartTimer(timerName);
                                    }));
                } else {
                    timerManager.restartTimer(timerName);
                }
            });
        manaRecharge.carryOut();
        this.blast = new BlastAbility(this);
        this.healing = new HealAbility(this);

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CharacterID getCharacterID() {
        return CharacterID.CASTER;
    }

    /**
     * {@inheritDoc}
     * The weapon changes depending on the quantity of mana available:
     * if the mana is enough to cast a spell the caster will attack with
     * the tome, else the caster will use the staff.
     */
    @Override
    public void attack() {
        if (this.hasEnoughMana(AMOUNT_MANA_NORMAL_ATTACK)) {
            this.changeEquippedWeapon(this.tome);
        } else {
            this.changeEquippedWeapon(this.staff);
        }
        super.attack();
    }

    /**
     * Method to check if the weapon to use is correct,
     * otherwise the weapon will be changed. 
     * @param weapon to be used
     */
    private void changeEquippedWeapon(final Weapon weapon) {
        this.getEquippedWeapon().ifPresent(w -> {
            if (!w.equals(weapon)) {
                this.equipWeapon(weapon);
            }
        });
    }

    /**
     * {@inheritDoc}
     * This method also checks if an active attack was used.
     */
    @Override
    public void update(double deltaTime) {
        super.update(deltaTime);
        if (this.getLevel().getGameEventManager().checkCondition("ActiveAbility")) {
            this.blast.activate();
        } else if (this.getLevel().getGameEventManager().checkCondition("SpecialAbility")) {
            this.healing.activate();
        }
    }

    /**
     * @return the mana needed to make a magic normal attack
     */
    public double getAmountManaNormalAttack() {
        return AMOUNT_MANA_NORMAL_ATTACK;
    }
}
