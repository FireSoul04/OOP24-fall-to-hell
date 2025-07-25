package it.unibo.falltohell.model.impl.gameobjects.movable.entity;

import it.unibo.falltohell.model.api.gameobjects.movable.entity.Statistics;
import it.unibo.falltohell.util.Dimensions;
import it.unibo.falltohell.util.Vector2;

/**
 * Class containing implementation for statistics.
 *
 * @author Davide Mancini
 * @author Sara Visani
 */
public class StatisticsImpl implements Statistics {

    private final double fullLife;
    private final double initialAttack;
    private final Vector2 initialSpeed;
    private double life;
    private double attack;
    private Vector2 speed;
    private final Dimensions dimensions;

    /**
     * Create new statistics with the parameters specified.
     *
     * @param life
     * @param attack
     * @param speed
     * @param dimension
     */
    protected StatisticsImpl(final double life, final double attack, final Vector2 speed, final Dimensions dimension) {
        this.fullLife = life;
        this.life = life;
        this.initialAttack = attack;
        this.attack = attack;
        this.initialSpeed = speed;
        this.speed = speed;
        this.dimensions = dimension;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getFullLife() {
        return this.fullLife;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getLife() {
        return this.life;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setLife(final double life) {
        this.life = life;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addLife(final double life) {
        this.life = this.life + life;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void subLife(final double life) {
        this.addLife(-life);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getInitialAttack() {
        return this.initialAttack;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getAttack() {
        return this.attack;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setAttack(final double attack) {
        this.attack = attack;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addAttack(final double attack) {
        this.attack += attack;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void subAttack(final double attack) {
        this.addAttack(-attack);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Vector2 getInitialSpeed() {
        return this.initialSpeed;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Vector2 getSpeed() {
        return this.speed;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setSpeed(final Vector2 speed) {
        this.speed = speed;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addSpeed(final Vector2 speed) {
        this.speed = this.speed.add(speed);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void subSpeed(final Vector2 speed) {
        this.addSpeed(speed.invert());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Dimensions getDimensions() {
        return this.dimensions;
    }
}
