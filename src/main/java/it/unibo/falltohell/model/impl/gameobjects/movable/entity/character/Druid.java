package it.unibo.falltohell.model.impl.gameobjects.movable.entity.character;

import it.unibo.falltohell.model.api.Level;
import it.unibo.falltohell.model.api.abilities.passive.StatisticPassiveAbility;
import it.unibo.falltohell.model.api.gameobjects.movable.entity.statistic.CharacterStatistics;
import it.unibo.falltohell.model.impl.CustomTimerImpl;
import it.unibo.falltohell.model.impl.GameEventManager;
import it.unibo.falltohell.model.impl.abilities.AbilityFactoryImpl;
import it.unibo.falltohell.model.impl.gameobjects.movable.entity.BaseCharacter;
import it.unibo.falltohell.model.impl.gameobjects.movable.entity.StatisticFactoryImpl;
import it.unibo.falltohell.util.Dimensions;
import it.unibo.falltohell.util.Vector2;

public class Druid extends BaseCharacter {

    private static final double CREATION_COST = 30;
    private static final double ATTACK_COST = 10;
    private final CharacterStatistics stats;
    final private AbilityFactoryImpl factory = new AbilityFactoryImpl();
    final private StatisticPassiveAbility sPa;
    final private GameEventManager<String> input = super.getLevel().getGameEventManager();
    final private ManagerFamiliars manager = new ManagerFamiliars();
    private int kills = 0;
    private boolean canAttack = true;
    private boolean SaActive = false;

    public Druid(final Level level, final Vector2 position) {
        super(level, position, new StatisticFactoryImpl().createCharacterStatistic(10, 10, new Vector2(10, 10),
                new Dimensions(10, 10), 10, 10));
        this.stats = (CharacterStatistics) super.getStats();

        this.sPa = this.factory.createPassiveAbility(this, (character) -> {
            final double[][] lifeManaGains = {
                    {}, // 0 kill
                    { 0.10, 0.0 }, // 1 kill
                    { 0.15, 0.0 }, // 2 kills
                    { 0.20, 0.10 }, // 3 kills
                    { 0.25, 0.15 }, // 4 kills
                    { 0.30, 0.20 } // 5 kills
            };

            if (this.kills >= 1 && this.kills <= 5) {
                double lifeGain = stats.getFullLife() * lifeManaGains[this.kills][0];
                double manaGain = stats.getInitialMana() * lifeManaGains[this.kills][1];

                stats.setLife(Math.min(stats.getLife() + lifeGain, stats.getFullLife()));
                if (manaGain > 0) {
                    stats.setMana(Math.min(stats.getMana() + manaGain, stats.getInitialMana()));
                }

                if (this.kills == 5)
                    this.setZeroKill();
            }

            this.manager.setNoFamiliarsCallback(() -> this.setSaActive(false));
        });
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

    public void addKill() {
        this.kills += 1;
        this.sPa.carryOut();
    }

    private void setZeroKill() {
        this.kills = 0;
    }

    /**
     * TODO
     */
    private void baseAttack() {

    }

    private void handleAttackInput() {
        if (this.input.checkCondition("NormalAttack") && this.canAttack) {
            this.canAttack = false;
            if (super.getLevel().getTimerManager().searchTimer("Druid_Attack")) {
                super.getLevel().getTimerManager().restartTimer("Druid_Attack");
            } else {
                super.getLevel().getTimerManager().addTimer("Druid_Attack",
                        new CustomTimerImpl(1000, () -> this.canAttack = true));
            }
            this.baseAttack();
        }
        if (this.input.checkCondition("SpecialAbility") && this.tryPayCost(CREATION_COST)) {
            this.SaActive = true;
            new AbilityFactoryImpl().createGhostActiveAbility(this.manager::createFamiliar, this).action();
        }
        if (this.SaActive && this.tryPayCost(ATTACK_COST)) {
            Vector2 direction = Vector2.zero();

            if (this.input.checkCondition("SaAttackRight")) {
                direction = direction.add(Vector2.right());
            }
            if (this.input.checkCondition("SaAttackLeft")) {
                direction = direction.add(Vector2.left());
            }
            if (this.input.checkCondition("SaAttackUp")) {
                direction = direction.add(Vector2.up());
            }
            if (this.input.checkCondition("SaAttackDown")) {
                direction = direction.add(Vector2.down());
            }

            if (!direction.equals(Vector2.zero())) {
                this.manager.attack(direction);
            }
        }
    }

    private boolean tryPayCost(final double cost) {
        if (this.stats.getMana() + this.stats.getTemporaryMana() - cost >= 0) {
            if (this.stats.getTemporaryMana() > 0) {
                var remaining = cost - this.stats.getTemporaryMana();
                this.stats.setTemporaryMana(0);
                this.stats.subMana(remaining);
            } else {
                this.stats.subMana(cost);
            }
            return true;
        }
        return false;
    }

    private void setSaActive(final boolean newState) {
        this.SaActive = newState;
    }
}
