package it.unibo.falltohell.model.impl.gameobjects.movable.entity;

import it.unibo.falltohell.model.api.gameobjects.Interactable;
import it.unibo.falltohell.model.api.gameobjects.movable.entity.Character;
import it.unibo.falltohell.model.util.Vector2;

// TODO remove the methods that needs to be inherited by movable when the interface gets an implementation

public abstract class BaseCharacter implements Character {

    public BaseCharacter() {

    }

    @Override
    public void update(final double deltaTime) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'move'");
    }

    @Override
    public Vector2 getPosition() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getPosition'");
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
    public boolean isSolid() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'isSolid'");
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
    public void interact(final Interactable interactable) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'interact'");
    }

}
