package it.unibo.falltohell;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.falltohell.model.api.gameobjects.movable.Projectile;
import it.unibo.falltohell.model.api.gameobjects.movable.entity.statistic.CharacterStatistics;
import it.unibo.falltohell.model.impl.GameCameraImpl;
import it.unibo.falltohell.model.impl.LevelImpl;
import it.unibo.falltohell.model.impl.abilities.active.ReturnArrowAbility;
import it.unibo.falltohell.model.impl.gameobjects.movable.entity.TestStatsFactory;
import it.unibo.falltohell.model.impl.gameobjects.movable.entity.character.Archer;
import it.unibo.falltohell.model.impl.gameobjects.movable.entity.weapons.Bow;
import it.unibo.falltohell.model.impl.gameobjects.movable.projectile.ReturnableArrow;
import it.unibo.falltohell.model.impl.physics.colliders.BoxCollider;
import it.unibo.falltohell.util.Dimensions;
import it.unibo.falltohell.util.Vector2;

public class ArcherTest {
    private Archer archer;
    private LevelImpl level;
    private Bow bow;

    @BeforeEach
    void setUp() {
        
        GameCameraImpl camera = new GameCameraImpl(Vector2.zero(), 10, 10, 1.0, 100, 100);
        level = new LevelImpl(camera);
        
        CharacterStatistics stats = TestStatsFactory.createDefault();
        
        bow = new Bow(5, 0.5, null, "");
        archer = new Archer(level, Vector2.zero(), stats, bow, "");

        bow.setOwner(archer);
        
    }
    @Test
    void testShootArrowConsumesAmmoAndAddsToList() {
        int initialAmmo = bow.getAmmo();

        archer.shootArrow(Vector2.right(), 1.0, 
        new BoxCollider(Vector2.zero(), new Dimensions(1.0, 1.0)));

        assertEquals(initialAmmo - 1, bow.getAmmo());
        assertEquals(1, archer.getShotArrows().size());
    }

    @Test
    void testReturnArrowAbilityActivatesReturning() {
        
        for (int i = 0; i < 2; i++) {
            archer.shootArrow(Vector2.left(), 8.0,
            new BoxCollider(Vector2.zero(), new Dimensions(1.0, 1.0)));
        }

        ReturnArrowAbility ability = new ReturnArrowAbility(archer, level);
        ability.activate();

        for (Projectile p : archer.getShotArrows()) {
            assertTrue(((ReturnableArrow) p).isReturning());
        }
    }
    @Test
    void testArrowReturnRestoresAmmoAndRemovesArrow() {
        int initialAmmo = bow.getAmmo();

        archer.shootArrow(Vector2.up(), 5.0, 
            new BoxCollider(Vector2.zero(), new Dimensions(1.0, 1.0)));

        ReturnableArrow arrow = (ReturnableArrow) archer.getShotArrows().get(0);

        archer.setPosition(new Vector2(0.0, 1.0));

        arrow.startReturn();

        for (int i = 0; i < 60; i++) {
            arrow.update(0.016);  
        }
        assertEquals(initialAmmo, bow.getAmmo());
        assertFalse(archer.getShotArrows().contains(arrow));
    }
}

