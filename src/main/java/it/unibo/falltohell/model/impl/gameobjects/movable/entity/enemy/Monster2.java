package it.unibo.falltohell.model.impl.gameobjects.movable.entity.enemy;

import java.util.Optional;

import it.unibo.falltohell.model.api.GameObject;
import it.unibo.falltohell.model.api.Level;
import it.unibo.falltohell.model.api.gameobjects.Block;
import it.unibo.falltohell.model.api.gameobjects.movable.entity.Character;
import it.unibo.falltohell.model.impl.CustomTimerImpl;
import it.unibo.falltohell.model.impl.LevelImpl;
import it.unibo.falltohell.model.impl.gameobjects.movable.ProjectileImpl;
import it.unibo.falltohell.model.impl.gameobjects.movable.entity.BaseEnemy;
import it.unibo.falltohell.model.impl.physics.colliders.BoxCollider;
import it.unibo.falltohell.model.util.Dimensions;
import it.unibo.falltohell.model.util.Vector2;

/**
 * Implements abstract class Enemies, creates second type for enemies
 * @author Sara Visani
 */

public class Monster2 extends BaseEnemy{
    private static final double HEIGHT = 10;
    private static final double WIDTH = 10;
    private static final double HEIGHT_A = 10;
    private static final double WIDTH_A = 10;
    private static final double FULL_LIFE = 10;
    private static final double DAMAGE = 10; //Physical damage
    private static final double DAMAGE_A = 10; //Damage of projectile
    private static final double X_VEL = 1;
    private static final double Y_VEL = 10;
    private static final double X_VEL_A = 1;
    private static final double Y_VEL_A = 10;
    private static final double DISTANCE = 10;
    private static final int NO_AGGRO = 10;
    private int direction = 1;
    private Optional<Vector2> collided = Optional.empty();
    private String attack = "attack";
    

    public Monster2(final Level level, final Vector2 initialCord,final Character character) {
        super(level, initialCord, WIDTH, HEIGHT, X_VEL, Y_VEL, character, FULL_LIFE, DAMAGE);

        super.getTm().addTimer(super.getNo_aggro(), new CustomTimerImpl(NO_AGGRO, () -> {if(this.isFull() && super.getTimeNoAggro() > NO_AGGRO){
                                                                        if(super.getLife()+super.getLife()*0.1>FULL_LIFE){
                                                                            super.setLife(FULL_LIFE);
                                                                        }else{
                                                                            super.addLife(super.getLife()*0.1);
                                                                        }
                                                                        super.getTm().restartTimer(super.getNo_aggro());
                                                                    };}));
        super.getTm().addTimer(attack, new CustomTimerImpl(4000, () -> {this.attack(); super.getTm().restartTimer(attack);}));
    }

    @Override
    public void update(final double deltaTime) {
        this.move(deltaTime);
    }

    @Override
    public void onCollision(final GameObject other, final Vector2 direction) {
        if(other instanceof Block){
            if(direction.y() != 0){
                this.collided = Optional.of(super.getPosition());
            }
        }else if(other instanceof Character){
            this.getCharacter().setDamagedLife(DAMAGE);
            //super.getTm().removeTimer(getNo_aggro());
        }
        //TODO delete when the tests works without this
        this.collided = Optional.of(super.getPosition());
    }

    @Override
    public void setDamagedLife(final double damage){
        super.setDamagedLife(damage);
        //super.getTm().restart(getNo_aggro());
    }

    @Override
    protected boolean isFull() {
        return super.getLife() == FULL_LIFE;
    }

    @Override
    protected void attack() {
        if(this.getCharacter().getPosition().distance(super.getPosition())<20){
            new ProjectileImpl(new LevelImpl(), super.getPosition().subtract(new Vector2(0,HEIGHT + 1)), WIDTH_A, HEIGHT_A, X_VEL_A, Y_VEL_A, new BoxCollider(Vector2.zero(),new Dimensions(WIDTH_A, HEIGHT_A)));
        }
    }

    protected void move(final double deltaTime) {

        double other_X = deltaTime*X_VEL;
        final double y = super.getPosition().y();
        final Vector2 chara = this.getCharacter().getPosition();

        while(other_X > 0){
            if(chara.distance(super.getPosition())>20){
                if(super.getInitialPos().distance(new Vector2(super.getPosition().x() + (other_X * this.direction), y)) <= DISTANCE){
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
                    other_X -= Math.abs((super.getInitialPos().x() + DISTANCE * this.direction) - super.getPosition().x());
                    super.setPosition(new Vector2(super.getInitialPos().x() + DISTANCE * this.direction, y));
                    this.direction*= -1;
                }
            }else{
                if((chara.x() <= DISTANCE+super.getInitialPos().x()) && (chara.x() >= super.getInitialPos().x() - DISTANCE)){
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
                        if(super.getInitialPos().distance(new Vector2(super.getPosition().x() + other_X, y)) <= DISTANCE && !(this.collided.isPresent() && super.getPosition().add(new Vector2(other_X, 0)).x() > this.collided.get().x())){
                            super.setPosition(super.getPosition().add(new Vector2(other_X, 0)));
                            other_X = 0;
                        }else if (!(this.collided.isPresent() && this.collided.get().x() < super.getInitialPos().x() + DISTANCE)){
                            super.setPosition(new Vector2(super.getInitialPos().x() + DISTANCE, y));
                            other_X = 0;
                            this.direction *= -1;
                        }else{
                            super.setPosition(this.collided.get());
                            other_X = 0;
                        }
                    }else{
                        if(super.getInitialPos().distance(new Vector2(super.getPosition().x() - other_X, y)) <= DISTANCE && !(this.collided.isPresent() && super.getPosition().add(new Vector2(- other_X, 0)).x() > this.collided.get().x())){
                            super.setPosition(super.getPosition().add(new Vector2(- other_X, 0)));
                            other_X = 0;
                        }else if (!(this.collided.isPresent() && this.collided.get().x() > super.getInitialPos().x() - DISTANCE)){
                            super.setPosition(new Vector2(super.getInitialPos().x() - DISTANCE, y));
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
