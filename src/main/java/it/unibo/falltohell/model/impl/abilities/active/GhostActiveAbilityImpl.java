package it.unibo.falltohell.model.impl.abilities.active;

import it.unibo.falltohell.model.api.abilities.active.GhostAbilityCreate;
import it.unibo.falltohell.model.api.abilities.active.GhostActiveAbility;
import it.unibo.falltohell.model.api.gameobjects.movable.entity.Character;

public class GhostActiveAbilityImpl implements GhostActiveAbility{

    private final GhostAbilityCreate obj;
    private final Character character;

    public GhostActiveAbilityImpl(final GhostAbilityCreate obj, final Character character){
        this.obj = obj;
        this.character = character;
    }

    public void action(){
        this.obj.create(this.character);
    }
}
