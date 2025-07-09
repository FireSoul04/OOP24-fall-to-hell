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
 */
public class CharacterStatisticsImpl extends StatisticsImpl implements CharacterStatistics{

	private final double initialMana;
	private final double initialAttackSpeed;
	private double mana;
	private double attackSpeed;

	/**
	 * Create new statistics with the parameters specified.
	 * @param life
	 * @param attack
	 * @param speed
	 * @param dimensions
	 * @param mana
	 * @param attackSpeed
	 */
	public CharacterStatisticsImpl(final double life, final double attack, final Vector2 speed, final Dimensions dimensions, final double mana, final double attackSpeed) {

		super(life,attack,speed,dimensions);
		this.initialMana = mana;
		this.mana = mana;
		this.initialAttackSpeed = attackSpeed;
		this.attackSpeed = attackSpeed;
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
	@Override
	public void addMana(double mana) {
		this.mana += mana;
	}

	/**
     * {@inheritDoc}
     */
	@Override
	public void subMana(double mana) {
		this.addMana(-mana);
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
	public double getAttackSpeed() {
		return this.attackSpeed;
	}

	/**
     * {@inheritDoc}
     */
	public void setAttackSpeed(final double attackSpeed) {
		this.attackSpeed = attackSpeed;
	}

	/**
     * {@inheritDoc}
     */
	@Override
	public void addAttackSpeed(double attackSpeed) {
		this.attackSpeed += attackSpeed;
	}

	/**
     * {@inheritDoc}
     */
	@Override
	public void subAttackSpeed(double attackSpeed) {
		this.addAttackSpeed(-attackSpeed);
	}
}
