package it.unibo.falltohell.model.api.gameobjects.movable.entity.statistic.builder;

import it.unibo.falltohell.model.api.Drawable;
import it.unibo.falltohell.model.api.Level;
import it.unibo.falltohell.model.api.gameobjects.movable.entity.statistic.CharacterStatistics;
import it.unibo.falltohell.model.impl.gameobjects.movable.entity.BaseEnemy.BuffNames;
import it.unibo.falltohell.model.impl.gameobjects.movable.entity.statistics.buff.DropImpl;
import it.unibo.falltohell.model.impl.gameobjects.movable.entity.statistics.builder.BuffBuilderImpl;
import it.unibo.falltohell.util.Vector2;

/**
 * Builder interface for creating {@link DropImpl} instances that apply a
 * specific
 * {@link it.unibo.falltohell.model.api.gameobjects.movable.entity.statistic.buff.Buff}
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
     * Sets the drawable for the drop.
     * <p>
     *
     * @param drawable the drawable to associate with the drop
     * @return this builder instance
     */
    BuffBuilderImpl withDrawable(Drawable drawable);

    /**
     * Builds the {@link DropImpl} instance.
     * <p>
     *
     * @return a new {@link DropImpl}
     * @throws IllegalStateException if required fields are not set
     */
    DropImpl build();
}
