package it.unibo.bubbleboop.model.api;

public interface GameObject {
    
    static final double TILE_SIZE = 20.0;
    
    /**
     * @return current position of this game object
     */
    Position getPosition();
}
