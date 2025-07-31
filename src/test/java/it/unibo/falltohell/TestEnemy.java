package it.unibo.falltohell;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.falltohell.model.api.factory.EnemyFactory;
import it.unibo.falltohell.model.api.gameobject.GameObject;
import it.unibo.falltohell.model.api.gameobject.movable.entity.enemy.Enemy;
import it.unibo.falltohell.model.api.level.Level;
import it.unibo.falltohell.model.api.statistic.BaseEnemyStatistics;
import it.unibo.falltohell.model.api.statistic.LongRangeEnemyStatistics;
import it.unibo.falltohell.model.impl.GameDataImpl;
import it.unibo.falltohell.model.impl.gameobject.movable.entity.enemy.BaseEnemy.BuffNames;
import it.unibo.falltohell.test.util.LevelTest;
import it.unibo.falltohell.test.util.TimerManagerTest;
import it.unibo.falltohell.test.util.debug.EnemyFactoryDebug;
import it.unibo.falltohell.test.util.debug.druid.DruidDebug;
import it.unibo.falltohell.test.util.debug.enemy.BaseEnemyDebug;
import it.unibo.falltohell.test.util.debug.enemy.CentaurDebug;
import it.unibo.falltohell.test.util.debug.enemy.ImpDebug;
import it.unibo.falltohell.test.util.debug.enemy.LotawiecDebug;
import it.unibo.falltohell.test.util.debug.enemy.TenguDebug;
import it.unibo.falltohell.test.util.debug.manager.EnemyTimerManagerDebug;
import it.unibo.falltohell.test.util.debug.manager.SafeZoneManagerDebug;
import it.unibo.falltohell.test.util.debug.manager.EnemyTimerManagerDebug.TimerPrefix;
import it.unibo.falltohell.util.Vector2;

/**
 * Unit tests for verifying the correct behavior of enemy entities in the game.
 *
 * <p>
 * This test suite focuses on:
 * <ul>
 * <li>Correct creation and registration of enemies in the game world</li>
 * <li>Enemy removal and respawn logic</li>
 * <li>Drop mechanics and buff probabilities</li>
 * <li>No-aggro timers and enemy regeneration</li>
 * <li>Attack timers and repeated enemy attacks</li>
 * </ul>
 *
 * <p>
 * Enemies tested:
 * <ul>
 * <li>{@link CentaurDebug}</li>
 * <li>{@link ImpDebug}</li>
 * <li>{@link TenguDebug}</li>
 * <li>{@link LotawiecDebug}</li>
 * </ul>
 *
 * @author Sara Visani
 */
class TestEnemy {
    private static final int OFFSET_3 = 700;
    private static final int OFFSET_2 = 400;
    private static final int OFFSET_1 = 200;
    private static final double TOLL_2 = 0.2;
    private static final double TOLL_1 = 0.15;
    private static final int ITERATION = 100_000;
    private TimerManagerTest time;
    private Level lv;
    private BaseEnemyDebug centaur, imp, tengu, lotawiec;
    private DruidDebug druid;
    private EnemyTimerManagerDebug manager;
    private SafeZoneManagerDebug safezone;

    /**
     * Initializes the test environment before each test.
     *
     * <p>
     * It creates a test level, a druid character, and instantiates all debug
     * enemies.
     * It also links the game data, initializes the timer manager, and the safe zone
     * manager.
     */
    @BeforeEach
    void setUp() {
        this.lv = new LevelTest();
        this.druid = new DruidDebug(lv, Vector2.zero());
        this.lv.linkGameData(new GameDataImpl(
                Map.of(this.druid.getCharacterID(), this.druid)));
        this.lv.getGameData().changeCurrentCharacter(this.druid);
        this.time = (TimerManagerTest) lv.getTimerManager();
        final EnemyFactory factory = new EnemyFactoryDebug();
        this.centaur = (CentaurDebug) factory.createCentaur(lv, Vector2.zero());
        this.imp = (ImpDebug) factory.createImp(lv, Vector2.zero());
        this.tengu = (TenguDebug) factory.createTengu(lv, Vector2.zero());
        this.lotawiec = (LotawiecDebug) factory.createLotawiec(lv, Vector2.zero());
        this.manager = (EnemyTimerManagerDebug) this.centaur.getEnemyTimerManager();
        this.safezone = (SafeZoneManagerDebug) this.centaur.getSafeZoneManager();
    }

    /**
     * Verifies that all enemies are correctly created, registered in the game
     * world,
     * and have their respective timers set.
     */
    @Test
    void createEnemy() {
        final List<GameObject> gameObject = lv.getGameObjects();
        final var enemies = List.of(this.imp, this.centaur, this.lotawiec, this.tengu);
        assertTrue(gameObject.containsAll(enemies));
        enemies.forEach(e -> {
            assertFalse(this.manager.getTimerName((Enemy) e, TimerPrefix.NO_AGGRO).isEmpty());
        });
        final var flying = List.of(this.lotawiec, this.tengu);
        flying.forEach(e -> {
            assertFalse(this.manager.getTimerName((Enemy) e, TimerPrefix.ATTACK).isEmpty());
        });
        assertNotNull(this.safezone);
    }

    /**
     * Tests enemy removal after their life reaches zero and ensures that
     * the timers are also cleared.
     */
    @Test
    void removeEnemy() {
        final var enemies = List.of(this.imp, this.centaur, this.lotawiec, this.tengu);
        enemies.forEach(e -> e.setDamagedLife(e.getStats().getFullLife()));
        final List<GameObject> gameObject = lv.getGameObjects();
        assertFalse(gameObject.containsAll(enemies));
        enemies.forEach(e -> {
            this.manager.getEnemyTimers().keySet().forEach(k -> assertNotEquals(k, e));
        });
    }

    /**
     * Tests the behavior of enemies entering and exiting a safe zone.
     *
     * <p>
     * When entering the safe zone:
     * <ul>
     * <li>All enemies are removed from the level</li>
     * </ul>
     *
     * <p>
     * When exiting the safe zone:
     * <ul>
     * <li>All enemies are respawned at their initial positions</li>
     * <li>Enemies have full life</li>
     * <li>Timers are reinitialized</li>
     * </ul>
     */
    @Test
    void resetEnemy() {
        final var enemies = List.of(this.imp, this.centaur, this.lotawiec, this.tengu);
        this.safezone.handleSafeZoneEnter();
        List<GameObject> gameObject = lv.getGameObjects();
        assertFalse(gameObject.containsAll(enemies));
        this.safezone.handleSafeZoneExit();
        gameObject = lv.getGameObjects();
        assertTrue(gameObject.containsAll(enemies));
        enemies.forEach(e -> {
            assertEquals(e.getStats().getLife(), e.getStats().getFullLife());
            assertEquals(e.getPosition(), ((BaseEnemyStatistics) e.getStats()).getInitialPos());
            assertFalse(this.manager.getTimerName((Enemy) e, TimerPrefix.NO_AGGRO).isEmpty());
        });
        final var flying = List.of(this.lotawiec, this.tengu);
        flying.forEach(e -> {
            assertFalse(this.manager.getTimerName((Enemy) e, TimerPrefix.ATTACK).isEmpty());
        });
    }

    /**
     * Tests the drop system of enemies.
     *
     * <p>
     * It simulates multiple kills to verify:
     * <ul>
     * <li>Drop probability roughly matches expected probability</li>
     * <li>Each buff type is generated with the expected relative frequency</li>
     * </ul>
     */
    @Test
    void manageOfDrop() {
        // Test drop mechanics with a single enemy since all share the same logic
        final BaseEnemyDebug enemy = this.centaur;

        final Map<BuffNames, Double> buffMap = ((BaseEnemyStatistics) enemy.getStats()).getBuffMap();
        assertFalse(buffMap.isEmpty(), "Buff map should not be empty");

        // 1. Calculate the drop probability and relative probabilities for buffs
        final double maxWeight = buffMap.values().stream().mapToDouble(Double::doubleValue).max().orElseThrow();

        // Drop probability = max weight / 100
        // Because beyond maxWeight is "no drop"
        final double dropProbability = maxWeight / 100.0;

        // Relative probability of each buff when a drop occurs
        final double totalBuffWeight = buffMap.values().stream().mapToDouble(Double::doubleValue).sum();
        final Map<BuffNames, Double> normalizedBuffProb = new java.util.EnumMap<>(BuffNames.class);
        buffMap.forEach((buff, weight) -> normalizedBuffProb.put(buff, weight / totalBuffWeight));

        // 2. Simulate drops
        final int trials = ITERATION;
        final Map<BuffNames, Integer> buffCount = new java.util.EnumMap<>(BuffNames.class);
        int dropsCreated = 0;

        for (int i = 0; i < trials; i++) {
            enemy.dropBuff();
            final var drop = enemy.getDrop();
            if (drop.isPresent()) {
                dropsCreated++;
                final BuffNames generatedBuff = drop.get().getType();
                buffCount.merge(generatedBuff, 1, Integer::sum);
            }
        }

        // 3. Check drop rate roughly matches expected drop probability
        final double actualDropRate = (double) dropsCreated / trials;
        // 10% tolerance due to randomness
        assertTrue(Math.abs(actualDropRate - dropProbability) <= TOLL_1,
                () -> String.format(
                        "Drop rate %.2f does not match expected %.2f",
                        actualDropRate,
                        dropProbability));

        // 4. Check that each buff's relative probability is roughly correct
        final double totalGenerated = buffCount.values().stream().mapToInt(Integer::intValue).sum();
        normalizedBuffProb.forEach((buff, expectedRatio) -> {
            final double actualRatio = buffCount.getOrDefault(buff, 0) / totalGenerated;
            final double toleranceBuff = TOLL_2; // 15% tolerance for distribution randomness
            assertTrue(Math.abs(expectedRatio - actualRatio) < toleranceBuff,
                    () -> String.format(
                            "%s generated ratio %.2f does not match expected %.2f",
                            buff,
                            actualRatio,
                            expectedRatio));
        });
    }

    /**
     * Tests the NO_AGGRO timer behavior for all enemies.
     *
     * <p>
     * This test ensures:
     * <ul>
     * <li>Enemies regenerate to full life after the NO_AGGRO timer</li>
     * <li>Collision during the countdown interrupts regeneration</li>
     * </ul>
     */
    @Test
    void noAggroTimer() {
        final var enemies = List.of(this.imp, this.centaur, this.lotawiec, this.tengu);

        // 1. All enemies should have NO_AGGRO timer
        enemies.forEach(e -> assertFalse(this.manager.getTimerName((Enemy) e, TimerPrefix.NO_AGGRO).isEmpty()));

        // 2. Damage all enemies and wait for full regeneration
        enemies.forEach(e -> e.setDamagedLife(1));
        final long noAggro = ((BaseEnemyStatistics) this.centaur.getStats()).getNoAggro();
        this.time.waitUntilTimeout(noAggro + OFFSET_1); // extra buffer
        enemies.forEach(e -> assertTrue(e.isFull()));

        // 3. Damage again, but interrupt the NO_AGGRO cycle with a collision
        enemies.forEach(e -> e.setDamagedLife(1));
        this.time.waitUntilTimeout(noAggro - OFFSET_2); // leave buffer
        enemies.forEach(e -> e.onCollision(this.druid, Vector2.left()));
        this.time.waitUntilTimeout(OFFSET_3); // short wait, should not be full yet
        enemies.forEach(e -> assertFalse(e.isFull()));
    }

    /**
     * Tests that flying enemies (Tengu and Lotawiec) trigger their attacks
     * periodically
     * according to their internal attack timers.
     *
     * <p>
     * The test waits for at least two attack cycles and checks that
     * the number of attacks reported by {@code getNumberAttack()} increases
     * accordingly.
     */
    @Test
    void attackTimer() throws InterruptedException {
        final var enemies = List.of(this.tengu, this.lotawiec);

        // Determine the max attack interval among the two enemies
        final long maxAttackInterval = enemies.stream()
                .mapToLong(e -> ((LongRangeEnemyStatistics) e.getStats()).getTimeAttack())
                .max()
                .orElseThrow();

        // Wait for at least one attack cycle for the slowest enemy
        this.time.waitUntilTimeout(maxAttackInterval + OFFSET_1);

        // Check that both enemies have triggered at least one attack
        enemies.forEach(enemy -> {
            int attacks = 0;
            if (enemy instanceof TenguDebug) {
                attacks = ((TenguDebug) enemy).getNumberAttack();
            } else if (enemy instanceof LotawiecDebug) {
                attacks = ((LotawiecDebug) enemy).getNumberAttack();
            }
            assertTrue(attacks > 0,
                    "Expected at least one attack from " + enemy.getClass().getSimpleName() + " but got " + attacks);
        });

        // Wait for another cycle to ensure repeated triggering
        this.time.waitUntilTimeout(maxAttackInterval + OFFSET_1);

        enemies.forEach(enemy -> {
            int attacks = 0;
            if (enemy instanceof TenguDebug) {
                attacks = ((TenguDebug) enemy).getNumberAttack();
            } else if (enemy instanceof LotawiecDebug) {
                attacks = ((LotawiecDebug) enemy).getNumberAttack();
            }
            assertTrue(attacks > 1,
                    "Expected multiple attacks from " + enemy.getClass().getSimpleName() + " but got " + attacks);
        });
    }
}
