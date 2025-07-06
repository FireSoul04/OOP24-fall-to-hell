package it.unibo.falltohell.model.impl.gameobjects.movable.entity.statistics;

import it.unibo.falltohell.model.api.gameobjects.movable.entity.statistic.CharacterStatistics;
import it.unibo.falltohell.model.impl.gameobjects.movable.entity.StatisticsImpl;
import it.unibo.falltohell.model.util.Dimensions;
import it.unibo.falltohell.model.util.Vector2;

/**
 * Class containing every statistic of an entity.
 * Every statistic is updatable.
 *
 * @author Davide Mancini
 */
public class CharacterStatisticsImpl extends StatisticsImpl implements CharacterStatistics{

	private double mana;
	private Vector2 attackSpeed;

	/**
	 * Create new statistics with the parameters specified.
	 * @param life
	 * @param attack
	 * @param speed
	 * @param dimensions
	 * @param mana
	 * @param attackSpeed
	 */
	public CharacterStatisticsImpl(final double life, final double attack, final Vector2 speed, final Dimensions dimensions, final double mana, final Vector2 attackSpeed) {
		
		super(life,attack,speed,dimensions);
		this.mana = mana;
		this.attackSpeed = attackSpeed;
	}

	/**
     * {@inheritDoc}
     */
	public double getMana() {
		return this.mana;
	}

	/**
     * {@inheritDoc}
     */
	public void setMana(final double mana) {
		this.mana = mana;
	}

	/**
     * {@inheritDoc}
     */
	public Vector2 getAttackSpeed() {
		return this.attackSpeed;
	}

	/**
     * {@inheritDoc}
     */
	public void setAttackSpeed(final Vector2 attackSpeed) {
		this.attackSpeed = attackSpeed;
	}
}
