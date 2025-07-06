package it.unibo.falltohell.model.impl.gameobjects.movable.entity;

import it.unibo.falltohell.model.impl.gameobjects.movable.EntityImpl;
import it.unibo.falltohell.model.impl.physics.colliders.BoxCollider;
import it.unibo.falltohell.model.api.GameObject;
import it.unibo.falltohell.model.api.Level;
import it.unibo.falltohell.model.api.gameobjects.movable.entity.Character;
import it.unibo.falltohell.model.api.gameobjects.movable.entity.Enemy;
import it.unibo.falltohell.model.api.gameobjects.movable.entity.statistic.BaseEnemyStatistics;
import it.unibo.falltohell.model.util.Vector2;

/**
 * Abstract class for all Enemies, set the base enemy
 * @author Sara Visani
 */
public abstract class BaseEnemy extends EntityImpl implements Enemy {

    private BaseEnemyStatistics stats;

    public BaseEnemy(final Level level, final BaseEnemyStatistics stats)
    {
        super(level, stats.getInitialPos(), new BoxCollider(Vector2.zero(), stats.getDimensions()), stats);
        this.stats = (BaseEnemyStatistics)super.getStats();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setCharacter(final Character character) {
        this.stats.setCharacter(character);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setDamagedLife(final double damage){
        super.setDamagedLife(damage);
        this.stats.getTm().stopTimer(stats.getNo_aggroName());
        this.stats.getTm().restartTimer(stats.getNo_aggroName());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public abstract void update(final double deltaTime);

    /**
     * {@inheritDoc}
     */
    @Override
    public abstract void onCollision(final GameObject other, final Vector2 direction);
    
    /**
     * @return true if this enemy is full health, false when not
     */
    protected abstract boolean isFull();

    /**
     * type of attack of the enemy
     */
    protected abstract void attack();

    /**
     * characterizes the movement of the enemy
     * @param deltaTime elapsed time between the current frame and the last one
     */
    protected abstract void move(final double deltaTime);
}
