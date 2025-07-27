package it.unibo.falltohell.model.api.gameobject.movable.entity.character;

import it.unibo.falltohell.model.api.gameobject.movable.entity.Entity;
import it.unibo.falltohell.model.api.gameobject.weapon.Weapon;

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
     * Enable the current character as the player.
     */
    void enable();

    /**
     * Disable the current character.
     */
    void disable();

    /**
     * Adds mana to the character.
     * @param mana to add
     */
    void addMana(double mana);

    /**
     * Subtracts mana to the character if it has enough mana.
     * @param mana to remove
     * @return if character has enough mana
     */
    boolean subManaIfEnough(double mana);
}
