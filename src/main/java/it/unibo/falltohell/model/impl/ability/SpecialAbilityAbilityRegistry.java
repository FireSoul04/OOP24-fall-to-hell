package it.unibo.falltohell.model.impl.ability;

import java.util.HashMap;
import java.util.Map;

import it.unibo.falltohell.model.api.ability.SpecialAbilityCreator;
import it.unibo.falltohell.model.api.ability.active.SpecialActiveAbility;
import it.unibo.falltohell.model.api.gameobject.movable.entity.character.Character;
import it.unibo.falltohell.model.impl.factory.AbilityFactoryImpl;

/**
 * Registry that maps each {@link Character} subclass to its corresponding
 * {@link SpecialAbilityCreator}.
 * <p>
 * This registry enables dynamic creation of {@link SpecialActiveAbility}
 * instances
 * based on the actual runtime class of a character.
 * It is used internally by the
 * {@link AbilityFactoryImpl}.
 * </p>
 *
 * @author Sara Visani
 */
public class SpecialAbilityAbilityRegistry {

    private final Map<Class<? extends Character>, SpecialAbilityCreator> registry = new HashMap<>();

    /**
     * Registers a {@link MethodPassiveAbilityCreator} for a specific subclass of
     * {@link Character}.
     * <p>
     *
     * @param characterClass the {@link Class} object representing the subclass of
     *                       {@link Character} to associate with the creator
     * @param creator        the {@link SpecialAbilityCreator} responsible for
     *                       generating the {@link SpecialActiveAbility}
     *                       for the given character class
     */
    public void register(final Class<? extends Character> characterClass, final SpecialAbilityCreator creator) {
        this.registry.put(characterClass, creator);
    }

    /**
     * Creates a {@link SpecialActiveAbility} instance for the given
     * {@link Character}, if supported.
     * <p>
     *
     * @param character the {@link Character} instance for which the
     *                  {@link SpecialActiveAbility} should be created
     * @return the {@link SpecialActiveAbility} associated with the character's
     *         class
     * @throws IllegalArgumentException if the character's class is not registered
     *                                  in this registry
     */
    public SpecialActiveAbility createAbility(final Character character) {
        final SpecialAbilityCreator creator = this.registry.get(character.getClass());
        if (creator == null) {
            throw new IllegalArgumentException("Unsupported character type: " + character.getClass().getSimpleName());
        }
        return creator.create(character);
    }
}
