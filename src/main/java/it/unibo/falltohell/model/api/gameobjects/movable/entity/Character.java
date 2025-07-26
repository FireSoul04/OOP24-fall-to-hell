package it.unibo.falltohell.model.api.gameobjects.movable.entity;

import it.unibo.falltohell.model.api.gameobjects.movable.Entity;
import it.unibo.falltohell.model.api.gameobjects.movable.entity.statistic.buff.BuffManager;
import it.unibo.falltohell.model.api.gameobjects.movable.entity.weapons.Weapon;

import java.util.Optional;

/**
 * Interface for a character.
 *
 * @author Davide Mancini
 */
public interface Character extends Entity {

    /**
     * Identifier for the type of character.
     */
    enum CharacterID {
        /**
         * Rogue character.
         */
        ROGUE,
        /**
         * Caster character.
         */
        CASTER,
        /**
         * Archer character.
         */
        ARCHER,
        /**
         * Druid character.
         */
        DRUID,
    }

    /**
     * If character is touching an interactable object, it can interact with it.
     */
    void interact();

    /**
     * Attack with the weapon.
     */
    void attack();

    /**
     * @return the equipped weapon if present
     */
    Optional<Weapon> getEquippedWeapon();

    /**
     * @param weapon to equip
     */
    void equipWeapon(Weapon weapon);

    /**
     * @return this character id
     */
    CharacterID getCharacterID();

    /**
     * @return buff manager of the character
     */
    BuffManager getBuffManager();
}
