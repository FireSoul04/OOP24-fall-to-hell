package it.unibo.falltohell.model.impl.gameobjects.movable.character.entity.enemy;

import it.unibo.falltohell.model.api.Collider;
import it.unibo.falltohell.model.api.GameObject;
import it.unibo.falltohell.model.api.gameobjects.movable.entity.Enemy;
import it.unibo.falltohell.model.util.Vector2;

public class Monster1 extends Enemy{
    private static final double HEIGHT=20;
    private static final double WIDTH=20;
    private static final float FULL_LIFE=20;
    private static final float DAMAGE=20;
    private static final double X_VEL=20;
    private static final double Y_VEL=20;

    public Monster1(Vector2 initialCord) {
        super(initialCord);
        super.setLife(FULL_LIFE);
        super.height=HEIGHT;
        super.width=WIDTH;
        super.setDamage(DAMAGE);
        super.setSpeedX(X_VEL);
        super.setSpeedY(Y_VEL);
    }
    
    @Override
    public Collider getCollider() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getCollider'");
    }

    @Override
    public void update(double deltaTime) {
        this.move(deltaTime);
    }

    @Override
    public void onCollision(GameObject other) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'onCollide'");
    }

    @Override
    protected boolean isFull() {
        return this.life == FULL_LIFE;
    }

    @Override
    protected void attack() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'attack'");
    }

    @Override
    protected void move(double deltaTime) {
        super.setPosition(super.position.add(new Vector2(deltaTime*X_VEL, 0)));
    }

}
