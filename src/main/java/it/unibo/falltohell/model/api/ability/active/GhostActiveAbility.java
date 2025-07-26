package it.unibo.falltohell.model.api.ability.active;

/**
 * <p>
 * Represents a special type of {@link ActiveAbility} used to summon or control
 * ghost-based familiars.
 * </p>
 *
 * <p>
 * The {@code action()} method defines the specific behavior or effect that the
 * ghost ability performs,
 * such as creating or activating a familiar.
 * </p>
 *
 * @author Sara Visani
 * @see ActiveAbility
 */
public interface GhostActiveAbility extends ActiveAbility {

    /**
     * <p>
     * Executes the ghost ability's behavior, such as summoning a familiar
     * or triggering a ghost-specific effect.
     * </p>
     */
    void action();
}
