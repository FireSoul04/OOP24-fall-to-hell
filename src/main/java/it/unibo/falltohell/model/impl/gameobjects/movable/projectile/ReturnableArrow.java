package it.unibo.falltohell.model.impl.gameobjects.movable.projectile;

import java.util.Optional;

import it.unibo.falltohell.model.api.Drawable;
import it.unibo.falltohell.model.api.GameObject;
import it.unibo.falltohell.model.api.gameobjects.movable.entity.Enemy;
import it.unibo.falltohell.model.api.physics.Collider;
import it.unibo.falltohell.model.impl.gameobjects.movable.entity.character.Archer;
import it.unibo.falltohell.util.Vector2;
import it.unibo.falltohell.model.api.Level;
/**
 * A special projectile that can return to the archer after being fired.
 * 
 * Behavior:
 * - Initially behaves like a normal arrow: it can hit solid objects and enemies.
 * - When the return is activated via {@code startReturn()}, the arrow becomes non-solid,
 *   flies back toward the owner, and can hit enemies again during its return.
 * - Once it reaches the archer, it is removed from the level and ammo is restored.
 */
public class ReturnableArrow extends ProjectileImpl{

    private boolean returning = false;
    private final Archer owner;
    private final double originalSpeed;
     /**
     * Creates a new ReturnableArrow instance.
     *
     * @param level the level the arrow belongs to
     * @param position the initial position of the arrow
     * @param width the width of the arrow
     * @param height the height of the arrow
     * @param speedX the initial horizontal speed
     * @param speedY the initial vertical speed
     * @param collider the collider used for collisions
     * @param owner the archer who fired the arrow
     */
    public ReturnableArrow(Level level, Vector2 position, double speedX, double speedY, Collider collider, Archer owner) {
        super(level, position, speedX, speedY, collider);
        this.owner = owner;
        this.originalSpeed = Math.sqrt(speedX * speedX + speedY * speedY);
    }

    /**
     * Starts the return phase of the arrow.
     * The arrow becomes non-solid, resets hit state, and begins flying toward the owner.
     */
    public void startReturn() {
        this.returning = true;
        this.setSolid(false);  
        this.setHit(false);    
    }
     /**
     * @return true if the arrow is currently returning to the owner
     */
    public boolean isReturning() {
        return returning;
    }
    /**
     * Updates the arrow's state and position.
     * During the return phase, it flies manually toward the owner.
     * If close enough to the owner, it is destroyed and ammo is restored.
     *
     * @param deltaTime time since the last update
     */
    @Override
    public void update(double deltaTime) {
        if (isReturning()) {
            
            Vector2 direction = owner.getPosition().subtract(getPosition()).normalize();

            setSpeedX(direction.x() * originalSpeed);
            setSpeedY(direction.y() * originalSpeed);

            Vector2 displacement = new Vector2(getSpeedX(), getSpeedY()).multiply(deltaTime);
            setPosition(getPosition().add(displacement));

            if (getPosition().distance(owner.getPosition()) < 0.5) {
                owner.returnArrow(this);  
                destroy();               
            }
        } else if (!isHit()) {
            
            super.update(deltaTime);
        }
        
    }
     /**
     * Handles collisions based on arrow state.
     * - While going forward: stops on any solid object and calls base logic.
     * - While returning: only hits enemies (and does not stop).
     *
     * @param other the object the arrow collided with
     */
    @Override
    public void onCollision(GameObject other) {
        if (!returning && other != this && other.isSolid()) {
            super.onCollision(other);
            this.setHit(true);  
        } else if (returning && isEnemy(other)) {
            this.onProjectileHit(other); 
        }
    }
    /**
     * Checks if the given object is a valid enemy.
     *
     * @param obj the object to check
     * @return true if the object is an enemy and not the owner
     */
    private boolean isEnemy(GameObject obj) {
        return obj instanceof Enemy && obj != owner;
    }

    /**
     * Removes the arrow from the level.
     */
    public void destroy() {
        getLevel().removeGameObject(this);
    }
}

