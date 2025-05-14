package it.unibo.falltohell.model.impl;

import it.unibo.falltohell.model.api.EnemyFactory;
import it.unibo.falltohell.model.api.gameobjects.movable.entity.Enemy;
import it.unibo.falltohell.model.impl.gameobjects.movable.character.Entity.Enemy.Monster1;
import it.unibo.falltohell.model.impl.gameobjects.movable.character.Entity.Enemy.Monster2;
import it.unibo.falltohell.model.util.Vector2;

public class EnemyFactoryImpl implements EnemyFactory{

    @Override
    public Enemy CreateMonster1(Vector2 initialCords) {
        return new Monster1(initialCords);
    }

    @Override
    public Enemy CreateMonster2(Vector2 initialCords) {
        return new Monster2(initialCords);
    }
    
}
