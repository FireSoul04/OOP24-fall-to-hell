package it.unibo.falltohell.model.impl;

import it.unibo.falltohell.model.api.Level;

import java.util.ArrayList;
import java.util.List;

import it.unibo.falltohell.model.impl.gameobjects.MovableImpl;
import it.unibo.falltohell.model.impl.physics.colliders.AABBCollisionsManager;
import it.unibo.falltohell.model.api.physics.CollisionsManager;
import it.unibo.falltohell.model.api.GameObject;

public class LevelImpl implements Level{
    private final List<GameObject> gameObjects;
    private final CollisionsManager collisionsManager;
    

    public LevelImpl(final List<GameObject> gameObjects) {
        this.gameObjects = gameObjects;
        this.collisionsManager = new AABBCollisionsManager();
    }
    public LevelImpl() {
        this.gameObjects = new ArrayList<>();
        this.collisionsManager = new AABBCollisionsManager();
    }
    public void addGameObject(GameObject gameObject) {
        this.gameObjects.add(gameObject);
    }
    public void removeGameObject(GameObject gameObject) {
        this.gameObjects.remove(gameObject);
    }
    public List<GameObject> getGameObject() {
        return new ArrayList<>(this.gameObjects);
    }
    public void update(double deltaTime){
        for(GameObject gameObject : this.gameObjects) {
            if(gameObject instanceof MovableImpl) {
                ((MovableImpl) gameObject).update(deltaTime);
            }
        }
        this.collisionsManager.checkCollisions(this.gameObjects);
        
    }

}
