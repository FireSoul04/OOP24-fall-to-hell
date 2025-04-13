package it.unibo.falltohell.model.api.gameobjects.movable;

import it.unibo.falltohell.model.api.gameobjects.Movable;

public interface Projectile extends Movable {
    
    /** 
     * @return
     */
    boolean isHit();
}
