package it.unibo.falltohell.model.api;

import java.util.List;

public interface Level {
    
    /**
     * @return the list of game objects currently present in the level
     */
    List<GameObject> getGameObject();

    /**
     * @param gameObject to be added in the level
     */
    void addGameObject(GameObject gameObject);

    /**
     * @param gameObject to be removed in the level
     */
    void removeGameObject(GameObject gameObject);
    
    /**
     * @param deltaTime 
     */
    void update(double deltaTime);
}
