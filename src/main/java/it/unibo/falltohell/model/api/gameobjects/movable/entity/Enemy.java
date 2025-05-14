package it.unibo.falltohell.model.api.gameobjects.movable.entity;

import it.unibo.falltohell.model.api.gameobjects.Movable;
import it.unibo.falltohell.model.util.Vector2;

public abstract class Enemy implements Movable {

    protected float life;
    protected float damage;
    protected double xVelocity;
    protected double yVelocity;
    final protected Vector2 initialPos;
    protected Vector2 position;
    protected double timeNoAggro = 0;

    public Enemy(Vector2 initialCord)
    {
        this.initialPos = initialCord;
        this.position=this.initialPos;
    }

    protected float getLife() {
        return this.life;
    }
    protected void setLife(float life) {
        this.life = life;
    }
    protected void setDamage(float damage) {
        this.damage = damage;
    }
    protected Vector2 getInizialPos() {
        return this.initialPos;
    }
    public Vector2 getPosition() {
        return this.position;
    }
    public void setPosition(Vector2 position) {
        this.position = position;
    }
    public double getTimeNoAggro() {
        return timeNoAggro;
    }
    public void setTimeNoAggro(double timeNoAggro) {
        this.timeNoAggro = timeNoAggro;
    }

    @Override
    public double getSpeedX(){
        return this.xVelocity;
    }
    @Override
    public double getSpeedY(){
        return this.yVelocity;
    }
    @Override
    public void setSpeedX(double speedX){
        this.xVelocity=speedX;
    }
    @Override
    public void setSpeedY(double speedY){
        this.yVelocity=speedY;
    }
    @Override
    public abstract void update(double deltaTime);
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
    protected abstract void move(double deltaTime);
}
