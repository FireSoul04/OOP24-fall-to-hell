package it.unibo.falltohell.model.impl.gameobjects.movable.entity.enemy;

import java.util.Optional;

import it.unibo.falltohell.model.api.GameObject;
import it.unibo.falltohell.model.api.Level;
import it.unibo.falltohell.model.api.gameobjects.Block;
import it.unibo.falltohell.model.api.gameobjects.movable.entity.Character;
import it.unibo.falltohell.model.api.gameobjects.movable.entity.statistic.RestrictedLongRangeEnemyStatistics;
import it.unibo.falltohell.model.impl.CustomTimerImpl;
import it.unibo.falltohell.model.impl.LevelImpl;
import it.unibo.falltohell.model.impl.gameobjects.movable.ProjectileImpl;
import it.unibo.falltohell.model.impl.gameobjects.movable.entity.BaseEnemy;
import it.unibo.falltohell.model.impl.gameobjects.movable.entity.StatisticFactoryImpl;
import it.unibo.falltohell.model.impl.physics.colliders.BoxCollider;
import it.unibo.falltohell.model.util.Dimensions;
import it.unibo.falltohell.model.util.Vector2;

/**
 * Concrete implementation of a long-range enemy type.
 * Extends {@link BaseEnemy} to provide specific behaviors for Monster2 enemies.
 * <p>
 * This class manages movement, attacks, collision behavior, and health regeneration timers.
 * </p>
 * @author Sara Visani
 */
public class Monster2 extends BaseEnemy{
    private static final Dimensions DIMENSIONS = new Dimensions(10, 10);
    private static final Dimensions DIMENSIONS_ARROW = new Dimensions(10, 10);
    private static final double FULL_LIFE = 10;
    private static final double DAMAGE = 10; //Physical damage
    private static final double DAMAGE_A = 10; //Damage of projectile
    private static final Vector2 VELOCITY = new Vector2(1, 10);
    private static final Vector2 VELOCITY_ARROW = new Vector2(1, 10);
    private static final double DISTANCE = 10;
    private static final int NO_AGGRO = 10;

    final private RestrictedLongRangeEnemyStatistics stats;
    private int direction = 1;
    private Optional<Vector2> collided = Optional.empty();
    private String attack = "attack";
    
    /**
     * Constructs a new Monster2 enemy instance.
     * <p>
     * @param level the {@link Level} this enemy belongs to
     * @param initialCord the initial position of the enemy in the level
     * @param character the {@link Character} this enemy targets or is associated with
     */
    public Monster2(final Level level, final Vector2 initialCord,final Character character) {
        super(level, new StatisticFactoryImpl().createLongRangeRestrictedStatistic(FULL_LIFE, DAMAGE, VELOCITY, DIMENSIONS, initialCord, NO_AGGRO, character, DAMAGE_A, VELOCITY_ARROW, DIMENSIONS_ARROW, DISTANCE));

        stats = (RestrictedLongRangeEnemyStatistics)super.getStats();

        this.stats.getTm().addTimer(this.stats.getNo_aggroName(), new CustomTimerImpl(this.stats.getNoAggro(), () -> {if(this.isFull()){
                                                                        if(this.stats.getLife()+this.stats.getLife()*0.1>FULL_LIFE){
                                                                            this.stats.setLife(FULL_LIFE);
                                                                        }else{
                                                                            this.stats.addLife(this.stats.getLife()*0.1);
                                                                        }
                                                                        this.stats.getTm().restartTimer(this.stats.getNo_aggroName());
                                                                    };}));
        this.stats.getTm().addTimer(attack, new CustomTimerImpl(4000, () -> {this.attack(); this.stats.getTm().restartTimer(attack);}));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update(final double deltaTime) {
        this.move(deltaTime);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onCollision(final GameObject other, final Vector2 direction) {
        if(other instanceof Block){
            if(direction.y() != 0){
                this.collided = Optional.of(super.getPosition());
            }
        }else if(other instanceof Character){
            this.stats.getCharacter().setDamagedLife(DAMAGE);
            //super.getTm().removeTimer(getNo_aggro());
        }
        //TODO delete when the tests works without this
        this.collided = Optional.of(super.getPosition());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setDamagedLife(final double damage){
        super.setDamagedLife(damage);
        //super.getTm().restart(getNo_aggro());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected boolean isFull() {
        return this.stats.getLife() == FULL_LIFE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void attack() {
        if(this.stats.getCharacter().getPosition().distance(super.getPosition())<20){
            new ProjectileImpl(new LevelImpl(), super.getPosition().subtract(new Vector2(0,this.stats.getDimensions().width() + 1)), this.stats.getProjectileDimensions().width(), this.stats.getProjectileDimensions().height(), this.stats.getProjectileSpeed().x(), this.stats.getProjectileSpeed().y(), new BoxCollider(Vector2.zero(),this.stats.getProjectileDimensions()));
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void move(final double deltaTime) {

        double other_X = deltaTime*this.stats.getSpeed().x();
        final double y = super.getPosition().y();
        final Vector2 chara = this.stats.getCharacter().getPosition();

        while(other_X > 0){
            if(chara.distance(super.getPosition())>20){
                if(this.stats.getInitialPos().distance(new Vector2(super.getPosition().x() + (other_X * this.direction), y)) <= this.stats.getDistance()){
                    if(this.collided.isPresent() && super.getPosition().add(new Vector2(other_X * this.direction, 0)).x() > this.collided.get().x()){
                        if(super.getPosition() != this.collided.get()){
                            other_X -= super.getPosition().distance(this.collided.get());
                            super.setPosition(this.collided.get());
                        }
                        this.direction *= -1;
                    }
                    super.setPosition(super.getPosition().add(new Vector2(other_X * this.direction, 0)));
                    other_X = 0;
                }
                else{
                    if(this.collided.isPresent() && super.getPosition().add(new Vector2(other_X * this.direction, 0)).x() > this.collided.get().x()){
                        if(super.getPosition() != this.collided.get()){
                            other_X -= super.getPosition().distance(this.collided.get());
                            super.setPosition(this.collided.get());
                        }
                        this.direction *= -1;
                    }
                    other_X -= Math.abs((this.stats.getInitialPos().x() + this.stats.getDistance() * this.direction) - super.getPosition().x());
                    super.setPosition(new Vector2(this.stats.getInitialPos().x() + this.stats.getDistance() * this.direction, y));
                    this.direction*= -1;
                }
            }else{
                if((chara.x() <= this.stats.getDistance() + this.stats.getInitialPos().x()) && (chara.x() >= this.stats.getInitialPos().x() - this.stats.getDistance())){
                    if(chara.distance(super.getPosition()) > super.getPosition().distance(new Vector2(super.getPosition().x() + other_X * this.direction, y))){
                        if(chara.x() - super.getPosition().x() > 0 && !(this.collided.isPresent() && this.collided.get().x() < super.getPosition().add(new Vector2(other_X, 0)).x())){
                            super.setPosition(super.getPosition().add(new Vector2(other_X, 0)));
                            other_X = 0;
                        }else if (!(this.collided.isPresent() && this.collided.get().x() > super.getPosition().add(new Vector2(- other_X, 0)).x())){
                            super.setPosition(super.getPosition().add(new Vector2(- other_X, 0)));
                            other_X = 0;
                        }else{
                            super.setPosition(this.collided.get());
                            other_X = 0;
                        }
                    }else if (!(this.collided.isPresent() && this.collided.get().x() < chara.x())){
                        super.setPosition(chara);
                        other_X = 0;
                    }else{
                        super.setPosition(this.collided.get());
                        other_X = 0;
                    }
                }else{
                    if(chara.x() - super.getPosition().x() > 0){
                        if(this.stats.getInitialPos().distance(new Vector2(super.getPosition().x() + other_X, y)) <= this.stats.getDistance() && !(this.collided.isPresent() && super.getPosition().add(new Vector2(other_X, 0)).x() > this.collided.get().x())){
                            super.setPosition(super.getPosition().add(new Vector2(other_X, 0)));
                            other_X = 0;
                        }else if (!(this.collided.isPresent() && this.collided.get().x() < this.stats.getInitialPos().x() + this.stats.getDistance())){
                            super.setPosition(new Vector2(this.stats.getInitialPos().x() + this.stats.getDistance(), y));
                            other_X = 0;
                            this.direction *= -1;
                        }else{
                            super.setPosition(this.collided.get());
                            other_X = 0;
                        }
                    }else{
                        if(this.stats.getInitialPos().distance(new Vector2(super.getPosition().x() - other_X, y)) <= this.stats.getDistance() && !(this.collided.isPresent() && super.getPosition().add(new Vector2(- other_X, 0)).x() > this.collided.get().x())){
                            super.setPosition(super.getPosition().add(new Vector2(- other_X, 0)));
                            other_X = 0;
                        }else if (!(this.collided.isPresent() && this.collided.get().x() > this.stats.getInitialPos().x() - this.stats.getDistance())){
                            super.setPosition(new Vector2(this.stats.getInitialPos().x() - this.stats.getDistance(), y));
                            other_X = 0;
                            this.direction *= -1;
                        }else{
                            super.setPosition(this.collided.get());
                            other_X = 0;
                        }
                    }
                }
            }
        }
    }
}
