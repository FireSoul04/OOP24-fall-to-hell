package it.unibo.falltohell.model.impl.gameobjects.movable.entity;

/**
 * Class containing every statistic of an entity.
 * Every statistic is updatable.
 *
 * @author Davide Mancini
 */
public class Statistics {

	private int life;
	private int attack;
	private int speed;
	private int mana;
	private int attackSpeed;

	/**
	 * Create new statistics with the parameters specified.
	 * @param life
	 * @param attack
	 * @param speed
	 * @param mana
	 * @param attackSpeed
	 */
	public Statistics(final int life, final int attack, final int speed, final int mana, final int attackSpeed) {
		this.life = life;
		this.attack = attack;
		this.speed = speed;
		this.mana = mana;
		this.attackSpeed = attackSpeed;
	}

	/**
	 * @return current life of entity
	 */
	public int getLife() {
		return this.life;
	}

	/**
	 * @param life to be updated
	 */
	public void setLife(final int life) {
		this.life = life;
	}

	/**
	 * @return current attack of entity
	 */
	public int getAttack() {
		return this.attack;
	}

	/**
	 * @param attack to be updated
	 */
	public void setAttack(final int attack) {
		this.attack = attack;
	}

	/**
	 * @return current speed of entity
	 */
	public int getSpeed() {
		return this.speed;
	}

	/**
	 * @param speed to be updated
	 */
	public void setSpeed(final int speed) {
		this.speed = speed;
	}

	/**
	 * @return mana attack of entity
	 */
	public int getMana() {
		return this.mana;
	}

	/**
	 * @param mana to be updated
	 */
	public void setMana(final int mana) {
		this.mana = mana;
	}

	/**
	 * @return current attack speed of entity
	 */
	public int getAttackSpeed() {
		return this.attackSpeed;
	}

	/**
	 * @param attackSpeed to be updated
	 */
	public void setAttackSpeed(final int attackSpeed) {
		this.attackSpeed = attackSpeed;
	}
}
