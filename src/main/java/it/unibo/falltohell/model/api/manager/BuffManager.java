package it.unibo.falltohell.model.api.manager;

import it.unibo.falltohell.model.api.buff.Buff;

/**
 * Interface to handle the addition and removal of buffs to an entity.
 * @author Martina Malagoli
 */
public interface BuffManager {

    /**
     * Method to add the buff to the buff manager and start a timer to check.
     * @param buff to be added to the current character and to be handled by the buff manager
     * @param duration of the buff
     * @param name of the buff
     */
    void addBuff(Buff buff, long duration, String name);

    /**
     * Method that adds an infinite buff to the entity.
     * @param buff to be added indefinitely
     * @param name of the buff
     */
    void addInfiniteBuff(Buff buff, String name);

    /**
     * Removes an infinite buff from the active buffs of the entity.
     * @param name of the infinite buff to be removed
     */
    void removeInfiniteBuff(String name);

    /**
     * Method to remove all buffs from the entity.
     */
    void removeBuffs();

    /**
     * @param name of the buff to be searched
     * @return true if the buff is present in the active buffs of the entity
     */
    boolean searchBuff(String name);
}
