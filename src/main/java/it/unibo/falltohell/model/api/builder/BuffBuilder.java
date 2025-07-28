package it.unibo.falltohell.model.api.builder;

import it.unibo.falltohell.model.api.level.Level;
import it.unibo.falltohell.model.api.statistic.CharacterStatistics;
import it.unibo.falltohell.model.api.buff.Buff;
import it.unibo.falltohell.model.impl.gameobject.movable.drop.DropImpl;
import it.unibo.falltohell.model.impl.gameobject.movable.entity.enemy.BaseEnemy.BuffNames;
import it.unibo.falltohell.model.impl.builder.BuffBuilderImpl;
import it.unibo.falltohell.util.Vector2;

/**
 * Builder interface for creating {@link DropImpl} instances that apply a
 * specific
 * {@link Buff}
 * to a character.
 * <p>
 *
 * @author Sara Visani
 * @see DropImpl
 * @see BuffNames
 * @see BuffBuilderImpl
 */
public interface BuffBuilder {

    /**
     * Sets the {@link Level} in which the drop will be spawned.
     * <p>
     *
     * @param level the level where the drop exists
     * @return this builder instance
     */
    BuffBuilderImpl withLevel(Level level);

    /**
     * Sets the initial {@link Vector2} position where the drop will be placed.
     * <p>
     *
     * @param position the spawn position of the drop
     * @return this builder instance
     */
    BuffBuilderImpl withPosition(Vector2 position);

    /**
     * Sets the buff to apply based on a {@link BuffType}, the character's stats,
     * and a multiplier.
     * <p>
     *
     * @param type       the type of buff
     * @param stats      the {@link CharacterStatistics} to apply the buff to
     * @param multiplier the strength multiplier
     * @return this builder instance
     */
    BuffBuilderImpl withBuff(BuffNames type, CharacterStatistics stats, double multiplier);

    /**
     * Builds the {@link DropImpl} instance.
     * <p>
     *
     * @return a new {@link DropImpl}
     * @throws IllegalStateException if required fields are not set
     */
    DropImpl build();
}
