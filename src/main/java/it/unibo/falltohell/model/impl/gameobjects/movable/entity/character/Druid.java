package it.unibo.falltohell.model.impl.gameobjects.movable.entity.character;

import it.unibo.falltohell.model.api.Level;
import it.unibo.falltohell.model.api.abilities.passive.StatisticPassiveAbility;
import it.unibo.falltohell.model.api.gameobjects.movable.entity.statistic.CharacterStatistics;
import it.unibo.falltohell.model.impl.CustomTimerImpl;
import it.unibo.falltohell.model.impl.GameEventManager;
import it.unibo.falltohell.model.impl.abilities.AbilityFactoryImpl;
import it.unibo.falltohell.model.impl.gameobjects.movable.entity.BaseCharacter;
import it.unibo.falltohell.model.impl.gameobjects.movable.entity.StatisticFactoryImpl;
import it.unibo.falltohell.model.impl.gameobjects.movable.entity.weapons.WarScythe;
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

    private static final int END_KILL = 5;
    private static final int KILL_RESET = 10_000;
    private static final double CREATION_COST = 30;
    private static final double ATTACK_COST = 10;
    private final CharacterStatistics stats;
    private final AbilityFactoryImpl factory = new AbilityFactoryImpl();
    private final StatisticPassiveAbility sPa;
    private final GameEventManager<String> input = super.getLevel().getGameEventManager();
    private final ManagerFamiliars manager = new ManagerFamiliars();
    private final WarScythe weapon = new WarScythe(this.getLevel(), this.getPosition(), "");
    private int kills;
    private int passiveCycles = 1;
    private boolean canAttack = true;
    private boolean sAactive;

    /**
     * <p>
     * Constructs a new {@code Druid} character.
     * </p>
     *
     * @param level    the level this character belongs to
     * @param position the initial spawn position
     * @param fileName is the name of the image file associated to the druid
     */
    public Druid(final Level level, final Vector2 position, final String fileName) {
        super(level, position, new StatisticFactoryImpl().createCharacterStatistic(10, 10, new Vector2(10, 10),
                new Dimensions(10, 10), 10, 10), fileName);
        this.stats = (CharacterStatistics) super.getStats();

        this.sPa = this.factory.createPassiveAbility(this, (character) -> {
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
        this.restartOrAddTimer(resetTimerName, new CustomTimerImpl(KILL_RESET, () -> this.setZeroKill()));
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
        if (this.input.checkCondition("NormalAttack") && this.canAttack) {
            this.canAttack = false;
            this.restartOrAddTimer("Druid_Attack", new CustomTimerImpl(1000, () -> this.canAttack = true));
            this.weapon.attack();
        }
        if (this.input.checkCondition("SpecialAbility") && this.tryPayCost(CREATION_COST)) {
            this.sAactive = true;
            this.factory.createGhostActiveAbility(this.manager::createFamiliar, this).action();
        }
        if (this.sAactive && this.spAtkCalled() && this.manager.isFree() && this.tryPayCost(ATTACK_COST)) {
            Vector2 direction = Vector2.zero();

            if (this.input.checkCondition("SaAttackRight")) {
                direction = direction.add(Vector2.right());
            } else if (this.input.checkCondition("SaAttackLeft")) {
                direction = direction.add(Vector2.left());
            } else if (this.input.checkCondition("SaAttackUp")) {
                direction = direction.add(Vector2.up());
            } else if (this.input.checkCondition("SaAttackDown")) {
                direction = direction.add(Vector2.down());
            }

            this.manager.attack(direction);
        }
    }

    /**
     * <p>
     * Attempts to pay the mana cost using regular and temporary mana.
     * </p>
     *
     * @param cost the cost to pay
     * @return true if the cost was successfully paid, false otherwise
     */
    private boolean tryPayCost(final double cost) {
        if (this.stats.getMana() + this.stats.getTemporaryMana() - cost >= 0) {
            if (this.stats.getTemporaryMana() > 0) {
                final var remaining = cost - this.stats.getTemporaryMana();
                this.stats.setTemporaryMana(0);
                this.stats.subMana(remaining);
            } else {
                this.stats.subMana(cost);
            }
            return true;
        }
        return false;
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
        return this.input.checkCondition("SaAttackRight")
                || this.input.checkCondition("SaAttackLeft")
                || this.input.checkCondition("SaAttackUp")
                || this.input.checkCondition("SaAttackDown");
    }
}
