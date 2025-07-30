package it.unibo.falltohell.test.util.debug;

import it.unibo.falltohell.model.api.buff.Buff;
import it.unibo.falltohell.model.api.level.Level;
import it.unibo.falltohell.model.api.statistic.CharacterStatistics;
import it.unibo.falltohell.model.impl.buff.AttackBuff;
import it.unibo.falltohell.model.impl.buff.AttackSpeedBuff;
import it.unibo.falltohell.model.impl.buff.LifeBuff;
import it.unibo.falltohell.model.impl.buff.ManaBuff;
import it.unibo.falltohell.model.impl.buff.SpeedBuff;
import it.unibo.falltohell.model.impl.gameobject.movable.entity.enemy.BaseEnemy.BuffNames;
import it.unibo.falltohell.util.Vector2;

public class DropBuilderDebug {

    private Level level;
    private Vector2 position;
    private Buff buff;
    private BuffNames type;

    public DropBuilderDebug withLevel(final Level level) {
        this.level = level;
        return this;
    }

    public DropBuilderDebug withPosition(final Vector2 position) {
        this.position = position;
        return this;
    }

    public DropBuilderDebug withBuff(final BuffNames type, final CharacterStatistics stats, final double multiplier) {
        this.type = type;
        this.buff = switch (type) {
            case ATTACK -> new AttackBuff(stats, multiplier);
            case ATTACK_SPEED -> new AttackSpeedBuff(stats, multiplier);
            case LIFE -> new LifeBuff(stats, multiplier);
            case MANA -> new ManaBuff(stats, multiplier);
            case SPEED -> new SpeedBuff(stats, multiplier);
        };
        return this;
    }

    public DropDebug build() {
        if (level == null || position == null || buff == null) {
            throw new IllegalStateException("Level, position, and buff must be set before building DropImpl.");
        }
        return new DropDebug(level, position, buff, "drop.png", this.type);
    }
}
