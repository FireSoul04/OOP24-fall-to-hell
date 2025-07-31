package it.unibo.falltohell;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import it.unibo.falltohell.model.api.level.Level;
import it.unibo.falltohell.test.util.LevelTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.falltohell.model.api.gameobject.movable.Projectile;
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
class ArcherTest {
    private Archer archer;

    /**
     * set up for the test.
     */
    @BeforeEach
    void setUp() {
        final Level level = new LevelTest();
        archer = new Archer(level, Vector2.zero());
    }

    /**
     * Test if the archer consume ammo after shooting.
     */
    @Test
    void testConsumesAmmo() {
        final int initialAmmo = archer.getBowAmmo();
        archer.attack();
        assertEquals(initialAmmo - 1, archer.getBowAmmo());
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
        final int initialAmmo = archer.getBowAmmo();
        archer.setPosition(new Vector2(0.0, 1.0));
        archer.attack();
        final ReturnableArrow arrow;
        if (archer.getBowLastProjectile().isPresent()) {
            arrow = (ReturnableArrow) archer.getBowLastProjectile().get();
            archer.setPosition(new Vector2(0.0, 1.0));
            arrow.startReturn();
            final int frames = 60;
            final double deltaTime = 0.016;
            for (int i = 0; i < frames; i++) {
                arrow.update(deltaTime);
            }
            assertEquals(initialAmmo, archer.getBowAmmo());
            assertFalse(archer.getShotedArrows().contains(arrow));
        } else {
            assertEquals(initialAmmo, archer.getBowAmmo());
        }
    }
}

