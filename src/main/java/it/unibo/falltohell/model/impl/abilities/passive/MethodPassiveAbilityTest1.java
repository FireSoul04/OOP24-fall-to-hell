package it.unibo.falltohell.model.impl.abilities.passive;

import it.unibo.falltohell.model.api.GameObject;
import it.unibo.falltohell.model.api.abilities.passive.MethodPassiveAbility;
import it.unibo.falltohell.model.api.gameobjects.movable.entity.Character;
import it.unibo.falltohell.model.util.Vector2;

/**
 * Implementation of {@link MethodPassiveAbility} representing a test passive
 * ability
 * linked to a specific {@link Character}.
 * <p>
 * This ability overrides the {@link #update(double)} and
 * {@link #onCollision(GameObject, Vector2)} methods
 * to provide custom behavior.
 * </p>
 * 
 * @author Sara Visani
 */
public class MethodPassiveAbilityTest1 implements MethodPassiveAbility {

    private final Character character;

    /**
     * Constructs a MethodPassiveAbilityTest1 for the specified {@link Character}.
     * <p>
     * 
     * @param character the {@link Character} instance this ability is associated
     *                  with
     */
    public MethodPassiveAbilityTest1(final Character character2) {
        this.character = character2;
    }

    /**
     * {@inheritDoc}
     * <p>
     * Custom update logic for the ability.
     * </p>
     * 
     * @param deltaTime time elapsed since last update
     */
    @Override
    public void update(double deltaTime) {
        System.out.println("Ability 1 updating for: " + character.getClass().getSimpleName());
    }

    /**
     * {@inheritDoc}
     * <p>
     * Custom logic executed on collision with another {@link GameObject}.
     * </p>
     * 
     * @param other    the other {@link GameObject} involved in the collision
     * @param position the position where the collision occurred
     */
    @Override
    public void onCollision(GameObject other, Vector2 position) {
        System.out.println("Ability 1 collision at " + position);
    }

}
