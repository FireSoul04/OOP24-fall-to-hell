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

    /**
     * set up for the test.
     */
    @BeforeEach
    void setUp() {
        final GameCameraImpl camera = new GameCameraImpl(Vector2.zero(), 10, 10, 1.0);
        camera.setLevelSize(new Vector2(100, 100));
        level = new LevelImpl(camera);
        archer = new Archer(level, Vector2.zero());
    }

    /**
     * Test if the archer consume ammo after shooting.
     */
    @Test
    void testConsumesAmmo() {
        final int initialAmmo = archer.getBow().getAmmo();
        archer.attack();

        assertEquals(initialAmmo - 1, archer.getBow().getAmmo());
        assertEquals(1, archer.getShotedArrows().size());
    }
    /**
     * Test if the arrows came back after activate the ability. 
     */
    @Test
    void testReturnArrowAbility() {

        for (int i = 0; i < 2; i++) {
            archer.attack();
        }

        final ReturnArrowAbility ability = new ReturnArrowAbility(archer);
        ability.activate();

        for (final Projectile p : archer.getShotedArrows()) {
            assertTrue(((ReturnableArrow) p).isReturning());
        }
    }
    /**
     * Test if the ammo are restored after the arrows came back.
     */
    @Test
    void testArrowReturnRestoresAmmo() {
        final int initialAmmo = archer.getBow().getAmmo();

        archer.setPosition(new Vector2(0.0, 1.0));
        archer.attack();
        final ReturnableArrow arrow;
        if (archer.getBow().getShotProjectile().isPresent()) {
            arrow = (ReturnableArrow) archer.getBow().getShotProjectile().get();
            archer.setPosition(new Vector2(0.0, 1.0));

            arrow.startReturn();
            final int frames = 60;
            final double deltaTime = 0.016;
            for (int i = 0; i < frames; i++) {
                arrow.update(deltaTime);
            }
            assertEquals(initialAmmo, archer.getBow().getAmmo());
            assertFalse(archer.getShotedArrows().contains(arrow));
        } else {
            assertEquals(initialAmmo, archer.getBow().getAmmo());
        }

    }
}

