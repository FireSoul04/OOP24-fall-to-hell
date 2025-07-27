package it.unibo.falltohell;

import it.unibo.falltohell.model.api.gameobject.GameObject;
import it.unibo.falltohell.model.api.level.Level;
import it.unibo.falltohell.model.api.gameobject.movable.entity.character.Character;
import it.unibo.falltohell.model.api.gameobject.movable.entity.enemy.Enemy;
import it.unibo.falltohell.model.api.factory.StatisticsFactory;
import it.unibo.falltohell.model.api.statistic.BaseEnemyStatistics;
import it.unibo.falltohell.model.impl.GameDataImpl;
import it.unibo.falltohell.model.impl.manager.GameEventManagerImpl;
import it.unibo.falltohell.model.impl.gameobject.movable.projectile.Knife;
import it.unibo.falltohell.model.impl.factory.StatisticFactoryImpl;
import it.unibo.falltohell.model.impl.gameobject.movable.entity.character.Rogue;
import it.unibo.falltohell.test.util.DummyEnemyTest;
import it.unibo.falltohell.test.util.LevelTest;
import it.unibo.falltohell.util.Dimensions;
import it.unibo.falltohell.util.Vector2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertTrue;

class TestRogueAbilities {

    private static final int STEPS = 500;

    private Character rogue;
    private Level level;
    private int steps = 0;

    /**
     * Initiate all variables for the test.
     */
    @BeforeEach
    void initialization() {
        this.level = new LevelTest();
        this.level.getGameEventManager().addCondition("ActiveAbility", () -> steps < 1);
        this.rogue = new Rogue(this.level, Vector2.zero());
        this.level.linkGameData(new GameDataImpl(Map.of(this.rogue.getCharacterID(), this.rogue)));
    }

    /**
     * Test to check if the knives are not colliding with each other and spread in the correct directions.
     */
    @Test
    void TestKnifeThrowDirections() {
        // Do a single update to save the knives starting positions
        this.level.update(1.0);
        steps++;
        final List<Vector2> startingKnivesPositions = this.level.getGameObjects()
            .stream()
            .filter(t -> t instanceof Knife)
            .map(GameObject::getPosition)
            .toList();
        while (steps < STEPS) {
            this.level.update(1.0);
            steps++;
        }
        final List<Vector2> knives = this.level.getGameObjects()
            .stream()
            .filter(t -> t instanceof Knife)
            .map(GameObject::getPosition)
            .toList();
        assertTrue(
            knives.get(0).x() > startingKnivesPositions.get(0).x() &&
                knives.get(0).y() == startingKnivesPositions.get(0).y(),
            "First knife should move only in the x axes"
        );
        assertTrue(
            knives.get(1).x() > startingKnivesPositions.get(1).x() &&
                knives.get(1).y() > startingKnivesPositions.get(1).y(),
            "Second knife should move down and right"
        );
        assertTrue(
            knives.get(2).x() > startingKnivesPositions.get(2).x() &&
                knives.get(2).y() < startingKnivesPositions.get(2).y(),
            "Third knife should move up and right"
        );
    }

    @Test
    void TestKnifeDamageOnEnemy() {
        final Vector2 enemyPosition = new Vector2(100.0, 0.0);
        final StatisticsFactory sf = new StatisticFactoryImpl();
        final BaseEnemyStatistics enemyStats = sf.createBaseEnemyStatistic(
            10, 0, Vector2.zero(), new Dimensions(5, 5),
            enemyPosition, this.rogue, 1, sf.createOptional()
        );
        final Enemy dummy = new DummyEnemyTest(this.level, Vector2.zero(), enemyStats);
        final double initialLife = dummy.getStats().getLife();
        while (steps < STEPS) {
            this.level.update(1.0);
            steps++;
        }
        assertTrue(dummy.getStats().getLife() < initialLife, "The enemy should be hit and take damage");
    }
}
