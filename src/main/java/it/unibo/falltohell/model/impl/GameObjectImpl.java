package it.unibo.falltohell.model.impl;

import it.unibo.falltohell.model.api.Collider;
import it.unibo.falltohell.model.api.GameObject;
import it.unibo.falltohell.model.util.Vector2;

public class GameObjectImpl implements GameObject {
    private Vector2 pos;
    private Collider collider;
    private double width;
    private double height;
    private boolean isSolid;
    private double widthSize;
    private double heightSize;

    public GameObjectImpl(Vector2 position, double width, double height) {
        this.pos = position;
        this.width = width;
        this.height = height;
        this.isSolid = true; // Default
        this.widthSize = width * GameObject.TILE_SIZE;
        this.heightSize = height * GameObject.TILE_SIZE;
    }
    public GameObjectImpl(Vector2 position, double width, double height, boolean isSolid, Collider collider) {
        this.pos = position;
        this.width = width;
        this.height = height;
        this.isSolid = isSolid; 
        this.widthSize = width * GameObject.TILE_SIZE;
        this.heightSize = height * GameObject.TILE_SIZE;
        this.collider = collider;
    }
    public Vector2 getPosition(){
        return this.pos;
    }

    @Override
    public Collider getCollider() {
        return this.collider;
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
    
    public void onCollide(GameObject other) {
        
    }

}
