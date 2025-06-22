package it.unibo.falltohell.model.impl.abilities;

import it.unibo.falltohell.model.api.abilities.passive.PassiveAbility;
import it.unibo.falltohell.model.api.abilities.passive.PassiveAbilityDo;
import it.unibo.falltohell.model.api.gameobjects.movable.entity.Character;

/**
 * Implementation of PassiveAbility
 * @author Sara Visani
 */
public class PassiveAbilityImpl implements PassiveAbility{

    final private Character character;
    final private PassiveAbilityDo event;

    /**
     * @param character refers to who holds this passive
     * @param lambda what needs to be done
     */
    public PassiveAbilityImpl(final Character character,final PassiveAbilityDo lambda){
        this.character = character;
        this.event = lambda;
    }

    /*
     * {@inheritDoc}
     */
    public void carryOut(){
        this.event.carryOut(this.character);
    }
}