package it.unibo.falltohell.model.impl.gameobjects.movable.entity.enemy;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import it.unibo.falltohell.model.api.gameobjects.movable.entity.AggroListener;
import it.unibo.falltohell.model.api.gameobjects.movable.entity.Enemy;
import it.unibo.falltohell.model.impl.gameobjects.entrance.BaseEntrance;

public class ManagerIngage {

    private final Set<BaseEntrance> listEntrance = new HashSet<>();
    private final Set<Enemy> listEnemy = new HashSet<>();
    private AggroListener listener = () -> this.change();

    public void addEntrance(final BaseEntrance entrance){
        this.listEntrance.add(entrance);
        entrance.(listener);
    }

    public void addEnemy(final Enemy enemy){
        this.listEnemy.add(enemy);
    }

    public void removeEntrance(final BaseEntrance entrance){
        this.listEntrance.remove(entrance);
        entrance.(Optional.empty());
    }

    public void removeEnemy(final Enemy enemy){
        this.listEnemy.remove(enemy);
    }

    private void change() {
        this.listEnemy.forEach(e -> e.setIngage());
    }
}
