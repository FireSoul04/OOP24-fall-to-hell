package it.unibo.falltohell.model.impl.abilities.active;

import it.unibo.falltohell.model.api.abilities.active.GhostAbilityCreate;
import it.unibo.falltohell.model.api.abilities.active.GhostActiveAbility;
import it.unibo.falltohell.model.api.gameobjects.movable.entity.Character;

/**
 * Implementation of the {@link GhostActiveAbility} interface.
 * <p>
 * This ability triggers a ghost-related behavior through the {@link GhostAbilityCreate}
 * functional interface when its {@link #action()} method is called.
 * </p>
 * <p>
 * The behavior depends on the specific {@link Character} that owns or activates the ability.
 * </p>
 *
 * @see GhostAbilityCreate
 * @see GhostActiveAbility
 * @see Character
 *
 * @author Sara Visani
 */
public class GhostActiveAbilityImpl implements GhostActiveAbility {

    private final GhostAbilityCreate obj;
    private final Character character;

    /**
     * Constructs a new {@link GhostActiveAbilityImpl} with the given creator logic and character.
     *
     * @param obj       the {@link GhostAbilityCreate} responsible for defining the ability's behavior
     * @param character the {@link Character} that triggers or owns this ability
     */
    public GhostActiveAbilityImpl(final GhostAbilityCreate obj, final Character character) {
        this.obj = obj;
        this.character = character;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void action() {
        this.obj.create(this.character);
    }
}
