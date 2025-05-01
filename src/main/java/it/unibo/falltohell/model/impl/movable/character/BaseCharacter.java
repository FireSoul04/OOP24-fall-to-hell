package it.unibo.falltohell.model.impl.movable.character;

import it.unibo.falltohell.model.impl.MovableImpl;
import it.unibo.falltohell.model.util.Vector2;
import it.unibo.falltohell.model.api.gameobjects.Interactable;
import it.unibo.falltohell.model.api.gameobjects.movable.Character;

public class BaseCharacter extends MovableImpl implements Character {

    public BaseCharacter(Vector2 position) {
        super(position, 20, 20, 20, 20);
    }

    @Override
    public void interact(Interactable interactable) {

    }
}
