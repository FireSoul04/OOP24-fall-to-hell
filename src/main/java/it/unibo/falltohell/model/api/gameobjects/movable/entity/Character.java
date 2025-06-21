package it.unibo.falltohell.model.api.gameobjects.movable.entity;

import it.unibo.falltohell.model.api.gameobjects.Interactable;
import it.unibo.falltohell.model.api.gameobjects.movable.Entity;

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
        ROGUE,
        CASTER,
        ARCHER,
        DRUID,
        FIGHTER
    }
    
    /**
     * @param interactable object to be interacted with
     */
    void interact(Interactable interactable);

    /**
     * @return this character id
     */
    CharacterID getCharacterID();
}
