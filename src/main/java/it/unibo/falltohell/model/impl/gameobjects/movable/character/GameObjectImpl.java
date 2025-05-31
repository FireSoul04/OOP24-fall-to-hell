package it.unibo.falltohell.model.impl.gameobjects.movable.character;

import it.unibo.falltohell.model.api.GameObject;
import it.unibo.falltohell.model.api.physics.Collider;
import it.unibo.falltohell.model.util.Vector2;
import it.unibo.falltohell.model.api.Level;

public class GameObjectImpl implements GameObject {
    private Vector2 pos;
    private double width;
    private double height;
    private boolean isSolid;
    private double widthSize;
    private double heightSize;
    private Collider collider;

    public GameObjectImpl(Level level, Vector2 position, double width, double height, Collider collider) {
        this.pos = position;
        this.width = width;
        this.height = height;
        this.isSolid = true; // Default
        this.widthSize = width * GameObject.TILE_SIZE;
        this.heightSize = height * GameObject.TILE_SIZE;
        this.collider = collider;
        level.addGameObject(this);
    }
    public GameObjectImpl(Level level, Vector2 position, double width, double height,boolean isSolid,Collider collider) {
        this.pos = position;
        this.width = width;
        this.height = height;
        this.isSolid = isSolid; 
        this.widthSize = width * GameObject.TILE_SIZE;
        this.heightSize = height * GameObject.TILE_SIZE;
        this.collider = collider;
        level.addGameObject(this);
    }
    public Vector2 getPosition(){
        return this.pos;
    }
    public double getWidth(){
        return this.width;
    }
    public double getHeight(){
        return this.height;
    }
    public boolean isSolid(){
        return this.isSolid;
    }
    public double getWidthSize() {
        return this.widthSize;
    }
    
    public double getHeightSize() {
        return this.heightSize;
    }
    
    public void setPosition(Vector2 position) {
        this.pos = position;
    }
    public Collider getCollider(){
        return this.collider;
    }
    public void onCollision(GameObject other){
        
    }
}
