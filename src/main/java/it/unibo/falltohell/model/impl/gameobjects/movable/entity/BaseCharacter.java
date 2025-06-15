package it.unibo.falltohell.model.impl.gameobjects.movable.entity;

import it.unibo.falltohell.model.api.Level;
import it.unibo.falltohell.model.api.gameobjects.Interactable;
import it.unibo.falltohell.model.api.gameobjects.movable.entity.Character;
import it.unibo.falltohell.model.impl.LevelImpl;
import it.unibo.falltohell.model.impl.gameobjects.MovableImpl;
import it.unibo.falltohell.model.impl.physics.colliders.BoxCollider;
import it.unibo.falltohell.model.util.Dimensions;
import it.unibo.falltohell.model.util.Vector2;

// TODO remove the methods that needs to be inherited by movable when the interface gets an implementation

public abstract class BaseCharacter extends MovableImpl implements Character {

    private double life=100;

    // TODO: It needs to use a real level where it should be added automatically
    public BaseCharacter(final Vector2 position) {
        super(new LevelImpl(), position, 0, 0, 0, 0, new BoxCollider(Vector2.zero(), new Dimensions(0, 0)));
    }

    @Override
    public void interact(Interactable interactable) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public double getLife() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public boolean isDead() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void setDamagedLife(double damage) {
        this.life-=damage;
    }
}
