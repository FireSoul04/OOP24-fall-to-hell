package it.unibo.falltohell.model.impl.gameobjects.movable.character.entity.enemy;

import it.unibo.falltohell.model.api.GameObject;
import it.unibo.falltohell.model.api.gameobjects.movable.entity.Enemy;
import it.unibo.falltohell.model.impl.colliders.BoxCollider;
import it.unibo.falltohell.model.util.Dimensions;
import it.unibo.falltohell.model.util.Vector2;

public class Monster1 extends Enemy{
    private static final double HEIGHT=20;
    private static final double WIDTH=20;
    private static final double FULL_LIFE=20;
    private static final double DAMAGE=20;
    private static final double X_VEL=20;
    private static final double Y_VEL=20;

    public Monster1(Vector2 initialCord) {
        super(initialCord);
        super.setLife(FULL_LIFE);
        super.setHeight(HEIGHT);
        super.setWidth(WIDTH);
        super.setDamage(DAMAGE);
        super.setSpeedX(X_VEL);
        super.setSpeedY(Y_VEL);
        super.setCollider(new BoxCollider(Vector2.zero(),new Dimensions(WIDTH, HEIGHT)));
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
        return super.getLife() == FULL_LIFE;
    }

    @Override
    protected void attack() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'attack'");
    }

    @Override
    protected void move(double deltaTime) {
        super.setPosition(super.getPosition().add(new Vector2(deltaTime*X_VEL, super.getPosition().y())));
    }
}
