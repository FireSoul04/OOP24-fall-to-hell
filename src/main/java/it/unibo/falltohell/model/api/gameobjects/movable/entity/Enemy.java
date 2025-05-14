package it.unibo.falltohell.model.api.gameobjects.movable.entity;

import it.unibo.falltohell.model.api.Collider;
import it.unibo.falltohell.model.api.GameObject;
import it.unibo.falltohell.model.api.gameobjects.Movable;
import it.unibo.falltohell.model.util.Vector2;

public abstract class Enemy implements Movable {

    protected float life;
    protected float damage;
    protected double height;
    protected double width;
    protected double xVelocity;
    protected double yVelocity;
    final protected Vector2 initialPos;
    protected Vector2 position;
    protected double timeNoAggro = 0;
    protected double tileHeight;
    protected double tileWidth;

    public Enemy(Vector2 initialCord)
    {
        this.initialPos = initialCord;
        this.setPosition(this.initialPos);
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
    @Override
    public Vector2 getPosition() {
        return this.position;
    }
    @Override
    public void setPosition(Vector2 position) {
        this.position = position;
    }
    protected double getTimeNoAggro() {
        return timeNoAggro;
    }
    protected void setTimeNoAggro(double timeNoAggro) {
        this.timeNoAggro = timeNoAggro;
    }
    @Override
    public double getWidth() {
        return this.width;
    }
    @Override
    public double getHeight() {
        return this.height;
    }
    @Override
    public double getWidthSize() {
        return this.tileWidth;
    }
    @Override
    public double getHeightSize() {
        return this.tileHeight;
    }
    @Override
    public boolean isSolid(){
        return true;
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
    public abstract Collider getCollider();
    @Override
    public abstract void update(double deltaTime);
    @Override
    public abstract void onCollide(GameObject other);

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
