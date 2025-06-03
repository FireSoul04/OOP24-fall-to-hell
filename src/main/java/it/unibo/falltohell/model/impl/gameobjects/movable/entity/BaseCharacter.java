package it.unibo.falltohell.model.impl.gameobjects.movable.entity;

import it.unibo.falltohell.model.api.GameObject;
import it.unibo.falltohell.model.api.gameobjects.Interactable;
import it.unibo.falltohell.model.api.gameobjects.movable.entity.Character;
import it.unibo.falltohell.model.api.physics.Collider;
import it.unibo.falltohell.model.util.Vector2;

// TODO remove the methods that needs to be inherited by movable when the interface gets an implementation

public abstract class BaseCharacter implements Character {

    private Vector2 position;
    private double life=100;

    public BaseCharacter(final Vector2 position) {
        this.position = position;
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
    public void setDamadLife(double damage) {
        this.life-=damage;
    }

    @Override
    public double getSpeedX() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public double getSpeedY() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public void setSpeedX(double speedX) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void setSpeedY(double speedY) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void update(double deltaTime) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public Collider getCollider() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public double getHeight() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public double getHeightSize() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public Vector2 getPosition() {
        return this.position;
    }

    @Override
    public double getWidth() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public double getWidthSize() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public boolean isSolid() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void onCollision(GameObject other) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void setPosition(Vector2 vector2) {
        // TODO Auto-generated method stub
        
    }
}
