package it.unibo.falltohell.model.api;

public interface GameData {
    
    /**
     * @param amount of points to be added
     */
    void addPoints(long amount);

    /**
     * @param amount of point to be removed
     */
    void removePoints(long amount);
    
    /**
     * @return the player current points
     */
    long getPoints();

    /**
     * @param newCharacter to be changed into
     */
    void changeCurrentCharacter(Character newCharacter);
}
