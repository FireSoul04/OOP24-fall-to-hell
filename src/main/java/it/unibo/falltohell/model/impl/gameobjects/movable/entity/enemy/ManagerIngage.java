package it.unibo.falltohell.model.impl.gameobjects.movable.entity.enemy;

import java.util.HashSet;
import java.util.Set;

import it.unibo.falltohell.model.api.gameobjects.movable.entity.Enemy;
import it.unibo.falltohell.model.impl.gameobjects.entrance.BaseEntrance;

public class ManagerIngage {

    private final Set<BaseEntrance> listEntrance = new HashSet<>();
    private final Set<Enemy> listEnemy = new HashSet<>();

    public void addEntrance(final BaseEntrance entrance){
        this.listEntrance.add(entrance);
        
    }

    public void addEnemy(final Enemy enemy){
        this.listEnemy.add(enemy);
    }
}
