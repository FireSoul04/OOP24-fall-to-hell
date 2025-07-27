package it.unibo.falltohell;

import it.unibo.falltohell.model.impl.GameDataImpl;
import it.unibo.falltohell.model.impl.factory.EnemyFactoryImpl;
import it.unibo.falltohell.model.impl.manager.EnemyTimeManagerImpl;
import it.unibo.falltohell.model.impl.physics.BoxCollider;
import it.unibo.falltohell.test.util.LevelTest;
import it.unibo.falltohell.model.impl.gameobject.block.BaseCollidableBlock;
import it.unibo.falltohell.model.impl.gameobject.movable.entity.character.*;
import it.unibo.falltohell.model.impl.gameobject.movable.entity.enemy.Lotawiec;
import it.unibo.falltohell.util.Dimensions;
import it.unibo.falltohell.util.Vector2;
import it.unibo.falltohell.model.api.gameobject.movable.entity.character.Character;
import it.unibo.falltohell.model.api.gameobject.movable.entity.enemy.Enemy;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.falltohell.model.api.GameData;
import it.unibo.falltohell.model.api.factory.EnemyFactory;
import it.unibo.falltohell.model.api.level.Level;

public class TestEnemyTimers {
    Level lv;
    EnemyFactory f;
    Druid character;
    Enemy e1;
    Enemy e2;
    Enemy e3;
    Enemy e4;
    EnemyTimeManagerImpl manager;
    BaseCollidableBlock block;

    @BeforeEach
    public void setUp() {
        lv = new LevelTest();
        f = new EnemyFactoryImpl();
        character = new Druid(lv, new Vector2(30, 10));
        block = new BaseCollidableBlock(lv, new Vector2(20, 7), new BoxCollider(new Dimensions(10,10)), "base_block.png");
        GameData gameData = new GameDataImpl(Map.of(Character.CharacterID.DRUID, character));
        lv.linkGameData(gameData);
        e1 = f.createCentaur(lv, Vector2.zero(), character);
        e2 = f.createImp(lv, Vector2.zero(), character);
        e3 = f.createLotawiec(lv, Vector2.zero(), character);
        e4 = f.createTengu(lv, Vector2.zero(), character);
        manager = ((Lotawiec)e3).getTimerManager();
    }

    @Test
    void death(){
        assertEquals(0, character.getkill());
        System.out.println(manager.getNameTimers(e1));
        System.out.println(manager.getNameTimers(e2));
        System.out.println(manager.getNameTimers(e3));
        System.out.println(manager.getNameTimers(e4));
        assertEquals(1, manager.getNameTimers(e1).size());
        assertEquals(1, manager.getNameTimers(e2).size());
        assertEquals(2, manager.getNameTimers(e3).size());
        assertEquals(2, manager.getNameTimers(e4).size());

        e1.setDamagedLife(e1.getStats().getFullLife());
        assertEquals(1, character.getkill());
        assertTrue(manager.getNameTimers(e1).isEmpty());

        e2.setDamagedLife(e2.getStats().getFullLife());
        assertEquals(2, character.getkill());
        assertTrue(manager.getNameTimers(e1).isEmpty());

        e3.setDamagedLife(e3.getStats().getFullLife());
        assertEquals(3, character.getkill());
        assertTrue(manager.getNameTimers(e1).isEmpty());

        e4.setDamagedLife(e4.getStats().getFullLife());
        assertEquals(4, character.getkill());
        assertTrue(manager.getNameTimers(e1).isEmpty());

    }

    @Test
    void movement(){

        e3.onCollision(block, Vector2.right());
        e3.update(10);
        assertEquals(new Vector2(20, 0), e3.getPosition());
    }

}
