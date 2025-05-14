package it.unibo.falltohell.model.impl.gameobjects.movable.character.Entity.Enemy;

import it.unibo.falltohell.model.api.Collider;
import it.unibo.falltohell.model.api.GameObject;
import it.unibo.falltohell.model.api.gameobjects.movable.entity.Enemy;
import it.unibo.falltohell.model.util.Vector2;

public class Monster1 extends Enemy{
    private static final double HEIGHT=20;
    private static final double WIDTH=20;
    private static final double FULL_LIFE=20;

    public Monster1(Vector2 initialCord) {
        super(initialCord);
    }

    @Override
    public double getWidth() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getWidth'");
    }

    @Override
    public double getHeight() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getHeight'");
    }

    @Override
    public double getWidthSize() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getWidthSize'");
    }

    @Override
    public double getHeightSize() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getHeightSize'");
    }

    @Override
    public Collider getCollider() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getCollider'");
    }

    @Override
    public void update(double deltaTime) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public void onCollide(GameObject other) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'onCollide'");
    }

    @Override
    protected boolean isFull() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'isFull'");
    }

    @Override
    protected void attack() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'attack'");
    }

    @Override
    protected void move(double deltaTime) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'move'");
    }

}
