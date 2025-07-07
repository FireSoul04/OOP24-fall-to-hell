package it.unibo.falltohell.model.impl;

import it.unibo.falltohell.model.api.EnemyFactory;
import it.unibo.falltohell.model.api.Level;
import it.unibo.falltohell.model.api.gameobjects.movable.entity.Enemy;
import it.unibo.falltohell.model.impl.gameobjects.movable.entity.enemy.Monster1;
import it.unibo.falltohell.model.impl.gameobjects.movable.entity.enemy.Monster2;
import it.unibo.falltohell.model.util.Vector2;
import it.unibo.falltohell.model.api.gameobjects.movable.entity.Character;

/**
 * Implements abstract factory for Enemies, creates Enemies
 * 
 * @author Sara Visani
 */
public class EnemyFactoryImpl implements EnemyFactory {

    /**
     * {@inheritDoc}
     */
    @Override
    public Enemy CreateMonster1(final Level level, final Vector2 initialCords, final Character character) {
        return new Monster1(level, initialCords, character);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Enemy CreateMonster2(final Level level, final Vector2 initialCords, final Character character) {
        return new Monster2(level, initialCords, character);
    }

}
