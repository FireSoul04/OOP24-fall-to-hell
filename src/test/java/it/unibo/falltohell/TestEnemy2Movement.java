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

public class TestEnemy2Movement {

    Level lv = new LevelTest();
    EnemyFactory fact;
    Character chara1;
    Character chara2;
    Character chara1_2;
    Enemy en1;
    Enemy en2;
    Enemy en3;
    Enemy en4;

    @BeforeEach
    public void setUp(){
        fact = new EnemyFactoryImpl();
        chara1 = new Druid(lv, new Vector2(-50, 0), "druid.png");
        chara1_2 = new Druid(lv, new Vector2(-45, 0), "druid.png");
        chara2 = new Druid(lv, new Vector2(5, 0), "druid.png");
        en1 = fact.createTengu(lv, Vector2.zero(), chara1);
        en2 = fact.createTengu(lv, new Vector2(-30, 0), chara1);
        en3 = fact.createTengu(lv, new Vector2(-40, 0), chara1);
        en4 = fact.createTengu(lv, new Vector2(10, 0), chara1);
    }

    /*Testing Movement with Monster 2 to far away from Player*/

    @Test
    void justMonster2(){
        en1.update(0);
        assertEquals(en1.getPosition(),Vector2.zero());
        en1.update(10);
        assertEquals(en1.getPosition(),new Vector2(10,0));
        en1.update(30);
        assertEquals(en1.getPosition(),new Vector2(0,0));
        en1.update(0);
        assertEquals(en1.getPosition(),new Vector2(0,0));
        en1.update(5);
        assertEquals(en1.getPosition(),new Vector2(5,0));
        en1.update(20);
        assertEquals(en1.getPosition(),new Vector2(-5,0));
    }

    @Test
    void justMonster2NotZeroPos(){
        en4.update(0);
        assertEquals(en4.getPosition(),new Vector2(10, 0));
        en4.update(10);
        assertEquals(en4.getPosition(),new Vector2(20,0));
        en4.update(30);
        assertEquals(en4.getPosition(),new Vector2(10,0));
        en4.update(0);
        assertEquals(en4.getPosition(),new Vector2(10,0));
        en4.update(5);
        assertEquals(en4.getPosition(),new Vector2(15,0));
        en4.update(20);
        assertEquals(en4.getPosition(),new Vector2(5,0));
    }

    @Test
    void justMonster2NotZeroNeg(){
        en3.setCharacter(chara2);
        en3.update(0);
        assertEquals(en3.getPosition(),new Vector2(-40, 0));
        en3.update(10);
        assertEquals(en3.getPosition(),new Vector2(-30,0));
        en3.update(30);
        assertEquals(en3.getPosition(),new Vector2(-40,0));
        en3.update(0);
        assertEquals(en3.getPosition(),new Vector2(-40,0));
        en3.update(5);
        assertEquals(en3.getPosition(),new Vector2(-35,0));
        en3.update(20);
        assertEquals(en3.getPosition(),new Vector2(-45,0));
    }

    /*Testing Movement with Monster 2 with Player just close enough*/

    @Test
    void Monster2EqualsCharacterNotReachable(){
        en2.update(0);
        assertEquals(en2.getPosition(),new Vector2(-30, 0));
        en2.update(1);
        assertEquals(en2.getPosition(),new Vector2(-31,0));
        en2.update(5);
        assertEquals(en2.getPosition(),new Vector2(-36,0));
        en2.update(6);
        assertEquals(en2.getPosition(),new Vector2(-40,0));
        en2.update(0);
        assertEquals(en2.getPosition(),new Vector2(-40,0));
        en2.update(30);
        assertEquals(en2.getPosition(),new Vector2(-40,0));
    }

    /*Testing Movement with Monster 2 with Player are close*/

    @Test
    void Monster2CloseCharacterLimitReachable(){
        en3.update(0);
        assertEquals(en3.getPosition(),new Vector2(-40, 0));
        en3.update(1);
        assertEquals(en3.getPosition(),new Vector2(-41,0));
        en3.update(5);
        assertEquals(en3.getPosition(),new Vector2(-46,0));
        en3.update(0);
        assertEquals(en3.getPosition(),new Vector2(-46,0));
        en3.update(30);
        assertEquals(en3.getPosition(),new Vector2(-50,0));
    }

    /*Testing Movement with Monster 2 with Player are close*/

    @Test
    void Monster2CloseCharacterReachable(){
        en3.setCharacter(chara1_2);
        en3.update(0);
        assertEquals(en3.getPosition(),new Vector2(-40, 0));
        en3.update(1);
        assertEquals(en3.getPosition(),new Vector2(-41,0));
        en3.update(5);
        assertEquals(en3.getPosition(),new Vector2(-45,0));
        en3.update(0);
        assertEquals(en3.getPosition(),new Vector2(-45,0));
        en3.update(30);
        assertEquals(en3.getPosition(),new Vector2(-45,0));
    }

    /*Testing Movement with Monster 2 to far away from Player and hits wall*/

    @Test
    void hitsMonster2(){
        en1.update(5);
        assertEquals(en1.getPosition(),new Vector2(5,0));
        en1.onCollision(chara1, Vector2.zero());
        en1.update(30);
        assertEquals(en1.getPosition(),new Vector2(5,0));
        en1.update(20);
        assertEquals(en1.getPosition(),new Vector2(-5,0));
    }

    /*Testing Movement with Monster 2 just close enough and hits a wall*/

    @Test
    void hitsMonster2EqualsCharacterNegative(){
        en2.update(0);
        assertEquals(en2.getPosition(),new Vector2(-30, 0));
        en2.update(5);
        assertEquals(en2.getPosition(),new Vector2(-35,0));
        en2.onCollision(chara1, Vector2.zero());
        en2.update(30);
        assertEquals(en2.getPosition(),new Vector2(-35,0));
        en2.update(0);
        assertEquals(en2.getPosition(),new Vector2(-35,0));
        en2.update(10);
        assertEquals(en2.getPosition(),new Vector2(-35,0));
    }

    /*Testing Movement with Monster 2 just close enough Player and hits wall*/

    @Test
    void hitsMonster2EqualsCharacterPositive(){
        en1.setCharacter(chara2);
        en1.update(2);
        assertEquals(en1.getPosition(),new Vector2(2,0));
        en1.onCollision(chara1, Vector2.zero());
        en1.update(10);
        assertEquals(en1.getPosition(),new Vector2(2,0));
        en1.update(30);
        assertEquals(en1.getPosition(),new Vector2(2,0));
        en1.update(0);
        assertEquals(en1.getPosition(),new Vector2(2,0));
    }
}
