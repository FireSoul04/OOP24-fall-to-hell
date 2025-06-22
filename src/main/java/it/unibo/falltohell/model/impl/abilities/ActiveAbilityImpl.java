package it.unibo.falltohell.model.impl.abilities;

import java.util.Optional;

import it.unibo.falltohell.model.api.GameObject;
import it.unibo.falltohell.model.api.Level;
import it.unibo.falltohell.model.api.abilities.ActiveAbility;
import it.unibo.falltohell.model.api.abilities.ActiveAbilityUpdate;
import it.unibo.falltohell.model.api.abilities.OptionalCollision;
import it.unibo.falltohell.model.api.gameobjects.movable.Projectile;
import it.unibo.falltohell.model.api.gameobjects.movable.entity.Character;
import it.unibo.falltohell.model.api.gameobjects.movable.entity.Enemy;
import it.unibo.falltohell.model.api.physics.Collider;
import it.unibo.falltohell.model.impl.gameobjects.MovableImpl;
import it.unibo.falltohell.model.util.Vector2;

public class ActiveAbilityImpl extends MovableImpl implements ActiveAbility{
    final double damage;
    final ActiveAbilityUpdate attack;
    final Level level;
    final Optional<OptionalCollision> collided;

    /**
     * @param level level where is it
     * @param position position of the cast
     * @param damage damage of the ability
     * @param collider collider of the ability
     * @param velocity Vector2(velocity X, velocity y)
     * @param attack lambda needed for the type of movement, attack. it has two parameters velocity and deltaTime
     * @param collided this lambda is optional. give optional null if you want standard implementation of OnCollision
     */
    public ActiveAbilityImpl(final Level level, final Vector2 position, final double damage, final Collider collider, final Vector2 velocity, final ActiveAbilityUpdate attack, final Optional<OptionalCollision> collided){
        super(level, position, 0,0, velocity.x(), velocity.y(), collider);
        this.damage = damage;
        this.attack = attack;
        this.level = level;
        this.collided = collided;
    }

    /**
     * Called when collided. Standard hits Monster and get stopped by blocks and other elements that arent Character or Projectile
     * If that isn't the case it use the implementation passed by the constructor
     * @param other gameobject collided with
     */
    @Override
    public void onCollision(final GameObject other){
        if(!this.collided.isPresent()){
            if(other instanceof Enemy){
                ((Enemy)other).setDamagedLife(this.damage);
                this.level.removeGameObject(this);
            }
            if(!(other instanceof Character && other instanceof Projectile)){
                this.level.removeGameObject(this);
            }
        }else{
            this.collided.get().collided(other);
        }
    }

    /**
     * lambda passed by in the constructor
     * @param deltaTime
     */
    @Override
    public void update(final double deltaTime){
        this.attack.attack(new Vector2(super.getSpeedX(), super.getSpeedY()), deltaTime);
    }
    
}
