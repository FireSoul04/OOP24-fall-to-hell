package it.unibo.falltohell.model.impl;

import it.unibo.falltohell.model.api.GameObject;
import it.unibo.falltohell.model.api.physics.Collider;
import it.unibo.falltohell.util.Vector2;
import it.unibo.falltohell.model.api.Level;
/**
 * Default implementation of the {@link GameObject} interface.
 * <p>
 * Represents a generic object in the game world, with position, size, solidity,
 * collider, and a reference to the level it belongs to. Upon creation, the object
 * is automatically added to the specified level.
 * </p>
 */
public class GameObjectImpl implements GameObject {
    private Vector2 pos;
    private double width;
    private double height;
    private boolean isSolid;
    private double widthSize;
    private double heightSize;
    private Collider collider;
    private Level level;
    
    /**
     * Constructs a solid GameObject and adds it to the specified level.
     *
     * @param lv the level to which this object will be added
     * @param position the position of the object
     * @param width the width (in tiles) of the object
     * @param height the height (in tiles) of the object
     * @param collider the collider for this object
     */
    public GameObjectImpl(Level lv, Vector2 position, double width, double height, Collider collider) {
        this.pos = position;
        this.width = width;
        this.height = height;
        this.isSolid = true; // Default
        this.widthSize = width * GameObject.TILE_SIZE;
        this.heightSize = height * GameObject.TILE_SIZE;
        this.collider = collider;
        lv.addGameObject(this);
        this.level = lv;
    }
    /**
     * Constructs a GameObject with a specified solidity and adds it to the specified level.
     *
     * @param lv the level to which this object will be added
     * @param position the position of the object
     * @param width the width (in tiles) of the object
     * @param height the height (in tiles) of the object
     * @param isSolid whether the object is solid
     * @param collider the collider for this object
     */
    public GameObjectImpl(Level lv, Vector2 position, double width, double height, boolean isSolid, Collider collider) {
        this.pos = position;
        this.width = width;
        this.height = height;
        this.isSolid = isSolid; 
        this.widthSize = width * GameObject.TILE_SIZE;
        this.heightSize = height * GameObject.TILE_SIZE;
        this.collider = collider;
        lv.addGameObject(this);
        this.level = lv;
    }
    /**
     * {@inheritDoc}
     */
    public Vector2 getPosition(){
        return this.pos;
    }
     /**
     * {@inheritDoc}
     */
    public double getWidth(){
        return this.width;
    }
     /**
     * {@inheritDoc}
     */
    public double getHeight(){
        return this.height;
    }
     /**
     * {@inheritDoc}
     */
    public boolean isSolid(){
        return this.isSolid;
    }
     /**
     * {@inheritDoc}
     */
    public double getWidthSize() {
        return this.widthSize;
    }
     /**
     * {@inheritDoc}
     */
    public double getHeightSize() {
        return this.heightSize;
    }
     /**
     * {@inheritDoc}
     */
    public void setPosition(Vector2 position) {
        this.pos = position;
    }
     /**
     * {@inheritDoc}
     */
    public Collider getCollider(){
        return this.collider;
    }
     /**
     * {@inheritDoc}
     */
    public void onCollision(GameObject other){
        
    }
     /**
     * {@inheritDoc}
     */
    public void onCollision(GameObject other, Vector2 direction){

    }
    /**
     * {@inheritDoc}
     */
    public void onCollisionExit(GameObject other, Vector2 direction){

    }
    /**
     * {@inheritDoc}
     */
    public Level getLevel(){
        return this.level;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update() {

    }
}
