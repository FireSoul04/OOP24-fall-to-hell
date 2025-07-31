package it.unibo.falltohell;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.falltohell.model.api.level.Level;
import it.unibo.falltohell.model.api.statistic.CharacterStatistics;
import it.unibo.falltohell.test.util.LevelTest;
import it.unibo.falltohell.test.util.TimerManagerTest;
import it.unibo.falltohell.test.util.debug.druid.DruidDebug;
import it.unibo.falltohell.test.util.debug.manager.FamiliarManagerDebug;
import it.unibo.falltohell.util.Vector2;

/**
 * Unit tests for the {@link DruidDebug} character, testing kill counting,
 * passive regeneration cycles, special ability activation, and special attacks.
 * <p>
 * This test class validates that the druid's kill counter resets correctly,
 * passive cycles increment as expected, regeneration of life and mana follows
 * the expected formula, and that the special ability and special attack
 * mechanics
 * behave correctly over time and with respect to mana consumption.
 * </p>
 *
 * @author Sara Visani
 */
class TestDruid {

    private static final String NULL = "null";
    private static final double OFFSET_DOUBLE = 0.0001;
    private static final int OFFSET_TIMER = 100;

    /**
     * Represents different conditions that can affect entities or characters.
     */
    public enum Condition {
        /** Represents the activation or presence of a special ability. */
        SPECIAL_ABILITY,

        /** Represents the activation or use of a special attack. */
        SPECIAL_ATTACK;

        /**
         * Converts the movement direction to a corresponding comand.
         */
        @Override
        public String toString() {
            final String lower = name().toLowerCase(Locale.ROOT);
            final String[] parts = lower.split("_");
            final StringBuilder sb = new StringBuilder();
            for (final String part : parts) {
                sb.append(part.substring(0, 1)
                .toUpperCase(Locale.ROOT))
                .append(part.substring(1));
            }
            return sb.toString();
        }
    }

    /**
     * Represents possible movement directions.
     */
    public enum Move {
        /** Movement to the right. */
        MOVE_RIGHT,

        /** Movement to the left. */
        MOVE_LEFT,

        /** Movement upwards. */
        MOVE_UP,

        /** Movement downwards. */
        MOVE_DOWN;

        /**
         * Converts the movement direction to a corresponding 2D vector.
         *
         * @return a {@link Vector2} representing the direction of movement
         */
        public Vector2 toVector2() {
            return switch (this) {
                case MOVE_RIGHT -> Vector2.right();
                case MOVE_LEFT  -> Vector2.left();
                case MOVE_UP    -> Vector2.up();
                case MOVE_DOWN  -> Vector2.down();
            };
        }

        /**
         * Converts the movement direction to a corresponding comand.
         */
        @Override
        public String toString() {
            final String lower = name().toLowerCase(Locale.ROOT);
            final String[] parts = lower.split("_");
            final StringBuilder sb = new StringBuilder();
            for (final String part : parts) {
                sb.append(part.substring(0, 1)
                .toUpperCase(Locale.ROOT))
                .append(part.substring(1));
            }
            return sb.toString();
        }
    }

    private TimerManagerTest time;
    private DruidDebug druid;
    private CharacterStatistics stats;
    private final double[][] lifeManaGains = {
            {}, // 0 kill
            { 0.05, 0.0 }, // 1 kill
            { 0.10, 0.0 }, // 2 kills
            { 0.10, 0.05 }, // 3 kills
            { 0.15, 0.10 }, // 4 kills
            { 0.20, 0.20 }, // 5 kills
    };

    /**
     * Initializes a fresh {@link DruidDebug} and associated statistics before each
     * test.
     */
    @BeforeEach
    void setUp() {
        final Level lv = new LevelTest();
        this.druid = new DruidDebug(lv, Vector2.zero());
        this.time = (TimerManagerTest) lv.getTimerManager();
        this.stats = (CharacterStatistics) this.druid.getStats();
    }

    /**
     * Tests that kills are correctly counted and added.
     */
    @Test
    void addKill() {
        assertEquals(this.druid.getKills(), 0);
        this.druid.addKill();
        this.druid.addKill();
        assertEquals(this.druid.getKills(), 2);
    }

    /**
     * Tests that kill count resets after reaching 5 kills.
     */
    @Test
    void resetKillAfter5() {
        this.druid.addKill();
        this.druid.addKill();
        this.druid.addKill();
        this.druid.addKill();
        this.druid.addKill();
        assertEquals(this.druid.getKills(), 0);
        this.druid.addKill();
        assertEquals(this.druid.getKills(), 1);
    }

    /**
     * Tests that kills reset automatically after a certain timer expires.
     *
     */
    @Test
    void resetKillByTimer() {
        this.druid.addKill();
        this.druid.addKill();
        assertEquals(this.druid.getKills(), 2);
        this.time.waitUntilTimeout(DruidDebug.getKillReset() + OFFSET_TIMER);
        assertEquals(0, this.druid.getKills());
    }

    /**
     * Tests the progression of passive cycles as kills increase.
     */
    @Test
    void checkCycleAfter5() {
        assertEquals(this.druid.getPassiveCycles(), 1);
        this.druid.addKill();
        this.druid.addKill();
        this.druid.addKill();
        assertEquals(this.druid.getPassiveCycles(), 1);
        this.druid.addKill();
        this.druid.addKill();
        assertEquals(this.druid.getPassiveCycles(), 2);
        this.druid.addKill();
        this.druid.addKill();
        assertEquals(this.druid.getPassiveCycles(), 2);
    }

    /**
     * Tests that passive cycles reset properly after the kill timer expires.
     *
     */
    @Test
    void checkCycleByTimer() {
        assertEquals(this.druid.getPassiveCycles(), 1);
        this.druid.addKill();
        this.druid.addKill();
        this.druid.addKill();
        this.time.waitUntilTimeout(DruidDebug.getKillReset() + OFFSET_TIMER);
        assertEquals(this.druid.getPassiveCycles(), 1);
        this.druid.addKill();
        this.druid.addKill();
        this.druid.addKill();
        assertEquals(this.druid.getPassiveCycles(), 1);
        this.druid.addKill();
        this.druid.addKill();
        assertEquals(this.druid.getPassiveCycles(), 2);
        this.druid.addKill();
        this.druid.addKill();
        assertEquals(this.druid.getPassiveCycles(), 2);
        this.time.waitUntilTimeout(DruidDebug.getKillReset() + OFFSET_TIMER);
        this.druid.addKill();
        this.druid.addKill();
        assertEquals(this.druid.getPassiveCycles(), 2);
    }

    /**
     * Tests regeneration of life and mana after kills over multiple cycles.
     *
     */
    @Test
    void checkRegen() {
        final int maxKillsToTest = 10;

        for (int i = 1; i <= maxKillsToTest; i++) {
            this.druid.setDamagedLife(this.stats.getFullLife());
            this.druid.subMana(this.stats.getInitialMana());

            this.time.waitUntilTimeout(1000 + OFFSET_TIMER);

            this.druid.addKill();

            final List<Double> expectedGains;
            if (this.druid.getKills() == 0) {
                expectedGains = this.computeRegen(DruidDebug.getEndKill(), this.druid.getPassiveCycles() - 1);
            } else {
                expectedGains = this.computeRegen(this.druid.getKills(), this.druid.getPassiveCycles());
            }

            assertFalse(expectedGains.isEmpty(), "Regen should not be empty after kill #" + i);
            assertTrue(expectedGains.get(0) >= 0, "Expected life regen should be >= 0 after kill #" + i);

            assertEquals(expectedGains.get(0), this.stats.getLife(), OFFSET_DOUBLE,
                    "Life after regen should match expected after cycle #" + i + ", kill #" + this.druid.getKills()
                            + " and passive cycle #" + this.druid.getPassiveCycles());

            if (expectedGains.size() > 1) {
                assertTrue(expectedGains.get(1) >= 0, "Expected mana regen should be >= 0 after kill #" + i);
                assertEquals(expectedGains.get(1), this.stats.getMana(), OFFSET_DOUBLE,
                        "Mana after regen should match expected after kill #" + i);
            }
        }
    }

    /**
     * Helper method to compute expected life and mana regeneration gains based on
     * kills and passive cycles.
     *
     * @param kills         number of kills
     * @param passiveCycles current passive cycle count
     * @return list containing expected life gain at index 0 and optionally mana
     *         gain at index 1
     */
    private List<Double> computeRegen(final int kills, final int passiveCycles) {
        final List<Double> gains = new ArrayList<>();
        if (kills >= 1 && kills <= DruidDebug.getEndKill()) {
            final double lifeGain = this.stats.getFullLife() * this.lifeManaGains[kills][0] * passiveCycles;
            final double manaGain = this.stats.getInitialMana() * this.lifeManaGains[kills][1] * passiveCycles;

            gains.add(lifeGain);
            if (manaGain > 0) {
                gains.add(manaGain);
            }
        }
        return gains;

    }

    /**
     * Tests activation and deactivation of the special ability (familiar creation),
     * mana consumption, and timing-based expiration of familiars.
     */
    @Test
    void checkCreation() {
        // Initially, the special ability should NOT be active
        assertFalse(this.druid.isSaActive());

        // Calling handleAttackInput with irrelevant parameters should NOT activate
        // special ability
        this.druid.handleAttackInput(NULL, NULL);
        assertFalse(this.druid.isSaActive());
        assertEquals(this.stats.getInitialMana(), this.stats.getMana(), OFFSET_DOUBLE);

        // Reduce mana to zero; still should NOT activate special ability
        this.druid.subMana(this.stats.getInitialMana());
        this.druid.handleAttackInput(NULL, NULL);
        assertFalse(this.druid.isSaActive());
        // No mana assertion here since mana was just zeroed

        // Replenish mana and activate special ability
        this.stats.addMana(this.stats.getInitialMana());
        this.druid.handleAttackInput(Condition.SPECIAL_ABILITY.toString(), NULL);
        assertTrue(this.druid.isSaActive());
        assertEquals(this.stats.getInitialMana() - DruidDebug.getCreationCost(), this.stats.getMana(), OFFSET_DOUBLE);

        // Wait for the familiar’s life duration plus a small buffer; ability should
        // deactivate
        this.time.waitUntilTimeout(FamiliarManagerDebug.getLifeDuration() + OFFSET_TIMER);
        assertFalse(this.druid.isSaActive());

        // Activate special ability again, creating one familiar
        this.stats.addMana(this.stats.getInitialMana());
        this.druid.handleAttackInput(Condition.SPECIAL_ABILITY.toString(), NULL);
        assertEquals(this.stats.getInitialMana() - DruidDebug.getCreationCost(), this.stats.getMana(), OFFSET_DOUBLE);

        // Wait half the familiar’s life duration
        this.time.waitUntilTimeout(FamiliarManagerDebug.getLifeDuration() / 2);

        // Activate special ability again, creating a second familiar
        this.stats.addMana(this.stats.getInitialMana());
        this.druid.handleAttackInput(Condition.SPECIAL_ABILITY.toString(), NULL);
        assertEquals(this.stats.getInitialMana() - DruidDebug.getCreationCost(), this.stats.getMana(), OFFSET_DOUBLE);

        // Wait remaining half life duration plus buffer
        // Only one familiar expired; special ability should still be active
        this.time.waitUntilTimeout(FamiliarManagerDebug.getLifeDuration() / 2 + OFFSET_TIMER);
        assertTrue(this.druid.isSaActive());

        // Wait full life duration plus buffer for second familiar to expire
        // Both familiars are gone; special ability should deactivate
        this.time.waitUntilTimeout(FamiliarManagerDebug.getLifeDuration() + OFFSET_TIMER);
        assertFalse(this.druid.isSaActive());
    }

    /**
     * Tests special attack behavior including mana consumption, attack direction,
     * and manager availability.
     */
    @Test
    void checkSpAttack() {
        // Step 1: Activate special ability to create the first familiar
        this.druid.handleAttackInput(Condition.SPECIAL_ABILITY.toString(), NULL);
        assertTrue(this.druid.getManager().isFree());

        // Step 2: Trigger special attack with "null" move -> no attack should happen
        this.druid.handleAttackInput(Condition.SPECIAL_ATTACK.toString(), NULL);
        assertEquals(this.stats.getInitialMana() - DruidDebug.getCreationCost(),
                this.stats.getMana(), OFFSET_DOUBLE);
        assertTrue(this.druid.getManager().isFree());

        // Step 3: Wait for familiar to expire
        this.time.waitUntilTimeout(FamiliarManagerDebug.getLifeDuration() + OFFSET_TIMER);

        // Step 4: Replenish mana and create a new familiar
        this.stats.addMana(this.stats.getInitialMana());
        this.druid.handleAttackInput(Condition.SPECIAL_ABILITY.toString(), NULL);

        // Step 5: Trigger special attack with MOVE_LEFT -> familiar attacks, manager is no longer free
        this.druid.handleAttackInput(Condition.SPECIAL_ATTACK.toString(), Move.MOVE_LEFT.toString());
        assertFalse(this.druid.getManager().isFree());
        assertEquals(this.stats.getInitialMana() - DruidDebug.getCreationCost() - DruidDebug.getAttackCost(),
                this.stats.getMana(), OFFSET_DOUBLE);

        // Step 6: Trigger another special attack while manager is busy -> no additional mana should be spent
        this.druid.handleAttackInput(Condition.SPECIAL_ATTACK.toString(), Move.MOVE_DOWN.toString());
        assertFalse(this.druid.getManager().isFree());
        assertEquals(this.stats.getInitialMana() - DruidDebug.getCreationCost() - DruidDebug.getAttackCost(),
                this.stats.getMana(), OFFSET_DOUBLE);

        // Step 7: Wait for familiar to expire, then create multiple familiars
        this.time.waitUntilTimeout(FamiliarManagerDebug.getLifeDuration() + OFFSET_TIMER);
        this.stats.addMana(this.stats.getInitialMana());
        this.druid.handleAttackInput(Condition.SPECIAL_ABILITY.toString(), NULL);
        this.druid.handleAttackInput(Condition.SPECIAL_ABILITY.toString(), NULL);
        this.stats.addMana(this.stats.getInitialMana());
        this.druid.handleAttackInput(Condition.SPECIAL_ABILITY.toString(), NULL);
        this.druid.handleAttackInput(Condition.SPECIAL_ABILITY.toString(), NULL);

        // Step 8: Recharge mana and use special attacks in all four directions

        // MOVE_DOWN
        this.stats.addMana(this.stats.getInitialMana());
        this.druid.handleAttackInput(Condition.SPECIAL_ATTACK.toString(), Move.MOVE_DOWN.toString());
        assertEquals(this.stats.getInitialMana() - DruidDebug.getAttackCost(),
                this.stats.getMana(), OFFSET_DOUBLE);
        assertTrue(this.druid.getManager().isFree());
        assertEquals(Move.MOVE_DOWN.toVector2(), this.druid.getDirection());

        // MOVE_LEFT
        this.druid.handleAttackInput(Condition.SPECIAL_ATTACK.toString(), Move.MOVE_LEFT.toString());
        assertEquals(this.stats.getInitialMana() - 2 * DruidDebug.getAttackCost(),
                this.stats.getMana(), OFFSET_DOUBLE);
        assertTrue(this.druid.getManager().isFree());
        assertEquals(Move.MOVE_LEFT.toVector2(), this.druid.getDirection());

        // MOVE_RIGHT
        this.druid.handleAttackInput(Condition.SPECIAL_ATTACK.toString(), Move.MOVE_RIGHT.toString());
        assertEquals(this.stats.getInitialMana() - 3 * DruidDebug.getAttackCost(),
                this.stats.getMana(), OFFSET_DOUBLE);
        assertTrue(this.druid.getManager().isFree());
        assertEquals(Move.MOVE_RIGHT.toVector2(), this.druid.getDirection());

        // MOVE_UP
        this.druid.handleAttackInput(Condition.SPECIAL_ATTACK.toString(), Move.MOVE_UP.toString());
        assertEquals(this.stats.getInitialMana() - 4 * DruidDebug.getAttackCost(),
                this.stats.getMana(), OFFSET_DOUBLE);
        assertFalse(this.druid.getManager().isFree());
        assertEquals(Move.MOVE_UP.toVector2(), this.druid.getDirection());
    }
}
