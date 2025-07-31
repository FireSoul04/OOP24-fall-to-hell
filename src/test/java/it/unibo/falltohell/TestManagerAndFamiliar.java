package it.unibo.falltohell;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import it.unibo.falltohell.model.api.level.Level;
import it.unibo.falltohell.model.api.statistic.CharacterStatistics;
import it.unibo.falltohell.model.impl.GameDataImpl;
import it.unibo.falltohell.TestDruid.Move;
import it.unibo.falltohell.model.api.gameobject.GameObject;
import it.unibo.falltohell.model.api.gameobject.movable.entity.enemy.Enemy;
import it.unibo.falltohell.test.util.LevelTest;
import it.unibo.falltohell.test.util.TimerManagerTest;
import it.unibo.falltohell.test.util.debug.EnemyFactoryDebug;
import it.unibo.falltohell.test.util.debug.druid.DruidDebug;
import it.unibo.falltohell.test.util.debug.druid.FamiliarBatDebug;
import it.unibo.falltohell.test.util.debug.manager.FamiliarManagerDebug;
import it.unibo.falltohell.util.Vector2;

/**
 * Unit tests for the {@link FamiliarManagerDebug} and its interaction with
 * familiars (specifically {@link FamiliarBatDebug}) and the druid character.
 * <p>
 * These tests cover creation, removal (immediate and delayed), attack
 * functionality, attack direction control, randomness in attack behavior,
 * attack effects on enemies, and attack stopping conditions.
 * </p>
 *
 * @author Sara Visani
 */
class TestManagerAndFamiliar {

    private static final double P_40 = 40.0;
    private static final double P_20 = 20.0;
    private static final double P_10 = 10.0;
    private static final int MAX_BAT_ATTACK = 5;
    private static final int OFFSET_TIMER_1 = 100;
    private static final int OFFSET_TIMER_2 = 500;
    private static final double OFFSET_DOUBLE = 0.0001;
    private TimerManagerTest time;
    private Level lv;
    private DruidDebug druid;
    private CharacterStatistics stats;
    private FamiliarManagerDebug manager;

    /**
     * Initializes test environment before each test:
     * <ul>
     * <li>Creates a new {@link LevelTest} instance.</li>
     * <li>Initializes a {@link DruidDebug} at position (0,0).</li>
     * <li>Links game data and sets the current character to the druid.</li>
     * <li>Retrieves character statistics and the familiar manager.</li>
     * </ul>
     */
    @SuppressWarnings("PMD.UnnecessaryCast")
    @BeforeEach
    void setUp() {
        this.lv = new LevelTest();
        this.druid = new DruidDebug(lv, Vector2.zero());
        this.lv.linkGameData(new GameDataImpl(
                Map.of(this.druid.getCharacterID(), this.druid)));
        this.lv.getGameData().changeCurrentCharacter(this.druid);
        this.time = (TimerManagerTest) lv.getTimerManager();
        this.stats = (CharacterStatistics) druid.getStats();
        this.manager = (FamiliarManagerDebug) this.druid.getManager();
    }

    /**
     * Tests familiar creation and removal without relying on timers.
     * <p>
     * The test covers:
     * <ul>
     * <li>Creating a familiar and verifying it is added to the level.</li>
     * <li>Removing the familiar and verifying it is removed immediately.</li>
     * <li>Creating a familiar, triggering an attack, and checking the familiar
     * stays in the level while attacking.</li>
     * <li>Requesting removal while attacking, which should mark it for deferred
     * removal.</li>
     * <li>Simulating attack completion and verifying that the familiar is finally
     * removed.</li>
     * </ul>
     * </p>
     */
    @Test
    void creationAndRemoveWithoutTimer() {
        // Step 1: Create a familiar and ensure it is added to the level
        FamiliarBatDebug bat = this.manager.createFamiliar(this.druid);
        List<GameObject> gameObjects = this.lv.getGameObjects();
        assertTrue(gameObjects.contains(bat));

        // Step 2: Remove the familiar and verify it is no longer in the level
        this.manager.removeFamiliar(bat);
        gameObjects = this.lv.getGameObjects();
        assertFalse(gameObjects.contains(bat));

        // Step 3: Create another familiar and trigger an attack
        bat = this.manager.createFamiliar(this.druid);
        this.manager.attack(Move.MOVE_RIGHT.toVector2());
        gameObjects = this.lv.getGameObjects();
        assertTrue(gameObjects.contains(bat));

        // Step 4: Request removal (pending removal)
        this.manager.removeFamiliar(bat);
        gameObjects = this.lv.getGameObjects();
        assertTrue(gameObjects.contains(bat)); // Still in level until processed
        assertTrue(this.manager.getPendingRemoval().contains(bat));

        // Step 5: Simulate attack completion
        bat.setAttacking(false);
        bat.getAttackFinishListener().onAttackFinished(bat);

        // Step 6: Verify familiar is fully removed from both level and pending list
        assertFalse(this.manager.getPendingRemoval().contains(bat));
        gameObjects = this.lv.getGameObjects();
        assertFalse(gameObjects.contains(bat));
    }

    /**
     * Tests familiar creation and automatic removal after a fixed life duration.
     * <p>
     * This test includes:
     * <ul>
     * <li>Creation of a familiar and validation of its presence in the level.</li>
     * <li>Waiting for the familiar's lifespan to expire and verifying automatic
     * removal.</li>
     * <li>Creating another familiar, forcing it to attack before expiration,
     * ensuring removal is deferred until attack finishes.</li>
     * </ul>
     * </p>
     */
    @Test
    void creationAndRemoveTimer() throws InterruptedException {
        // Step 1: Create a familiar and verify it appears in the level
        FamiliarBatDebug bat = this.manager.createFamiliar(this.druid);
        List<GameObject> gameObjects = this.lv.getGameObjects();
        assertTrue(gameObjects.contains(bat));

        // Step 2: Wait for its life duration to expire and verify it is removed
        // automatically
        this.time.waitUntilTimeout(FamiliarManagerDebug.getLifeDuration() + OFFSET_TIMER_1);
        gameObjects = this.lv.getGameObjects();
        assertFalse(gameObjects.contains(bat));

        // Step 3: Create another familiar
        bat = this.manager.createFamiliar(this.druid);

        // Step 4: Wait until shortly before expiration, then make it attack
        this.time.waitUntilTimeout(FamiliarManagerDebug.getLifeDuration() - OFFSET_TIMER_2);
        this.manager.attack(Move.MOVE_RIGHT.toVector2());

        // Step 5: Wait until after its original lifespan finishes
        this.time.waitUntilTimeout(OFFSET_TIMER_2);

        // At this point, the familiar should still be in the level but pending removal
        gameObjects = this.lv.getGameObjects();
        assertTrue(gameObjects.contains(bat));
        assertTrue(this.manager.getPendingRemoval().contains(bat));

        // Step 6: Simulate the end of the attack to complete removal
        bat.setAttacking(false);
        bat.getAttackFinishListener().onAttackFinished(bat);

        // Step 7: Verify it is fully removed from both the level and pending removal
        gameObjects = this.lv.getGameObjects();
        assertFalse(gameObjects.contains(bat));
        assertFalse(this.manager.getPendingRemoval().contains(bat));
    }

    /**
     * Tests creating and removing multiple familiars and controlling the order of
     * their attacks.
     * <p>
     * This test validates:
     * <ul>
     * <li>Creating multiple familiars and ensuring both appear in the level.</li>
     * <li>Immediate removal of idle familiars.</li>
     * <li>Creating familiars, triggering attack, and checking only one attacks
     * while the other remains idle.</li>
     * <li>Removing an attacking familiar with deferred removal until attack
     * finishes.</li>
     * <li>Automatic removal of remaining familiars after lifespan expires.</li>
     * </ul>
     * </p>
     */
    @Test
    void createRemoveMultiFamiliarPlusControlOrderOfAttack() {
        // Step 1: Create two familiars and ensure they are in the level
        FamiliarBatDebug bat1 = this.manager.createFamiliar(this.druid);
        FamiliarBatDebug bat2 = this.manager.createFamiliar(this.druid);
        List<GameObject> gameObjects = this.lv.getGameObjects();
        assertTrue(gameObjects.contains(bat1));
        assertTrue(gameObjects.contains(bat2));

        // Step 2: Remove familiars while they are idle (immediate removal)
        this.manager.removeFamiliar(bat1);
        gameObjects = this.lv.getGameObjects();
        assertFalse(gameObjects.contains(bat1));

        this.manager.removeFamiliar(bat2);
        gameObjects = this.lv.getGameObjects();
        assertFalse(gameObjects.contains(bat2));

        // Step 3: Create two new familiars and trigger an attack
        bat1 = this.manager.createFamiliar(this.druid);
        bat2 = this.manager.createFamiliar(this.druid);
        this.manager.attack(Move.MOVE_RIGHT.toVector2());

        // First familiar should be attacking, second should remain idle
        assertTrue(bat2.isIdle());

        // Step 4: Attempt to remove the attacking familiar (bat1)
        this.manager.removeFamiliar(bat1);
        gameObjects = this.lv.getGameObjects();
        assertTrue(gameObjects.contains(bat1)); // still present until attack finishes
        assertTrue(this.manager.getPendingRemoval().contains(bat1)); // marked for deferred removal

        // Step 5: Simulate attack completion
        bat1.setAttacking(false); // mark it as idle to allow removal
        bat1.getAttackFinishListener().onAttackFinished(bat1);

        // Step 6: Verify bat1 is removed from pending and level
        assertFalse(this.manager.getPendingRemoval().contains(bat1));
        gameObjects = this.lv.getGameObjects();
        assertFalse(gameObjects.contains(bat1));
        assertTrue(gameObjects.contains(bat2)); // bat2 still alive

        // Step 7: Wait for the life duration of bat2 to expire automatically
        this.time.waitUntilTimeout(FamiliarManagerDebug.getLifeDuration() + OFFSET_TIMER_1);
        gameObjects = this.lv.getGameObjects();
        assertFalse(gameObjects.contains(bat2));
    }

    /**
     * Tests that familiars attack in the correct directions and update their
     * internal state accordingly.
     * <p>
     * Creates four familiars and commands attacks in the four cardinal directions.
     * Verifies that each familiar changes from idle to attacking and adopts the
     * proper attack direction vector.
     * </p>
     */
    @Test
    void controlDirectionAttack() {
        // Step 1: Create four familiars
        final FamiliarBatDebug bat1 = this.manager.createFamiliar(this.druid);
        final FamiliarBatDebug bat2 = this.manager.createFamiliar(this.druid);
        final FamiliarBatDebug bat3 = this.manager.createFamiliar(this.druid);
        final FamiliarBatDebug bat4 = this.manager.createFamiliar(this.druid);

        // Step 2: All familiars should be idle initially
        assertTrue(bat1.isIdle());
        assertTrue(bat2.isIdle());
        assertTrue(bat3.isIdle());
        assertTrue(bat4.isIdle());

        // Step 3: Trigger attacks in different directions
        this.manager.attack(Move.MOVE_RIGHT.toVector2());
        this.manager.attack(Move.MOVE_LEFT.toVector2());
        this.manager.attack(Move.MOVE_DOWN.toVector2());
        this.manager.attack(Move.MOVE_UP.toVector2());

        // Step 4: After attacking, all familiars should be busy (not idle)
        assertFalse(bat1.isIdle());
        assertFalse(bat2.isIdle());
        assertFalse(bat3.isIdle());
        assertFalse(bat4.isIdle());

        // Step 5: Validate that each familiar has the correct attack direction
        assertEquals(Move.MOVE_RIGHT.toVector2(), bat1.getAttackDirection());
        assertEquals(Move.MOVE_LEFT.toVector2(), bat2.getAttackDirection());
        assertEquals(Move.MOVE_DOWN.toVector2(), bat3.getAttackDirection());
        assertEquals(Move.MOVE_UP.toVector2(), bat4.getAttackDirection());
    }

    /**
     * Tests the distribution of the random number used for attack selection in
     * familiars.
     * <p>
     * Runs a large number of attack simulations (10,000) and collects statistics
     * on the generated attack number to verify that the distribution matches the
     * expected probabilities:
     * 10%, 20%, 40%, 20%, and 10% for numbers 1 through 5 respectively, within a 2%
     * tolerance.
     * </p>
     */
    @Test
    @SuppressFBWarnings(value = "DLS_DEAD_LOCAL_STORE",
    justification = "Tolerance variable is used in assertion inside the loop")
    void checkRandomNumberAttack() {
        final int trials = 10_000;
        final int[] counts = new int[MAX_BAT_ATTACK]; // For numbers 1 to 5

        for (int i = 0; i < trials; i++) {
            // Create a new familiar
            final FamiliarBatDebug bat = this.manager.createFamiliar(this.druid);
            this.manager.attack(Move.MOVE_RIGHT.toVector2()); // Trigger an attack

            // Get numberAttack and validate it is in range
            final int numberAttack = bat.getNumberAttack();
            assertTrue(numberAttack >= 1 && numberAttack <= MAX_BAT_ATTACK,
                    "numberAttack out of range: " + numberAttack);
            counts[numberAttack - 1]++;

            // Proper cleanup to avoid pending removal issues
            if (!bat.isIdle()) {
                bat.setAttacking(false); // Mark as idle
                bat.getAttackFinishListener().onAttackFinished(bat);
            }
            this.manager.removeFamiliar(bat);
        }

        // Calculate probabilities
        final double[] probabilities = new double[MAX_BAT_ATTACK];
        for (int i = 0; i < MAX_BAT_ATTACK; i++) {
            probabilities[i] = counts[i] * 100.0 / trials;
        }

        // Expected distribution
        final double[] expected = { P_10, P_20, P_40, P_20, P_10 };
        final double tolerance = 2.0; // 2% tolerance

        for (int i = 0; i < MAX_BAT_ATTACK; i++) {
            assertTrue(
                    Math.abs(probabilities[i] - expected[i]) < tolerance,
                    String.format("Probability for %d differs: expected %.1f%% but got %.1f%%",
                            i + 1, expected[i], probabilities[i]));
        }
    }

    /**
     * Tests the effect of familiar attacks on enemies and the resulting
     * regeneration of life and mana for the druid.
     * <p>
     * For each attack cycle:
     * <ul>
     * <li>Resets druid life and mana to full values.</li>
     * <li>Resets enemy life to full.</li>
     * <li>Creates a familiar and performs an attack effect on the enemy.</li>
     * <li>Validates that life and mana regeneration values are correctly
     * applied.</li>
     * <li>Checks that enemy health is reduced by the expected damage amount.</li>
     * </ul>
     * </p>
     */
    @Test
    void effectAttack() {
        final int maxAttack = MAX_BAT_ATTACK;
        final Enemy enemy = new EnemyFactoryDebug().createImp(lv, Vector2.left());

        for (int i = 1; i <= maxAttack; i++) {
            this.druid.setDamagedLife(this.stats.getFullLife());
            this.druid.subMana(this.stats.getInitialMana());
            enemy.getStats().setLife(enemy.getStats().getFullLife());

            this.time.waitUntilTimeout(1000 + OFFSET_TIMER_1);

            final List<Double> expectedGains = this.computeRegen();
            final var bat = this.manager.createFamiliar(this.druid);
            bat.attackEffect(enemy);

            assertFalse(expectedGains.isEmpty(), "Regen should not be empty after kill #" + i);
            assertTrue(expectedGains.get(0) >= 0, "Expected life regen should be >= 0 after kill #" + i);
            assertTrue(expectedGains.get(1) >= 0, "Expected mana regen should be >= 0 after kill #" + i);

            assertEquals(expectedGains.get(0), this.stats.getLife(), OFFSET_DOUBLE,
                    "Life after regen should match expected after cycle #" + i + ", kill #" + this.druid.getKills()
                            + " and passive cycle #" + this.druid.getPassiveCycles());
            assertEquals(expectedGains.get(1), this.stats.getMana(), OFFSET_DOUBLE,
                    "Mana after regen should match expected after kill #" + i);
            assertEquals(enemy.getStats().getFullLife() - FamiliarBatDebug.getDamage(), enemy.getStats().getLife());
        }
    }

    /**
     * Helper method to compute expected regeneration amounts for life and mana
     * based on the druid's stats and the familiar's regeneration rate.
     *
     * @return a list containing expected life gain and mana gain values
     */
    private List<Double> computeRegen() {
        final List<Double> gains = new ArrayList<>();

        double lifeGain = this.stats.getFullLife() * FamiliarBatDebug.getRegenRate();
        double manaGain = this.stats.getInitialMana() * FamiliarBatDebug.getRegenRate();

        lifeGain = Math.min(lifeGain, stats.getFullLife());
        manaGain = Math.min(manaGain, stats.getInitialMana());

        gains.add(lifeGain);
        gains.add(manaGain);

        return gains;
    }

    /**
     * Tests the stopping condition of a familiar's attack on an enemy.
     * <p>
     * Simulates attacks until the enemy is about to die, verifying the familiar
     * remains attacking,
     * then performs the final attack to kill the enemy and checks that:
     * <ul>
     * <li>The enemy is marked as dead.</li>
     * <li>The familiar returns to idle state.</li>
     * <li>The familiar clears its attack target and resets attack counters.</li>
     * </ul>
     * </p>
     */
    @Test
    void stopAttack() {
        // Create an enemy with known health
        final FamiliarBatDebug bat = this.manager.createFamiliar(this.druid);
        final Enemy enemy = new EnemyFactoryDebug().createImp(lv, Vector2.left());

        final double enemyLife = enemy.getStats().getFullLife();
        final double damage = FamiliarBatDebug.getDamage();
        final int hitsToKill = (int) Math.ceil(enemyLife / damage);
        bat.setAttacking(true);
        bat.setNumberAttack(hitsToKill);

        // Attack until just before the enemy dies
        for (int i = 0; i < hitsToKill - 1; i++) {
            bat.attackEffect(enemy);

            bat.onCollision(enemy, Vector2.left());

            // Enemy should still be alive
            assertFalse(enemy.isDead(), "Enemy should not be dead on hit #" + (i + 1));

            // Familiar should still be attacking
            assertFalse(bat.isIdle(), "Familiar should not be idle until the enemy is dead");
            assertFalse(bat.getEnemy().isEmpty(), "Familiar should still have a target until enemy dies");

            this.time.waitUntilTimeout(1000 + OFFSET_TIMER_1);
        }

        // Final attack that kills the enemy
        bat.attackEffect(enemy);

        // Enemy should now be dead
        assertTrue(enemy.isDead(), "Enemy should be dead after the final hit");
        assertTrue(bat.isIdle(), "Familiar should become idle after the enemy dies");
        assertEquals(0, bat.getNumberAttack(), "Number of attacks should reset after the kill");
        assertTrue(bat.getEnemy().isEmpty(), "Familiar should clear its target after enemy dies");
    }
}
