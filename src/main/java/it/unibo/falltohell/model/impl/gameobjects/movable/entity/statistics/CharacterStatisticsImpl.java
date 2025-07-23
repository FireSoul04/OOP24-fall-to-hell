package it.unibo.falltohell.model.impl.gameobjects.movable.entity.statistics;

import it.unibo.falltohell.model.api.gameobjects.movable.entity.statistic.CharacterStatistics;
import it.unibo.falltohell.model.impl.gameobjects.movable.entity.StatisticsImpl;
import it.unibo.falltohell.util.Dimensions;
import it.unibo.falltohell.util.Vector2;

/**
 * Class containing every statistic of an entity.
 * Every statistic is updatable.
 *
 * @author Davide Mancini
 * @author Sara Visani
 */
public class CharacterStatisticsImpl extends StatisticsImpl implements CharacterStatistics {

    private final double initialMana;
    private final double initialAttackSpeed;
    private double temporaryLife;
    private double mana;
    private double temporaryMana;
    private double attackSpeed;

    /**
     * Create new statistics with the parameters specified.
     *
     * @param life
     * @param attack
     * @param speed
     * @param dimensions
     * @param mana
     * @param attackSpeed
     */
    public CharacterStatisticsImpl(final double life, final double attack, final Vector2 speed,
            final Dimensions dimensions, final double mana, final double attackSpeed) {

        super(life, attack, speed, dimensions);
        this.temporaryLife = 0;
        this.temporaryMana = 0;
        this.initialMana = mana;
        this.mana = mana;
        this.initialAttackSpeed = attackSpeed;
        this.attackSpeed = attackSpeed;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getTemporaryLife() {
        return this.temporaryLife;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setTemporaryLife(final double temporaryLife) {
        this.temporaryLife = temporaryLife;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addTemporaryLife(final double temporaryLife) {
        this.temporaryLife = this.temporaryLife + temporaryLife;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void subTemporaryLife(final double temporaryLife) {
        this.addTemporaryLife(-temporaryLife);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getInitialMana() {
        return this.initialMana;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getMana() {
        return this.mana;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setMana(final double mana) {
        this.mana = mana;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addMana(final double mana) {
        this.mana += mana;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void subMana(final double mana) {
        this.addMana(-mana);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getTemporaryMana() {
        return this.temporaryMana;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setTemporaryMana(final double temporaryMana) {
        this.temporaryMana = temporaryMana;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addTemporaryMana(final double temporaryMana) {
        this.temporaryMana = this.temporaryMana + temporaryMana;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void subTemporaryMana(final double temporaryMana) {
        this.addTemporaryMana(-temporaryMana);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getInitialAttackSpeed() {
        return this.initialAttackSpeed;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getAttackSpeed() {
        return this.attackSpeed;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setAttackSpeed(final double attackSpeed) {
        this.attackSpeed = attackSpeed;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addAttackSpeed(final double attackSpeed) {
        this.attackSpeed += attackSpeed;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void subAttackSpeed(final double attackSpeed) {
        this.addAttackSpeed(-attackSpeed);
    }
}
