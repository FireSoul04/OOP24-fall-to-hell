package it.unibo.falltohell.model.impl.gameobjects.movable.entity.enemy;

import it.unibo.falltohell.model.api.gameobjects.Block;
import it.unibo.falltohell.model.api.gameobjects.Merchant;
import it.unibo.falltohell.model.api.gameobjects.movable.entity.Character;
import it.unibo.falltohell.model.api.GameObject;
import it.unibo.falltohell.model.api.gameobjects.movable.entity.Enemy;
import it.unibo.falltohell.model.impl.physics.colliders.BoxCollider;
import it.unibo.falltohell.model.util.Dimensions;
import it.unibo.falltohell.model.util.Vector2;

/**
 * Implements abstract class Enemies, creates first type for enemies
 * @author Sara Visani
 */

public class Monster1 extends Enemy{
    private static final double HEIGHT=20;
    private static final double WIDTH=20;
    private static final double FULL_LIFE=20;
    private static final double DAMAGE=20;
    private static final double X_VEL=20;
    private static final double Y_VEL=20;
    private int direction = 1;

    public Monster1(final Vector2 initialCord, final Character character) {
        super(initialCord, character);
        super.setLife(FULL_LIFE);
        super.setHeight(HEIGHT);
        super.setWidth(WIDTH);
        super.setDamage(DAMAGE);
        super.setSpeedX(X_VEL);
        super.setSpeedY(Y_VEL);
        super.setCollider(new BoxCollider(Vector2.zero(),new Dimensions(WIDTH, HEIGHT)));
    }

    @Override
    public void update(final double deltaTime) {
        this.move(deltaTime);
    }

    @Override
    public void onCollision(final GameObject other) {
        if(other instanceof Character){
            ((Character)other).setDamadLife(DAMAGE);
        }
        if((other instanceof Block)||(other instanceof Merchant)){
            this.direction*=-1;
        }
    }

    @Override
    protected boolean isFull() {
        return super.getLife() == FULL_LIFE;
    }

    @Override
    protected void attack() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'attack'");
    }

    @Override
    protected void move(final double deltaTime) {
        super.setPosition(super.getPosition().add((new Vector2(deltaTime*X_VEL*this.direction, super.getPosition().y()))));
    }

}
