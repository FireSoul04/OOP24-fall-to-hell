package main.java.it.unibo.bubbleboop.model.api;

import it.unibo.bubbleboop.model.api.GameObject;

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
