package it.unibo.falltohell.model.impl.gameobjects.movable.entity.character;

import it.unibo.falltohell.model.impl.gameobjects.movable.entity.BaseCharacter;
import it.unibo.falltohell.util.Vector2;

//TODO eliminate this class when character is implemented 

public class TestCharacter extends BaseCharacter{

    public TestCharacter(Vector2 position) {
        super(position);
    }

    @Override
    public CharacterID getCharacterID() {
        return null;
    }
}
