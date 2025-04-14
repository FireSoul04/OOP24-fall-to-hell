package it.unibo.falltohell.model.impl;

import it.unibo.falltohell.model.api.GameObject;
import it.unibo.falltohell.model.api.Position;

public class GameObjectImpl {
    private Position pos;
    private double width;
    private double height;
    private boolean isSolid;
    private double widthSize;
    private double heightSize;

    public GameObjectImpl(Position position, double width, double height) {
        this.pos = position;
        this.width = width;
        this.height = height;
        this.isSolid = true; // Default
        this.widthSize = width * GameObject.TILE_SIZE;
        this.heightSize = height * GameObject.TILE_SIZE;
    }
    public GameObjectImpl(Position position, double width, double height,boolean isSolid) {
        this.pos = position;
        this.width = width;
        this.height = height;
        this.isSolid = isSolid; 
        this.widthSize = width * GameObject.TILE_SIZE;
        this.heightSize = height * GameObject.TILE_SIZE;
    }
    public Position getPosition(){
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

}
