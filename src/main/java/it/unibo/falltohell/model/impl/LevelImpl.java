package it.unibo.falltohell.model.impl;

import it.unibo.falltohell.model.api.Level;
import it.unibo.falltohell.model.impl.gameobjects.movable.character.MovableImpl;

import java.util.ArrayList;
import java.util.List;
import it.unibo.falltohell.view.api.GameWindow;
import it.unibo.falltohell.model.api.GameObject;

public class LevelImpl implements Level{
    private final List<GameObject> gameObjects;
    private final GameWindow view;

    public LevelImpl(final List<GameObject> gameObjects, final GameWindow view) {
        this.gameObjects = gameObjects;
        this.view = view;
    }
    public LevelImpl(final GameWindow view) {
        this.gameObjects = new ArrayList<>();
        this.view = view;
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
                ((MovableImpl) gameObject).move(deltaTime);
            }
        }
        this.view.render();
    }

}
