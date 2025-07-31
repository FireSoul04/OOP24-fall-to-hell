package it.unibo.falltohell.test.util.debug;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
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

/**
 * A builder class for creating {@link DropDebug} objects in a debug context.
 *
 * <p>This class uses a fluent interface, allowing calls to be chained:
 * <pre>{@code
 * DropDebug drop = new DropBuilderDebug()
 *     .withLevel(level)
 *     .withPosition(new Vector2(10, 20))
 *     .withBuff(BuffNames.ATTACK, stats, 1.5)
 *     .build();
 * }</pre>
 *
 * <p>To build a drop, the following parameters must be set:
 * <ul>
 *   <li>{@link #withLevel(Level)} — the level where the drop will be placed</li>
 *   <li>{@link #withPosition(Vector2)} — the position of the drop</li>
 *   <li>{@link #withBuff(BuffNames, CharacterStatistics, double)} — the type of buff this drop grants</li>
 * </ul>
 *
 * <p>If any of these parameters are missing, {@link #build()} will throw an {@link IllegalStateException}.
 *
 * @author Sara Visani
 */
public class DropBuilderDebug {

    private Level level;
    private Vector2 position;
    private Buff buff;
    private BuffNames type;

    /**
     * Sets the {@link Level} in which the drop will exist.
     *
     * @param level the game level context
     * @return this builder for chaining
     */
    @SuppressFBWarnings(value = "EI_EXPOSE_REP2",
    justification = "Level is managed externally and safe to reference directly in debug mode")
    public DropBuilderDebug withLevel(final Level level) {
        this.level = level;
        return this;
    }

    /**
     * Sets the position of the drop.
     *
     * @param position a {@link Vector2} indicating the drop coordinates
     * @return this builder for chaining
     */
    public DropBuilderDebug withPosition(final Vector2 position) {
        this.position = position;
        return this;
    }

    /**
     * Configures the buff that this drop will grant when collected.
     *
     * <p>This method automatically instantiates the appropriate
     * {@link Buff} implementation depending on the {@link BuffNames} type:
     * <ul>
     *   <li>{@link BuffNames#ATTACK} → {@link AttackBuff}</li>
     *   <li>{@link BuffNames#ATTACK_SPEED} → {@link AttackSpeedBuff}</li>
     *   <li>{@link BuffNames#LIFE} → {@link LifeBuff}</li>
     *   <li>{@link BuffNames#MANA} → {@link ManaBuff}</li>
     *   <li>{@link BuffNames#SPEED} → {@link SpeedBuff}</li>
     * </ul>
     *
     * @param type       the type of buff to create
     * @param stats      the character statistics affected by the buff
     * @param multiplier the multiplier applied to the buff effect
     * @return this builder for chaining
     */
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

    /**
     * Builds the {@link DropDebug} instance with the configured parameters.
     *
     * <p>All of the following must be set before calling this method:
     * <ul>
     *   <li>Level</li>
     *   <li>Position</li>
     *   <li>Buff</li>
     * </ul>
     *
     * @return a fully constructed {@link DropDebug} object
     * @throws IllegalStateException if any required parameter is missing
     */
    public DropDebug build() {
        if (level == null || position == null || buff == null) {
            throw new IllegalStateException("Level, position, and buff must be set before building DropImpl.");
        }
        return new DropDebug(level, position, buff, "drop.png", this.type);
    }
}
