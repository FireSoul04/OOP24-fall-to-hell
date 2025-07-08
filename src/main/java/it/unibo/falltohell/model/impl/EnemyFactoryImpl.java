package it.unibo.falltohell.model.impl;

import it.unibo.falltohell.model.api.EnemyFactory;
import it.unibo.falltohell.model.api.Level;
import it.unibo.falltohell.model.api.gameobjects.movable.entity.Enemy;
import it.unibo.falltohell.model.impl.gameobjects.movable.entity.enemy.Centaur;
import it.unibo.falltohell.model.impl.gameobjects.movable.entity.enemy.Imp;
import it.unibo.falltohell.model.impl.gameobjects.movable.entity.enemy.Lotawiec;
import it.unibo.falltohell.model.impl.gameobjects.movable.entity.enemy.Tengu;
import it.unibo.falltohell.model.util.Vector2;
import it.unibo.falltohell.model.api.gameobjects.movable.entity.Character;

/**
 * Concrete implementation of the {@link EnemyFactory} interface.
 * <p>
 * This class is responsible for creating specific {@link Enemy} instances,
 * such as {@link Centaur}, {@link Imp}, {@link Lotawiec} and {@link Tengu}.
 * </p>
 *
 * @see Enemy
 * @see Centaur
 * @see Imp
 * @see Lotawiec
 * @see Tengu
 * @see Level
 * @see Character
 *
 * @author Sara Visani
 */
public class EnemyFactoryImpl implements EnemyFactory {

    /**
     * {@inheritDoc}
     */
    @Override
    public Enemy createCentaur(final Level level, final Vector2 initialCords, final Character character) {
        return new Centaur(level, initialCords, character);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Enemy createTengu(final Level level, final Vector2 initialCords, final Character character) {
        return new Tengu(level, initialCords, character);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Enemy createImp(Level level, Vector2 initialCords, Character character) {
        return new Imp(level, initialCords, character);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Enemy createLotawiec(Level level, Vector2 initialCords, Character character) {
        return new Lotawiec(level, initialCords, character);
    }

}
