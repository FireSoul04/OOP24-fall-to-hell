package it.unibo.falltohell.model.impl.gameobjects.movable.entity.character;

import it.unibo.falltohell.model.api.Level;
import it.unibo.falltohell.model.api.abilities.passive.StatisticPassiveAbility;
import it.unibo.falltohell.model.api.gameobjects.movable.entity.statistic.CharacterStatistics;
import it.unibo.falltohell.model.impl.GameEventManager;
import it.unibo.falltohell.model.impl.abilities.AbilityFactoryImpl;
import it.unibo.falltohell.model.impl.gameobjects.movable.entity.BaseCharacter;
import it.unibo.falltohell.model.impl.gameobjects.movable.entity.StatisticFactoryImpl;
import it.unibo.falltohell.util.Dimensions;
import it.unibo.falltohell.util.Vector2;

public class Druid extends BaseCharacter {

    private final CharacterStatistics stats;
    final private AbilityFactoryImpl factory = new AbilityFactoryImpl();
    final private StatisticPassiveAbility sPa;
    final private GameEventManager<String> input = super.getLevel().getGameEventManager();
    private int kills = 0;

    public Druid(final Level level, final Vector2 position) {
        super(level, position, new StatisticFactoryImpl().createCharacterStatistic(10, 10, new Vector2(10, 10),
                new Dimensions(10, 10), 10, 10));
        this.stats = (CharacterStatistics) super.getStats();

        this.sPa = this.factory.createPassiveAbility(this, (character) -> {
            final double[][] lifeManaGains = {
                {}, // 0 kill
                {0.10, 0.0}, // 1 kill
                {0.15, 0.0}, // 2 kills
                {0.20, 0.10}, // 3 kills
                {0.25, 0.15}, // 4 kills
                {0.30, 0.20}  // 5 kills
            };

            if (this.kills >= 1 && this.kills <= 5) {
                double lifeGain = stats.getFullLife() * lifeManaGains[this.kills][0];
                double manaGain = stats.getInitialMana() * lifeManaGains[this.kills][1];

                stats.setLife(Math.min(stats.getLife() + lifeGain, stats.getFullLife()));
                if (manaGain > 0) {
                    stats.setMana(Math.min(stats.getMana() + manaGain, stats.getInitialMana()));
                }

                if (this.kills == 5) this.setZeroKill();
            }
        });
    }

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

}
