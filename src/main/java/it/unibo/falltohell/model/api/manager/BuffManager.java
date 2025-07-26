package it.unibo.falltohell.model.api.manager;

import it.unibo.falltohell.model.api.buff.Buff;

/**
 * Interface to handle the addition and removal of buffs to the character.
 * @author Martina Malagoli
 */
public interface BuffManager {

    /**
     * @param buff to be added to the current character and to be handled by the buff manager
     */
    void addBuff(Buff buff);

    /**
     * Method to remove all buffs from the character.
     */
    void removeBuffs();
}
