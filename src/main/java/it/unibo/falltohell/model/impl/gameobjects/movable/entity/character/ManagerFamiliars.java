package it.unibo.falltohell.model.impl.gameobjects.movable.entity.character;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import it.unibo.falltohell.model.api.gameobjects.movable.entity.Character;
import it.unibo.falltohell.model.impl.CustomTimerImpl;
import it.unibo.falltohell.model.impl.gameobjects.movable.entity.weapons.FamiliarBat;
import it.unibo.falltohell.util.Vector2;

public class ManagerFamiliars {
    private List<FamiliarBat> list = new ArrayList<>();

    public void createFamiliar(final Character character){
        var familiar = new FamiliarBat(character);
        list.add(familiar);
        final String name = "Active-" + UUID.randomUUID();
        familiar.getLevel().getTimerManager().addTimer(name, new CustomTimerImpl(5000, () -> {
            this.list.remove(familiar);
            if(this.list.isEmpty())
            ((Druid)character).setSaActive(false);
        }));
    }

    public void removeFamiliar(final FamiliarBat familiar){
        if(familiar.canAttack()){
            this.list.remove(familiar);
        }else{
            //aspetta che finisca l'attacco prima di toglierlo
        }
    }

    public void attack(final Vector2 direction){
        this.list.stream()
                    .filter( f -> f.canAttack() )
                    .findFirst()
                    .ifPresent( f -> f.attack(direction));
    }
}
