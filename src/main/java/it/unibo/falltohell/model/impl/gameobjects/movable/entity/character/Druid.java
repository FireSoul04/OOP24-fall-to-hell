package it.unibo.falltohell.model.impl.gameobjects.movable.entity.character;

import it.unibo.falltohell.model.api.Level;
import it.unibo.falltohell.model.api.abilities.passive.StatisticPassiveAbility;
import it.unibo.falltohell.model.api.gameobjects.movable.entity.statistic.CharacterStatistics;
import it.unibo.falltohell.model.impl.abilities.AbilityFactoryImpl;
import it.unibo.falltohell.model.impl.gameobjects.movable.entity.BaseCharacter;
import it.unibo.falltohell.model.impl.gameobjects.movable.entity.StatisticFactoryImpl;
import it.unibo.falltohell.util.Dimensions;
import it.unibo.falltohell.util.Vector2;

public class Druid extends BaseCharacter {

    private final CharacterStatistics stats;
    final private AbilityFactoryImpl factory = new AbilityFactoryImpl();
    final private StatisticPassiveAbility sPa;
    private int kills = 0;

    public Druid(final Level level, final Vector2 position) {
        super(level, position, new StatisticFactoryImpl().createCharacterStatistic(10, 10, new Vector2(10, 10),
                new Dimensions(10, 10), 10, 10));
        this.stats = (CharacterStatistics) super.getStats();

        this.sPa = this.factory.createPassiveAbility(this, (character) -> {
            switch (this.kills) {
                case 1:
                    this.stats.addLife(10 * 0.1);
                    break;
                case 2:
                    this.stats.addLife(10 * 0.15);
                    break;
                case 3:
                    this.stats.addLife(10 * 0.2);
                    this.stats.setMana(10 * 0.1);
                    break;
                case 4:
                    this.stats.addLife(10 * 0.25);
                    this.stats.setMana(10 * 0.15);
                    break;
                case 5:
                    this.stats.addLife(10 * 0.30);
                    this.stats.setMana(10 * 0.20);
                    this.setZeroKill();
                    break;
                default:
                    break;
            }
        });
    }

    @Override
    public CharacterID getCharacterID() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getCharacterID'");
    }

    public void addKill() {
        this.kills += 1;
        this.sPa.carryOut();
    }

    private void setZeroKill() {
        this.kills = 0;
    }

}
