package it.unibo.falltohell.model.impl.gameobjects.movable.entity;

import it.unibo.falltohell.model.api.physics.Collider;
import it.unibo.falltohell.model.api.GameObject;
import it.unibo.falltohell.model.api.gameobjects.movable.entity.Character;
import it.unibo.falltohell.model.api.gameobjects.movable.entity.Enemy;
import it.unibo.falltohell.model.util.Vector2;

/**
 * Abstract class for all Enemies, set the base enemy
 * @author Sara Visani
 */

public abstract class BaseEnemy implements Enemy {

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
    private Character character;

    public BaseEnemy(final Vector2 initialCord,final Character character)
    {
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
    @Override
    public Vector2 getPosition() {
        return this.position;
    }
    @Override
    public void setPosition(final Vector2 position) {
        this.position = position;
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
    protected void setWidth(final double w){
        this.width = w;
        this.tileWidth = this.tileWidth * GameObject.TILE_SIZE;
    }
    @Override
    public double getWidth() {
        return this.width;
    }
    protected void setHeight(final double h){
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
    protected void setCollider(final Collider collider) {
        this.collider = collider;
    }
    @Override
    public Collider getCollider(){
        return this.collider;
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
