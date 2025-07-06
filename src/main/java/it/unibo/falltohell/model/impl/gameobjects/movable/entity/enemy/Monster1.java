package it.unibo.falltohell.model.impl.gameobjects.movable.entity.enemy;

import it.unibo.falltohell.model.api.gameobjects.Block;
import it.unibo.falltohell.model.api.gameobjects.movable.entity.Character;
import it.unibo.falltohell.model.api.gameobjects.movable.entity.statistic.BaseEnemyStatistics;
import it.unibo.falltohell.model.api.GameObject;
import it.unibo.falltohell.model.api.Level;
import it.unibo.falltohell.model.impl.CustomTimerImpl;
import it.unibo.falltohell.model.impl.gameobjects.movable.entity.BaseEnemy;
import it.unibo.falltohell.model.impl.gameobjects.movable.entity.StatisticFactoryImpl;
import it.unibo.falltohell.model.util.Dimensions;
import it.unibo.falltohell.model.util.Vector2;

/**
 * Implements abstract class Enemies, creates first type for enemies
 * @author Sara Visani
 */
public class Monster1 extends BaseEnemy{
    private static final Dimensions DIMENSIONS = new Dimensions(20, 20);
    private static final double FULL_LIFE = 20;
    private static final double DAMAGE = 20;
    private static final Vector2 VELOCITY = new Vector2(2, 20);
    private static final int NO_AGGRO = 10;

    private BaseEnemyStatistics stats;
    private int direction = 1;

    public Monster1(final Level level, final Vector2 initialCord, final Character character) {
        super(level, new StatisticFactoryImpl().createBaseEnemyStatistic(FULL_LIFE, DAMAGE, VELOCITY, DIMENSIONS, initialCord, NO_AGGRO, character));

        this.stats = (BaseEnemyStatistics)super.getStats();

        this.stats.getTm().addTimer(this.stats.getNo_aggroName(), new CustomTimerImpl(NO_AGGRO, () -> {if(this.isFull()){
                                                                                            if(this.stats.getLife()+this.stats.getLife()*0.1>this.stats.getFullLife()){
                                                                                                this.stats.setLife(this.stats.getFullLife());
                                                                                            }else{
                                                                                                this.stats.addLife(this.stats.getLife()*0.1);
                                                                                            }
                                                                                        }
                                                                                        this.stats.getTm().restartTimer(this.stats.getNo_aggroName());}));
    }

    /*
     * {@inheritDoc}
     */
    @Override
    public void update(final double deltaTime) {
        this.move(deltaTime);
    }

    /*
     * {@inheritDoc}
     */
    @Override
    public void onCollision(final GameObject other, final Vector2 direction) {
        if(other instanceof Block){
            if(direction.y() != 0){
                this.direction *= -1;
            }
        }else if(other instanceof Character){
            attack();
            //super.getTm().restart(getNo_aggro());
        }
        //TODO delete when the tests works without this
        this.direction *= -1;
    }

    /*
     * {@inheritDoc}
     */
    @Override
    public void setDamagedLife(final double damage){
        super.setDamagedLife(damage);
        //super.getTm().restart(getNo_aggro());
    }

    /*
     * {@inheritDoc}
     */
    @Override
    protected boolean isFull() {
        return this.stats.getLife() == this.stats.getFullLife();
    }

    /*
     * {@inheritDoc}
     */
    @Override
    protected void attack() {
        this.stats.getCharacter().setDamagedLife(this.stats.getAttack());
    }

    /*
     * {@inheritDoc}
     */
    @Override
    protected void move(final double deltaTime) {
        final Vector2 chara = this.stats.getCharacter().getPosition();
        final double charX = this.stats.getCharacter().getPosition().x();

        if(chara.distance(super.getPosition()) > 70){
            super.setPosition(super.getPosition().add((new Vector2(deltaTime * this.stats.getSpeed().x() * this.direction, super.getPosition().y()))));
        }else{
            if(charX - super.getPosition().x() > 0){
                if(this.direction > 0){
                    super.setPosition(super.getPosition().add((new Vector2(deltaTime * this.stats.getSpeed().x(), super.getPosition().y()))));
                }
            }else{
                if(this.direction > 0){
                    super.setPosition(super.getPosition().add((new Vector2(- deltaTime * this.stats.getSpeed().x(), super.getPosition().y()))));
                }
            }
        }
    }

}
