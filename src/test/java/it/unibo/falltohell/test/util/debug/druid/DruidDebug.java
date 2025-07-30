package it.unibo.falltohell.test.util.debug.druid;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import it.unibo.falltohell.model.api.ability.passive.StatisticPassiveAbility;
import it.unibo.falltohell.model.api.level.Level;
import it.unibo.falltohell.model.api.statistic.CharacterStatistics;
import it.unibo.falltohell.model.impl.factory.AbilityFactoryImpl;
import it.unibo.falltohell.model.impl.factory.StatisticFactoryImpl;
import it.unibo.falltohell.model.impl.gameobject.movable.entity.character.BaseCharacter;
import it.unibo.falltohell.model.impl.gameobject.weapons.WarScythe;
import it.unibo.falltohell.model.impl.timer.CustomTimerImpl;
import it.unibo.falltohell.test.util.debug.manager.FamiliarManagerDebug;
import it.unibo.falltohell.util.Dimensions;
import it.unibo.falltohell.util.Vector2;

/**
 * Debug implementation of the {@link BaseCharacter} representing a Druid
 * character.
 * <p>
 * This class is used for testing purposes and includes additional debug-related
 * features such as kill tracking, passive ability cycles, and control over
 * special attacks.
 * </p>
 *
 * <p>
 * The Druid character has base statistics such as life, mana, speed, attack,
 * and
 * attack speed, and uses a {@link WarScythe} as its weapon.
 * </p>
 * <p>
 * It supports a passive ability that provides life and mana regeneration based
 * on
 * the number of kills, and a special ability to summon familiars.
 * </p>
 *
 * @author Sara Visani
 */
public class DruidDebug extends BaseCharacter {

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
    private final FamiliarManagerDebug manager = new FamiliarManagerDebug();
    private int kills;
    private int passiveCycles = 1;
    private boolean sAactive;
    private Vector2 direction;

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
    justification =
        "attack() is safe: fully initialized object; "
        + "no subclass state is accessed"
    )
    public DruidDebug(final Level level, final Vector2 position) {
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
        final String condition = "Test";
        final String move = "Test";
        super.update(deltaTime);
        this.handleAttackInput(condition, move);
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
    public void setZeroKill() {
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
     *
     * @param condition needed to activate special ability
     * @param move      needed to give the command to special ability
     */
    public void handleAttackInput(final String condition, final String move) {
        if ("SpecialAbility".equals(condition) && super.subManaIfEnough(CREATION_COST)) {
            this.sAactive = true;
            new AbilityFactoryImpl().createGhostActiveAbility(this.manager::createFamiliar, this).action();
        }

        if (this.sAactive && this.spAtkCalled(condition, move) && this.manager.isFree()
                && super.subManaIfEnough(ATTACK_COST)) {
            Vector2 direction = Vector2.zero();

            if ("MoveRight".equals(move)) {
                direction = direction.add(Vector2.right());
            } else if ("MoveLeft".equals(move)) {
                direction = direction.add(Vector2.left());
            } else if ("MoveUp".equals(move)) {
                direction = direction.add(Vector2.up());
            } else if ("MoveDown".equals(move)) {
                direction = direction.add(Vector2.down());
            }
            this.direction = direction;

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
    public void restartOrAddTimer(final String name, final CustomTimerImpl timer) {
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
     * @param condition needed to activate special ability
     * @param move      needed to give the command to special ability
     * @return true if a special attack direction was triggered
     */
    public boolean spAtkCalled(final String condition, final String move) {
        return "SpecialAttack".equals(condition)
                && ("MoveRight".equals(move)
                        || "MoveLeft".equals(move)
                        || "MoveUp".equals(move)
                        || "MoveDown".equals(move));
    }

    /**
     * Returns the number of kills after which passive ability resets.
     *
     * @return the max number of kills (END_KILL)
     */
    public static int getEndKill() {
        return END_KILL;
    }

    /**
     * Returns the duration in milliseconds before the kill count resets.
     *
     * @return the kill reset time in milliseconds
     */
    public static long getKillReset() {
        return KILL_RESET;
    }

    /**
     * Returns the mana cost for creating a familiar.
     *
     * @return the creation mana cost
     */
    public static double getCreationCost() {
        return CREATION_COST;
    }

    /**
     * Returns the mana cost for a special attack.
     *
     * @return the attack mana cost
     */
    public static double getAttackCost() {
        return ATTACK_COST;
    }

    /**
     * Returns the cooldown duration for attacks in milliseconds.
     *
     * @return the attack cooldown in milliseconds
     */
    public static long getAttackCooldown() {
        return ATTACK_COOLDOWN;
    }

    /**
     * Returns the passive ability instance.
     *
     * @return the {@link StatisticPassiveAbility} instance
     */
    public StatisticPassiveAbility getSpA() {
        return sPa;
    }

    /**
     * Returns the familiar manager responsible for managing familiars.
     *
     * @return the {@link FamiliarManagerDebug} instance
     */
    @SuppressFBWarnings(value = "EI_EXPOSE_REP", justification = "Manager is safe to expose for debug purposes")
    public FamiliarManagerDebug getManager() {
        return manager;
    }

    /**
     * Returns the current number of kills the druid has.
     *
     * @return the kill count
     */
    public int getKills() {
        return kills;
    }

    /**
     * Returns the current number of passive cycles completed.
     *
     * @return the number of passive cycles
     */
    public int getPassiveCycles() {
        return passiveCycles;
    }

    /**
     * Returns whether the special ability is currently active.
     *
     * @return {@code true} if special ability is active, {@code false} otherwise
     */
    public boolean isSaActive() {
        return sAactive;
    }

    /**
     * Returns the direction vector of the last special attack.
     *
     * @return the {@link Vector2} representing attack direction
     */
    public Vector2 getDirection() {
        return direction;
    }
}
