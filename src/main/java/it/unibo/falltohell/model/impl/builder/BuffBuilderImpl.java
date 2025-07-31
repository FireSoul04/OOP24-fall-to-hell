package it.unibo.falltohell.model.impl.builder;

import it.unibo.falltohell.model.api.level.Level;
import it.unibo.falltohell.model.api.statistic.CharacterStatistics;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import it.unibo.falltohell.model.api.buff.Buff;
import it.unibo.falltohell.model.api.builder.BuffBuilder;
import it.unibo.falltohell.model.impl.gameobject.movable.drop.DropImpl;
import it.unibo.falltohell.model.impl.gameobject.movable.entity.enemy.BaseEnemy.BuffNames;
import it.unibo.falltohell.model.impl.buff.AttackBuff;
import it.unibo.falltohell.model.impl.buff.AttackSpeedBuff;
import it.unibo.falltohell.model.impl.buff.LifeBuff;
import it.unibo.falltohell.model.impl.buff.ManaBuff;
import it.unibo.falltohell.model.impl.buff.SpeedBuff;
import it.unibo.falltohell.util.Vector2;

/**
 * Implementation of {@link BuffBuilder}, used to construct a {@link DropImpl}
 * that applies a specific {@link Buff} to a {@link CharacterStatistics}.
 *
 * <p>
 * This builder simplifies the process of creating and configuring drops
 * that carry stat-altering effects in the game world.
 * </p>
 *
 * @author Sara Visani
 * @see DropImpl
 * @see Buff
 * @see BuffNames
 * @see CharacterStatistics
 */
public class BuffBuilderImpl implements BuffBuilder {
    private Level level;
    private Vector2 position;
    private Buff buff;

    /**
     * {@inheritDoc}
     */
    @SuppressFBWarnings(
    value = "EI_EXPOSE_REP2",
    justification =
        "Level reference is intentionally stored in BuffBuilderImpl. "
        + "The builder pattern assumes external Level state management."
    )
    @Override
    public BuffBuilderImpl withLevel(final Level level) {
        this.level = level;
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BuffBuilderImpl withPosition(final Vector2 position) {
        this.position = position;
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BuffBuilderImpl withBuff(final BuffNames type, final CharacterStatistics stats, final double multiplier) {
        this.buff = switch (type) {
            case ATTACK -> new AttackBuff(stats, multiplier);
            case ATTACK_SPEED -> new AttackSpeedBuff(stats, multiplier);
            case LIFE -> new LifeBuff(stats, multiplier);
            case MANA -> new ManaBuff(stats, multiplier);
            case SPEED -> new SpeedBuff(stats, multiplier);
        };
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DropImpl build() {
        if (level == null || position == null || buff == null) {
            throw new IllegalStateException("Level, position, and buff must be set before building DropImpl.");
        }
        return new DropImpl(level, position, buff, "drop.png");
    }
}
