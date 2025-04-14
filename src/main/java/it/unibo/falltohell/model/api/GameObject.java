package it.unibo.falltohell.model.api;

public interface GameObject {
    
    static final double TILE_SIZE = 20.0;
    
    /**
     * @return current position of this game object
     */
    Position getPosition();
    
    /**
     * @return the width of the game object
     */
    double getWidth();

    /**
     * @return the height of the game object
     */
    double getHeight();

    /**
     * @return true if the game object is solid, false otherwise
     */
    boolean isSolid();

    /**
     * @return the width of the game object in terms of tile size
     */
    double getWidthSize();

    /**
     * @return the height of the game object in terms of tile size
     */
    double getHeightSize();
}
