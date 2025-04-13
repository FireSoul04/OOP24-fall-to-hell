package it.unibo.falltohell.model.api;

public interface GameData {
    
    /**
     * @param amount of points to be added
     */
    void addPoints(int amount);

    /**
     * @param amount of point to be removed
     */
    void removePoints(int amount);
    
    /**
     * @return the player current points
     */
    int getPoints();
}
