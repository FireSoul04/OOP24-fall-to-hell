package it.unibo.falltohell.model.impl.gameobject.movable.entity.character;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import it.unibo.falltohell.model.api.level.Level;
import it.unibo.falltohell.model.api.ability.passive.StatisticPassiveAbility;
import it.unibo.falltohell.model.api.statistic.CharacterStatistics;
import it.unibo.falltohell.model.api.manager.ManagerFamiliars;
import it.unibo.falltohell.model.impl.timer.CustomTimerImpl;
import it.unibo.falltohell.model.impl.manager.ManagerFamiliarsImpl;
import it.unibo.falltohell.model.impl.factory.AbilityFactoryImpl;
import it.unibo.falltohell.model.impl.factory.StatisticFactoryImpl;
import it.unibo.falltohell.model.impl.gameobject.weapons.WarScythe;
import it.unibo.falltohell.util.Dimensions;
import it.unibo.falltohell.util.Vector2;

/**
 * <p>
 * The {@code Druid} is a specialized character that utilizes a passive ability
 * triggered by kills and a summonable ghost familiar for special attacks.
 * </p>
 *
 * <p>
 * Features:
 * </p>
 * <ul>
 * <li>Passive healing and mana restoration based on kill count</li>
 * <li>Summoning ghost familiars for special attacks</li>
 * <li>Manages internal cooldowns and mana costs</li>
 * </ul>
 *
 * @author Sara Visani
 * @see BaseCharacter
 * @see AbilityFactoryImpl
 * @see ManagerFamiliars
 * @see WarScythe
 */
public class Druid extends BaseCharacter {

    private static final int ATTACK_SPEED = 4;
    private static final int MANA = 80;
    private static final Dimensions DIMENSIONS = new Dimensions(20, 25);
    private static final Vector2 SPEED = new Vector2(2.5, 2);
    private static final int ATTACK = 10;
    private static final int LIFE = 30;
    private static final int END_KILL = 5;
    private static final long KILL_RESET = 10_000;
    private static final double CREATION_COST = 30;
    private static final double ATTACK_COST = 10;
    private static final long ATTACK_COOLDOWN = 500;
    private final CharacterStatistics stats;
    private final StatisticPassiveAbility sPa;
    private final ManagerFamiliars manager = new ManagerFamiliarsImpl();
    private int kills;
    private int passiveCycles = 1;
    private boolean sAactive;

    /**
     * <p>
     * Constructs a new {@code Druid} character.
     * </p>
     *
     * @param level    the level this character belongs to
     * @param position the initial spawn position
     */
    @SuppressFBWarnings(
        value = "MC_OVERRIDABLE_METHOD_CALL_IN_CONSTRUCTOR",
        justification = "Using a factory method to create the ability"
    )
    public Druid(final Level level, final Vector2 position) {
        super(level, position, new StatisticFactoryImpl().createCharacterStatistic(LIFE, ATTACK,
            SPEED, DIMENSIONS, MANA, ATTACK_SPEED), "druid.png");
        this.stats = (CharacterStatistics) super.getStats();
        this.equipWeapon(new WarScythe(this, ATTACK_COOLDOWN));
        this.sPa = new AbilityFactoryImpl().createPassiveAbility(this, (character) -> {
            final double[][] lifeManaGains = {
                    {}, // 0 kill
                    { 0.05, 0.0 }, // 1 kill
                    { 0.10, 0.0 }, // 2 kills
                    { 0.10, 0.05 }, // 3 kills
                    { 0.15, 0.10 }, // 4 kills
                    { 0.20, 0.20 }, // 5 kills
            };

            if (this.kills >= 1 && this.kills <= END_KILL) {
                final double lifeGain = stats.getFullLife() * lifeManaGains[this.kills][0] * passiveCycles;
                final double manaGain = stats.getInitialMana() * lifeManaGains[this.kills][1] * passiveCycles;

                stats.setLife(Math.min(stats.getLife() + lifeGain, stats.getFullLife()));
                if (manaGain > 0) {
                    stats.setMana(Math.min(stats.getMana() + manaGain, stats.getInitialMana()));
                }

                if (this.kills == END_KILL) {
                    this.setZeroKill();
                    this.passiveCycles++;
                }
            }
        });
        this.manager.setNoFamiliarsCallback(() -> this.sAactive = false);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update(final double deltaTime) {
        super.update(deltaTime);
        this.handleAttackInput();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CharacterID getCharacterID() {
        return CharacterID.DRUID;
    }

    /**
     * <p>
     * Increases the kill count and triggers passive ability logic.
     * </p>
     *
     * <p>
     * Also restarts or adds a timer to reset kills after 10 seconds.
     * </p>
     *
     * @see #setZeroKill()
     */
    public void addKill() {
        this.kills += 1;
        this.sPa.carryOut();

        final String resetTimerName = "Druid_ResetKills";
        this.restartOrAddTimer(resetTimerName, new CustomTimerImpl(KILL_RESET, this::setZeroKill));
    }

    /**
     * <p>
     * Resets the kill count to zero.
     * </p>
     */
    private void setZeroKill() {
        this.kills = 0;
    }

    /**
     * <p>
     * Handles attack and special ability input from the player.
     * </p>
     *
     * <p>
     * Conditions:
     * </p>
     * <ul>
     * <li>Normal attack cooldown check</li>
     * <li>Summon ghost familiar if enough mana</li>
     * <li>Direct ghost attack in specified direction</li>
     * </ul>
     */
    private void handleAttackInput() {
        if (this.getLevel().checkCondition("SpecialAbility") && super.subManaIfEnough(CREATION_COST)) {
            this.sAactive = true;
            new AbilityFactoryImpl().createGhostActiveAbility(this.manager::createFamiliar, this).action();
        }

        if (this.sAactive && this.spAtkCalled() && this.manager.isFree() && super.subManaIfEnough(ATTACK_COST)) {
            Vector2 direction = Vector2.zero();

            if (this.getLevel().checkCondition("MoveRight")) {
                direction = direction.add(Vector2.right());
            } else if (this.getLevel().checkCondition("MoveLeft")) {
                direction = direction.add(Vector2.left());
            } else if (this.getLevel().checkCondition("MoveUp")) {
                direction = direction.add(Vector2.up());
            } else if (this.getLevel().checkCondition("MoveDown")) {
                direction = direction.add(Vector2.down());
            }

            this.manager.attack(direction);
        }
    }

    /**
     * <p>
     * Restarts an existing timer or adds it if it does not exist.
     * </p>
     *
     * @param name  the timer name
     * @param timer the timer implementation
     * @see CustomTimerImpl
     */
    private void restartOrAddTimer(final String name, final CustomTimerImpl timer) {
        final var tm = super.getLevel().getTimerManager();
        if (tm.searchTimer(name)) {
            tm.restartTimer(name);
        } else {
            tm.addTimer(name, timer);
        }
    }

    /**
     * <p>
     * Checks if any special attack direction has been input.
     * </p>
     *
     * @return true if a special attack direction was triggered
     */
    private boolean spAtkCalled() {
        return this.getLevel().checkCondition("SpecialAttack")
                && (this.getLevel().checkCondition("MoveRight")
                        || this.getLevel().checkCondition("MoveLeft")
                        || this.getLevel().checkCondition("MoveUp")
                        || this.getLevel().checkCondition("MoveDown"));
    }
}
