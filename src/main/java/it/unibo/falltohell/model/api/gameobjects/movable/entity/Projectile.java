package it.unibo.falltohell.model.api.gameobjects.movable.entity;

import it.unibo.falltohell.model.api.gameobjects.Movable;

public interface Projectile extends Movable {
    
    /** 
     * @return
     */
    boolean isHit();
}
