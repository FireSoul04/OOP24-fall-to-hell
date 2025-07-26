package it.unibo.falltohell.model.impl.gameobjects.movable.entity.character;

import it.unibo.falltohell.model.api.Level;
import it.unibo.falltohell.model.api.gameobjects.movable.entity.statistic.CharacterStatistics;
import it.unibo.falltohell.model.impl.gameobjects.movable.entity.BaseCharacter;
import it.unibo.falltohell.model.impl.gameobjects.movable.entity.StatisticFactoryImpl;
import it.unibo.falltohell.model.impl.gameobjects.movable.entity.weapons.Staff;
import it.unibo.falltohell.model.impl.gameobjects.movable.projectile.Fireball;
import it.unibo.falltohell.util.Dimensions;
import it.unibo.falltohell.util.Vector2;

public class Caster extends BaseCharacter {

    private static final double LIFE = 50;
    private static final double ATTACK = 20;
    private static final double ATTACK_SPEED = 8;
    private static final Vector2 SPEED = new Vector2(2, 2);
    private static final double MANA = 25;
    private static final double AMOUNT_MANA_NORMAL_ATTACK = 1;
    private static final double AMOUNT_MANA_SPECIAL_ATTACK = 20;
    private static final double AMOUNT_MANA_HEAL = 10;
    private static final CharacterStatistics STATISTICS = new StatisticFactoryImpl() //TODO --> si modificano se è costante? Mi sa va cambiato
            .createCharacterStatistic(LIFE, ATTACK, SPEED, new Dimensions(20,20), MANA, ATTACK_SPEED);
    private final Staff staff;

    /**
     * Base constructor for a new caster character.
     *
     * @param level where the caster actually is
     * @param position of the caster in the level
     */
    public Caster(final Level level, final Vector2 position) {
        super(level, position, STATISTICS, "caster.png");
        this.staff = new Staff(level, position, this);
    }

    @Override
    public CharacterID getCharacterID() {
        return CharacterID.CASTER;
    }

    public void attack() {
        if (STATISTICS.getMana() + STATISTICS.getTemporaryMana() >= AMOUNT_MANA_NORMAL_ATTACK) {
            
        } else {
            this.staff.attack();
        }
    }
}
