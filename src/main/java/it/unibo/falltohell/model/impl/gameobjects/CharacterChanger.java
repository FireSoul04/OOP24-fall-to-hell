package it.unibo.falltohell.model.impl.gameobjects;

import it.unibo.falltohell.model.api.Level;
import it.unibo.falltohell.model.api.gameobjects.Interactable;
import it.unibo.falltohell.model.api.gameobjects.movable.entity.Character;
import it.unibo.falltohell.model.api.gameobjects.movable.entity.Character.CharacterID;
import it.unibo.falltohell.model.api.physics.Collider;
import it.unibo.falltohell.model.impl.GameObjectImpl;
import it.unibo.falltohell.util.Vector2;

import java.util.Arrays;
import java.util.Map;
import java.util.List;

/**
 * Class that represents the object that permits to change the current character when the player
 * interacts with it.
 * @author Martina Malagoli
 */
public class CharacterChanger extends GameObjectImpl implements Interactable {

    private final Map<CharacterID, Character> characters;
    private final List<CharacterID> characterIDs;

    /**
     * Initialization of the CharacterChanger class.
     * @param lv is the current level
     * @param position is the position of this object in the level
     * @param width
     * @param height
     * @param collider is the collider associated with this object
     * @param characters is the map of all characters in the game
     */
    public CharacterChanger(final Level lv, final Vector2 position, final Collider collider,
        final Map<CharacterID, Character> characters) {
        super(lv, position, collider);
        this.characters = characters;
        this.characterIDs = Arrays.asList(CharacterID.values());
    }

    /**
     * {@inheritDoc}
     * Method that changes the current character with the next one in line.
     * @param character is the current character who interacts with this object
     */
    @Override
    public void interact(final Character character) {
        final CharacterID newCharacterID = characterIDs.get(
            (character.getCharacterID().ordinal() + 1) % characterIDs.size()
        );
        this.getLevel().getGameData().changeCurrentCharacter(this.characters.get(newCharacterID));
    }
}
