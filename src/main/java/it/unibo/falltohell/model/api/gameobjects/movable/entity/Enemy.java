package it.unibo.falltohell.model.api.gameobjects.movable.entity;

import it.unibo.falltohell.model.api.Collider;
import it.unibo.falltohell.model.api.GameObject;
import it.unibo.falltohell.model.api.gameobjects.Movable;
import it.unibo.falltohell.model.util.Vector2;

public abstract class Enemy implements Movable {

    private double life;
    private double damage;
    private double height;
    private double width;
    private double xVelocity;
    private double yVelocity;
    final private Vector2 initialPos;
    private Vector2 position;
    private double timeNoAggro = 0;
    private double tileHeight;
    private double tileWidth;
    private Collider collider;

    public Enemy(Vector2 initialCord)
    {
        this.initialPos = initialCord;
        this.setPosition(this.initialPos);
    }

    protected double getLife() {
        return this.life;
    }
    protected void setLife(double life) {
        this.life = life;
    }
    protected void addLife(double life) {
        this.life = this.life + life;
    }
    public double getDamage() {
        return this.damage;
    }
    protected void setDamage(double damage) {
        this.damage = damage;
    }
    protected Vector2 getInitialPos() {
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
    protected void setZeroTimeNoAggro() {
        this.timeNoAggro = 0;
    }
    protected void addTimeNoAggro(double timeNoAggro) {
        this.timeNoAggro = this.timeNoAggro + timeNoAggro;
    }
    protected void setWidth(double w){
        this.width = w;
        this.tileWidth = this.tileWidth * GameObject.TILE_SIZE;
    }
    @Override
    public double getWidth() {
        return this.width;
    }
    protected void setHeight(double h){
        this.height = h;
        this.tileHeight = this.tileHeight * GameObject.TILE_SIZE;
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
    protected void setCollider(Collider collider) {
        this.collider = collider;
    }
    @Override
    public Collider getCollider(){
        return this.collider;
    }

    @Override
    public abstract void update(double deltaTime);
    @Override
    public abstract void onCollision(GameObject other);

    /**
     * @return check if enemy is dead
     */
    protected boolean isDead(){
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
    protected abstract void move(double deltaTime);
}
