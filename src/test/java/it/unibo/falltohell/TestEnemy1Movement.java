package it.unibo.falltohell;

import it.unibo.falltohell.model.api.EnemyFactory;
import it.unibo.falltohell.model.api.Level;
import it.unibo.falltohell.model.api.gameobjects.movable.entity.Character;
import it.unibo.falltohell.model.api.gameobjects.movable.entity.Enemy;
import it.unibo.falltohell.model.impl.EnemyFactoryImpl;
import it.unibo.falltohell.model.impl.gameobjects.movable.entity.character.Druid;
import it.unibo.falltohell.test.util.LevelTest;
import it.unibo.falltohell.util.Vector2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TestEnemy1Movement {

    Level lv = new LevelTest();
    EnemyFactory fact;
    Character chara1;
    Character chara2;
    Enemy en1;
    Enemy en2;
    Enemy en3;

    @BeforeEach
    public void setUp(){
        fact = new EnemyFactoryImpl();
        chara1 = new Druid(lv, new Vector2(-100, 0));
        chara2 = new Druid(lv, new Vector2(100, 0));
        en1 = fact.createCentaur(lv, Vector2.zero(), chara1);
        en2 = fact.createCentaur(lv, new Vector2(-30, 0), chara1);
        en3 = fact.createCentaur(lv, new Vector2(-40, 0), chara1);
    }

    /*Testing Movement with Monster 1 to far away from Player*/

    @Test
    void justMonster1(){
        en1.update(0);
        assertEquals(en1.getPosition(),Vector2.zero());
        en1.update(10);
        assertEquals(en1.getPosition(),new Vector2(20,0));
        en1.update(30);
        assertEquals(en1.getPosition(),new Vector2(80,0));
        en1.update(0);
        assertEquals(en1.getPosition(),new Vector2(80,0));
    }

    /*Testing Movement with Monster 1 with Player just close enough*/

    @Test
    void Monster1EqualsCharacter(){
        en2.update(0);
        assertEquals(en2.getPosition(),new Vector2(-30, 0));
        en2.update(10);
        assertEquals(en2.getPosition(),new Vector2(-50,0));
        en2.update(30);
        assertEquals(en2.getPosition(),new Vector2(-110,0));
        en2.update(0);
        assertEquals(en2.getPosition(),new Vector2(-110,0));
        en2.update(10);
        assertEquals(en2.getPosition(),new Vector2(-90,0));
    }

    /*Testing Movement with Monster 1 with Player are close*/

    @Test
    void Monster1CloseCharacter(){
        en3.update(0);
        assertEquals(en3.getPosition(),new Vector2(-40, 0));
        en3.update(10);
        assertEquals(en3.getPosition(),new Vector2(-60,0));
        en3.update(30);
        assertEquals(en3.getPosition(),new Vector2(-120,0));
        en3.update(0);
        assertEquals(en3.getPosition(),new Vector2(-120,0));
        en3.update(10);
        assertEquals(en3.getPosition(),new Vector2(-100,0));
    }

    /*Testing Movement with Monster 1 to far away from Player and hits wall*/

    @Test
    void hitsMonster1(){
        en1.setCharacter(chara2);
        en1.onCollision(chara1, Vector2.zero());
        en1.update(10);
        assertEquals(en1.getPosition(),new Vector2(-20,0));
        en1.update(30);
        assertEquals(en1.getPosition(),new Vector2(-80,0));
        en1.update(0);
        assertEquals(en1.getPosition(),new Vector2(-80,0));
    }

    /*Testing Movement with Monster 1 just close enough and hits a wall*/

    @Test
    void hitsMonster1EqualsCharacterNegative(){
        en2.onCollision(chara1, Vector2.zero());
        en2.update(0);
        assertEquals(en2.getPosition(),new Vector2(-30, 0));
        en2.update(10);
        assertEquals(en2.getPosition(),new Vector2(-30,0));
        en2.update(30);
        assertEquals(en2.getPosition(),new Vector2(-30,0));
        en2.update(0);
        assertEquals(en2.getPosition(),new Vector2(-30,0));
        en2.update(10);
        assertEquals(en2.getPosition(),new Vector2(-30,0));
    }

    /*Testing Movement with Monster 1 just close enough Player and hits wall*/

    @Test
    void hitsMonster1EqualsCharacterPositive(){
        en1.setCharacter(chara2);
        en1.update(15);
        assertEquals(en1.getPosition(),new Vector2(30,0));
        en1.onCollision(chara1, Vector2.zero());
        en1.update(10);
        assertEquals(en1.getPosition(),new Vector2(30,0));
        en1.update(30);
        assertEquals(en1.getPosition(),new Vector2(30,0));
        en1.update(0);
        assertEquals(en1.getPosition(),new Vector2(30,0));
    }
}
