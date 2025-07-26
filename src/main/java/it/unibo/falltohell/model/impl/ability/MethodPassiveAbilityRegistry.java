package it.unibo.falltohell.model.impl.ability;

import java.util.HashMap;
import java.util.Map;

import it.unibo.falltohell.model.api.ability.MethodPassiveAbilityCreator;
import it.unibo.falltohell.model.api.ability.passive.MethodPassiveAbility;
import it.unibo.falltohell.model.api.gameobject.movable.entity.character.Character;
import it.unibo.falltohell.model.impl.factory.AbilityFactoryImpl;

/**
 * Registry that maps each {@link Character} subclass to its corresponding
 * {@link MethodPassiveAbilityCreator}.
 * <p>
 * This registry enables dynamic creation of {@link MethodPassiveAbility}
 * instances
 * based on the actual runtime class of a character.
 * It is used internally by the
 * {@link AbilityFactoryImpl}.
 * </p>
 *
 * @author Sara Visani
 */
public class MethodPassiveAbilityRegistry {

    private final Map<Class<? extends Character>, MethodPassiveAbilityCreator> registry = new HashMap<>();

    /**
     * Registers a {@link MethodPassiveAbilityCreator} for a specific subclass of
     * {@link Character}.
     * <p>
     *
     * @param characterClass the {@link Class} object representing the subclass of
     *                       {@link Character} to associate with the creator
     * @param creator        the {@link MethodPassiveAbilityCreator} responsible for
     *                       generating the {@link MethodPassiveAbility}
     *                       for the given character class
     */
    public void register(final Class<? extends Character> characterClass, final MethodPassiveAbilityCreator creator) {
        this.registry.put(characterClass, creator);
    }

    /**
     * Creates a {@link MethodPassiveAbility} instance for the given
     * {@link Character}, if supported.
     * <p>
     *
     * @param character the {@link Character} instance for which the
     *                  {@link MethodPassiveAbility} should be created
     * @return the {@link MethodPassiveAbility} associated with the character's
     *         class
     * @throws IllegalArgumentException if the character's class is not registered
     *                                  in this registry
     */
    public MethodPassiveAbility createAbility(final Character character) {
        final MethodPassiveAbilityCreator creator = this.registry.get(character.getClass());
        if (creator == null) {
            throw new IllegalArgumentException("Unsupported character type: " + character.getClass().getSimpleName());
        }
        return creator.create(character);
    }
}
