package it.unibo.falltohell;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.falltohell.model.api.gameobject.movable.Projectile;
import it.unibo.falltohell.model.impl.GameCameraImpl;
import it.unibo.falltohell.model.impl.level.LevelImpl;
import it.unibo.falltohell.model.impl.ability.active.ReturnArrowAbility;
import it.unibo.falltohell.model.impl.gameobject.movable.entity.character.Archer;
import it.unibo.falltohell.model.impl.gameobject.movable.projectile.ReturnableArrow;
import it.unibo.falltohell.util.Vector2;
/**
 * Test class for the Archer character and its interactions with the Bow and ReturnableArrow.
 * It verifies shooting arrows, returning them, and ammo management.
 *
 * @author Lorenzo Casadei
 */
public class ArcherTest {
    private Archer archer;
    private LevelImpl level;

    @BeforeEach
    void setUp() {

        GameCameraImpl camera = new GameCameraImpl(Vector2.zero(), 10, 10, 1.0);
        camera.setLevelSize(new Vector2(100, 100));
        level = new LevelImpl(camera);


        archer = new Archer(level, Vector2.zero());



    }
    @Test
    void testShootArrowConsumesAmmoAndAddsToList() {
        int initialAmmo = archer.getBow().getAmmo();

        archer.shootArrow(Vector2.right(), 1.0,
        new BoxCollider(Vector2.zero(), new Dimensions(1.0, 1.0)));
        archer.attack();

        assertEquals(initialAmmo - 1, archer.getBow().getAmmo());
        assertEquals(1, archer.getShotedArrows().size());
    }

    @Test
    void testReturnArrowAbility() {

        for (int i = 0; i < 2; i++) {
            archer.attack();
        }

        ReturnArrowAbility ability = new ReturnArrowAbility(archer);
        ability.activate();

        for (Projectile p : archer.getShotedArrows()) {
            assertTrue(((ReturnableArrow) p).isReturning());
        }
    }
    @Test
    void testArrowReturnRestoresAmmoAndRemovesArrow() {
        int initialAmmo = archer.getBow().getAmmo();

        archer.shootArrow(Vector2.up(), 5.0,
            new BoxCollider(Vector2.zero(), new Dimensions(1.0, 1.0)));

        ReturnableArrow arrow = (ReturnableArrow) archer.getShotedArrows().get(0);

        archer.setPosition(new Vector2(0.0, 1.0));
        archer.attack();
        ReturnableArrow arrow;
        if(archer.getBow().getShotProjectile().isPresent()){
            arrow = (ReturnableArrow)archer.getBow().getShotProjectile().get();
            archer.setPosition(new Vector2(0.0, 1.0));

            arrow.startReturn();

            for (int i = 0; i < 60; i++) {
                arrow.update(0.016);
            }
            assertEquals(initialAmmo, archer.getBow().getAmmo());
            assertFalse(archer.getShotedArrows().contains(arrow));
        }else {
            assertEquals(initialAmmo, archer.getBow().getAmmo());
        }

    }
}

