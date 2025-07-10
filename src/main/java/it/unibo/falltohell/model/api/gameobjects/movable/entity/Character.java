package it.unibo.falltohell.model.api.gameobjects.movable.entity;

import it.unibo.falltohell.model.api.gameobjects.Interactable;
import it.unibo.falltohell.model.api.gameobjects.movable.Entity;
import it.unibo.falltohell.model.api.gameobjects.movable.entity.statistic.buff.BuffManager;

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
     * @param interactable object to be interacted with
     */
    void interact(Interactable interactable);

    /**
     * @return this character id
     */
    CharacterID getCharacterID();

    /**
     * @return buff manager of the character
     */
    BuffManager getBuffManager();
}
