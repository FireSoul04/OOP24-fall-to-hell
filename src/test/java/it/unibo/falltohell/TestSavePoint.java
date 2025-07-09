package it.unibo.falltohell;

import it.unibo.falltohell.controller.api.SaveFileController;
import it.unibo.falltohell.controller.impl.SaveFileControllerImpl;
import it.unibo.falltohell.model.api.GameData;
import it.unibo.falltohell.model.api.gameobjects.Interactable;
import it.unibo.falltohell.model.impl.GameDataImpl;
import it.unibo.falltohell.model.impl.LevelImpl;
import it.unibo.falltohell.model.impl.SavePoint;
import it.unibo.falltohell.model.api.gameobjects.movable.entity.Character.CharacterID;
import it.unibo.falltohell.model.impl.physics.colliders.BoxCollider;
import it.unibo.falltohell.util.Dimensions;
import it.unibo.falltohell.util.Vector2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.util.List;

class TestSavePoint {
    private Interactable savePoint;
    private SaveFileController saveController;
    private GameData data;

    @BeforeEach
    void initialization() {
        this.data = new GameDataImpl(1000, CharacterID.CASTER);
        this.savePoint = new SavePoint(
            new LevelImpl(),
            Vector2.zero(),
            new BoxCollider(Vector2.zero(),new Dimensions(0,0)),
            data
        );
        this.saveController = new SaveFileControllerImpl(data);
    }

    @Test
    void TestIfSavesCorrectly() {
        this.savePoint.interact();
        try {
            final List<String> testData = this.saveController.read();
            assertEquals(testData, List.of("1000", CharacterID.CASTER.name()));
        } catch (IOException e) {
            //throw new RuntimeException(e);
        }
    }
}
