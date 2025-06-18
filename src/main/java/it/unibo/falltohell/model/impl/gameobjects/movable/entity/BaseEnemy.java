package it.unibo.falltohell.model.impl.gameobjects.movable.entity;

import it.unibo.falltohell.model.impl.LevelImpl;
import it.unibo.falltohell.model.impl.TimerManagerImpl;
import it.unibo.falltohell.model.impl.gameobjects.MovableImpl;
import it.unibo.falltohell.model.impl.physics.colliders.BoxCollider;
import it.unibo.falltohell.model.api.GameObject;
import it.unibo.falltohell.model.api.TimerManager;
import it.unibo.falltohell.model.api.gameobjects.movable.entity.Character;
import it.unibo.falltohell.model.api.gameobjects.movable.entity.Enemy;
import it.unibo.falltohell.model.util.Dimensions;
import it.unibo.falltohell.model.util.Vector2;

/**
 * Abstract class for all Enemies, set the base enemy
 * @author Sara Visani
 */

public abstract class BaseEnemy extends MovableImpl implements Enemy {

    private double life;
    private double damage;
    final private Vector2 initialPos;
    private double timeNoAggro = 0;
    private Character character;
    private TimerManager tm = new TimerManagerImpl();
    private final String no_aggro = "no_aggro";


    public BaseEnemy(final Vector2 initialCord,final double width,final double height,final double speedX,final double speedY,final Character character)
    {
        super(new LevelImpl(), initialCord, width, height, speedX, speedY, new BoxCollider(Vector2.zero(), new Dimensions(width, height)));
        this.initialPos = initialCord;
        this.setPosition(this.initialPos);
        this.character = character;
    }

    @Override
    public double getLife() {
        return this.life;
    }
    @Override
    public void setDamagedLife(final double damage){
        this.life-=damage;
    }
    protected void setLife(final double life) {
        this.life = life;
    }
    protected void addLife(final double life) {
        this.life = this.life + life;
    }
    protected double getDamage() {
        return this.damage;
    }
    protected void setDamage(final double damage) {
        this.damage = damage;
    }
    protected Vector2 getInitialPos() {
        return this.initialPos;
    }
    protected double getTimeNoAggro() {
        return timeNoAggro;
    }
    protected void setZeroTimeNoAggro() {
        this.timeNoAggro = 0;
    }
    protected void addTimeNoAggro(final double timeNoAggro) {
        this.timeNoAggro = this.timeNoAggro + timeNoAggro;
    }
    protected final TimerManager getTm() {
        return tm;
    }
    protected String getNo_aggro() {
        return no_aggro;
    }
    @Override
    public boolean isSolid(){
        return true;
    }
    protected Character getCharacter() {
        return this.character;
    }
    @Override
    public void setCharacter(final Character character) {
        this.character = character;
    }

    @Override
    public abstract void update(final double deltaTime);
    @Override
    public abstract void onCollision(final GameObject other);
    @Override
    public boolean isDead(){
        if(this.life <= 0){
            return true;
        }
        return false;
    }
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
