package it.unibo.falltohell.model.impl.gameobjects.movable.entity.enemy;

import java.util.HashSet;
import java.util.Set;

import it.unibo.falltohell.model.api.gameobjects.movable.entity.AggroListener;
import it.unibo.falltohell.model.api.gameobjects.movable.entity.Enemy;
import it.unibo.falltohell.model.impl.gameobjects.entrance.BaseEntrance;

/**
 * A manager that coordinates engagement logic between {@link Enemy} entities
 * and {@link BaseEntrance} triggers via a shared {@link AggroListener}.
 * <p>
 * When a {@link BaseEntrance} triggers the shared listener, all registered enemies
 * toggle their engagement state using {@link Enemy#setIngage()}.
 *
 * @author Sara Visani
 * @see Enemy
 * @see BaseEntrance
 * @see AggroListener
 */
public class ManagerIngage {

    private final Set<BaseEntrance> listEntrance = new HashSet<>();
    private final Set<Enemy> listEnemy = new HashSet<>();
    private AggroListener listener = () -> this.listEnemy.forEach(e -> e.setIngage());

    /**
     * Registers a new {@link BaseEntrance} and returns the shared {@link AggroListener}
     * to be assigned to the entrance.
     *
     * @param entrance the entrance to be added
     * @return the shared {@link AggroListener} that toggles all enemy engagement states
     */
    public AggroListener addEntrance(final BaseEntrance entrance){
        this.listEntrance.add(entrance);
        return this.listener;
    }

    /**
     * Adds an {@link Enemy} to be affected by the listener.
     *
     * @param enemy the enemy to be added
     */
    public void addEnemy(final Enemy enemy){
        this.listEnemy.add(enemy);
    }

    /**
     * Removes a {@link BaseEntrance} from the manager.
     *
     * @param entrance the entrance to remove
     */
    public void removeEntrance(final BaseEntrance entrance){
        this.listEntrance.remove(entrance);
    }

    /**
     * Removes an {@link Enemy} from the manager.
     *
     * @param enemy the enemy to remove
     */
    public void removeEnemy(final Enemy enemy){
        this.listEnemy.remove(enemy);
    }
}
