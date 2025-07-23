package it.unibo.falltohell.model.impl.abilities.active;
import it.unibo.falltohell.model.api.abilities.active.SpecialActiveAbility;
import it.unibo.falltohell.model.api.gameobjects.movable.Projectile;
import it.unibo.falltohell.model.impl.gameobjects.movable.ReturnableArrow;
import it.unibo.falltohell.model.impl.gameobjects.movable.entity.character.Archer;

import java.util.ArrayList;

import it.unibo.falltohell.model.api.Level;
/**
 * A special ability that causes all arrows previously shot by the archer to return to them.
 * 
 * When activated, each {@link ReturnableArrow} in the archer's shot list begins flying back.
 * Arrows in return mode:
 * - become non-solid (pass through walls),
 * - can hit enemies during the return,
 * - replenish the archer's ammo when they reach him.
 */
public class ReturnArrowAbility implements SpecialActiveAbility {
    
    private final Archer archer;
    private final Level level;
    /**
     * Creates a new ReturnArrowAbility for a specific archer in a given level.
     *
     * @param archer the archer who can activate this ability
     * @param level the current game level
     */
    public ReturnArrowAbility(Archer archer, Level level) {
        this.archer = archer;
        this.level = level;
    }
    /**
     * Activates the ability: all non-returning arrows shot by the archer
     * start returning to their owner.
     *
     * Arrows already in return mode are ignored.
     */
    @Override
    public void activate() {
        for (Projectile arrow : new ArrayList<>(archer.getShotArrows())) {
            if (arrow instanceof ReturnableArrow r && !r.isReturning()) {
                r.startReturn();
            }
        }
    }

}
