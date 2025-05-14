package it.unibo.falltohell.model.impl.gameobjects.movable.character;

import it.unibo.falltohell.model.util.Vector2;
import it.unibo.falltohell.model.api.physics.Collider;
import it.unibo.falltohell.model.api.gameobjects.Interactable;
import it.unibo.falltohell.model.api.gameobjects.movable.entity.Character;

public class BaseCharacter extends MovableImpl implements Character {

    public BaseCharacter(Vector2 position, Collider collider) {
        super(position, 20, 20, 0, 0, collider);
    }

    @Override
    public void interact(Interactable interactable) {

    }
}
