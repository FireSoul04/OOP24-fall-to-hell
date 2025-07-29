package it.unibo.falltohell.model.impl.statistics;

import it.unibo.falltohell.model.api.statistic.Statistics;
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
     * If dimension has any negative component it will use 0 instead.
     *
     * @param life maximum life to the entity and should be positive
     * @param attack of the entity and should be positive
     * @param speed of the entity and can't have a negative component
     * @param dimension of the entity's collider
     */
    protected StatisticsImpl(final double life, final double attack, final Vector2 speed, final Dimensions dimension) {
        this.checkPositiveAmountOrThrow(life);
        this.checkPositiveAmountOrThrow(attack);
        this.checkNonNegativeAmountOrThrow(speed.x());
        this.checkNonNegativeAmountOrThrow(speed.y());
        this.fullLife = life;
        this.life = life;
        this.initialAttack = attack;
        this.attack = attack;
        this.initialSpeed = speed;
        this.speed = speed;
        this.dimensions = new Dimensions(Math.max(dimension.width(), 0), Math.max(dimension.height(), 0));
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
        this.checkPositiveAmountOrThrow(life);
        this.life = Math.min(life, this.fullLife);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addLife(final double life) {
        this.checkPositiveAmountOrThrow(life);
        this.setLife(this.life + life);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void subLife(final double life) {
        this.checkPositiveAmountOrThrow(life);
        this.life = Math.max(this.life - life, 0);
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
        this.checkPositiveAmountOrThrow(attack);
        this.attack = attack;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addAttack(final double attack) {
        this.checkPositiveAmountOrThrow(attack);
        this.setAttack(this.attack + attack);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void subAttack(final double attack) {
        this.checkPositiveAmountOrThrow(attack);
        this.setAttack(this.attack - attack);
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

    /**
     * Check if amount is positive or else throw an IllegalStateException.
     *
     * @param amount to check
     * @throws IllegalStateException if amount is not positive
     */
    protected final void checkPositiveAmountOrThrow(final double amount) {
        this.checkFiniteAmountOrThrow(amount);
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount should be positive");
        }
    }

    /**
     * Check if amount is non-negative or else throw an IllegalStateException.
     *
     * @param amount to check
     * @throws IllegalStateException if amount is negative
     */
    protected final void checkNonNegativeAmountOrThrow(final double amount) {
        this.checkFiniteAmountOrThrow(amount);
        if (amount < 0) {
            throw new IllegalArgumentException("Amount should be non-negative");
        }
    }

    /**
     * Check if amount is finite or else throw an IllegalStateException.
     *
     * @param amount to check
     * @throws IllegalStateException if amount is negative
     */
    protected final void checkFiniteAmountOrThrow(final double amount) {
        if (!Double.isFinite(amount)) {
            throw new IllegalArgumentException("Amount should be finite");
        }
    }
}
