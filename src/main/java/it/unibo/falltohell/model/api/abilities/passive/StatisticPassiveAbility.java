package it.unibo.falltohell.model.api.abilities.passive;

/**
 * Interface for a passive ability that interfere with the statistics of a character
 * @author Sara Visani
 */
public interface StatisticPassiveAbility extends PassiveAbility{

    /**
     * Execute the passive ability
     */
    public void carryOut();
}
