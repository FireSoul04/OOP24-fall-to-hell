package it.unibo.falltohell;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.falltohell.model.api.factory.EnemyFactory;
import it.unibo.falltohell.model.api.gameobject.GameObject;
import it.unibo.falltohell.model.api.gameobject.movable.entity.enemy.Enemy;
import it.unibo.falltohell.model.api.level.Level;
import it.unibo.falltohell.model.api.statistic.BaseEnemyStatistics;
import it.unibo.falltohell.model.impl.GameDataImpl;
import it.unibo.falltohell.model.impl.gameobject.movable.entity.enemy.BaseEnemy.BuffNames;
import it.unibo.falltohell.test.util.LevelTest;
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

class TestEnemy {
    private Level lv;
    private EnemyFactory factory;
    private BaseEnemyDebug centaur, imp, tengu, lotawiec;
    private DruidDebug druid;
    private EnemyTimerManagerDebug manager;
    private SafeZoneManagerDebug safezone;

    @BeforeEach
    void setUp() {
        this.lv = new LevelTest();
        this.druid = new DruidDebug(lv, Vector2.zero());
        this.lv.linkGameData(new GameDataImpl(
                Map.of(this.druid.getCharacterID(), this.druid)));
        this.lv.getGameData().changeCurrentCharacter(this.druid);
        this.factory = new EnemyFactoryDebug();
        this.centaur = (CentaurDebug) this.factory.createCentaur(lv, Vector2.zero());
        this.imp = (ImpDebug) this.factory.createImp(lv, Vector2.zero());
        this.tengu = (TenguDebug) this.factory.createTengu(lv, Vector2.zero());
        this.lotawiec = (LotawiecDebug) this.factory.createLotawiec(lv, Vector2.zero());
        this.manager = (EnemyTimerManagerDebug) this.centaur.getEnemyTimerManager();
        this.safezone = (SafeZoneManagerDebug) this.centaur.getSafeZoneManager();
    }

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
        assertFalse(this.safezone.equals(null));
    }

    @Test
    void removeEnemy() {
        final var enemies = List.of(this.imp, this.centaur, this.lotawiec, this.tengu);
        enemies.forEach(e -> e.setDamagedLife(e.getStats().getFullLife()));
        final List<GameObject> gameObject = lv.getGameObjects();
        assertFalse(gameObject.containsAll(enemies));
        enemies.forEach(e -> {
            this.manager.getEnemyTimers().keySet().forEach(k -> assertFalse(k.equals(e)));
        });
    }

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

    @Test
    void manageOfDrop() {
        final var enemies = List.of(this.imp, this.centaur, this.lotawiec, this.tengu);

        // We will test that drop is created and follows the buff probability
        // distribution
        enemies.forEach(enemy -> {
            final Map<BuffNames, Double> buffMap = ((BaseEnemyStatistics)enemy.getStats()).getBuffMap();
            assertFalse(buffMap.isEmpty(), "Buff map should not be empty");

            int trials = 1000; // Number of times to trigger the drop
            Map<BuffNames, Integer> buffCount = new java.util.HashMap<>();
            int dropsCreated = 0;

            for (int i = 0; i < trials; i++) {
                enemy.dropBuff();
                final var drop = enemy.getDrop();
                if (!Optional.empty().equals(drop)) {
                    dropsCreated++;
                    BuffNames generatedBuff = drop.get().getType();
                    buffCount.merge(generatedBuff, 1, Integer::sum);
                }
            }

            assertTrue(dropsCreated > 0,
                    "At least one drop should be created for " + enemy.getClass().getSimpleName());

            // Check that generated buffs roughly follow the weighted probabilities
            double totalGenerated = buffCount.values().stream().mapToInt(Integer::intValue).sum();
            buffMap.forEach((buff, weight) -> {
                double expectedRatio = weight / 100.0;
                double actualRatio = buffCount.getOrDefault(buff, 0) / totalGenerated;

                // Allow some tolerance due to randomness
                double tolerance = 0.15;
                assertTrue(Math.abs(expectedRatio - actualRatio) < tolerance,
                        () -> String.format(
                                "%s generated ratio %.2f does not match expected %.2f for %s",
                                enemy.getClass().getSimpleName(),
                                actualRatio,
                                expectedRatio,
                                buff));
            });
        });
    }

}
