package it.unibo.falltohell.model.impl.gameobjects.movable.entity;

import it.unibo.falltohell.model.api.gameobjects.movable.entity.Statistics;
import it.unibo.falltohell.util.Dimensions;
import it.unibo.falltohell.util.Vector2;

/**
 * Class containing implementation for statistics
 *
 * @author Davide Mancini
 * @author Sara Visani
 */
public class StatisticsImpl implements Statistics{
    
    private double life;
	private double attack;
	private Vector2 speed;
    final private Dimensions dimensions;

    /**
     * Create new statistics with the parameters specified.
     * @param life
     * @param attack
     * @param speed
     * @param dimension
     */
    protected StatisticsImpl(final double life, final double attack, final Vector2 speed, final Dimensions dimension){
        this.life = life;
        this.attack = attack;
        this.speed = speed;
        this.dimensions = dimension;
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
    public void subLife(final double life){
        this.addLife(-life);
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
    public Dimensions getDimensions() {
        return this.dimensions;
    }
}
