package it.unibo.falltohell.model.impl.gameobjects.movable.entity.enemy;

import it.unibo.falltohell.model.api.gameobjects.Block;
import it.unibo.falltohell.model.api.gameobjects.movable.entity.Character;
import it.unibo.falltohell.model.api.GameObject;
import it.unibo.falltohell.model.api.Level;
import it.unibo.falltohell.model.impl.CustomTimerImpl;
import it.unibo.falltohell.model.impl.gameobjects.movable.entity.BaseEnemy;
import it.unibo.falltohell.model.util.Vector2;

/**
 * Implements abstract class Enemies, creates first type for enemies
 * @author Sara Visani
 */
public class Monster1 extends BaseEnemy{
    private static final double HEIGHT = 20;
    private static final double WIDTH = 20;
    private static final double FULL_LIFE = 20;
    private static final double DAMAGE = 20;
    private static final double X_VEL = 2;
    private static final double Y_VEL = 20;
    private static final int NO_AGGRO = 10;
    private int direction = 1;

    public Monster1(final Level level, final Vector2 initialCord, final Character character) {
        super(level, initialCord, WIDTH, HEIGHT, X_VEL, Y_VEL, character, FULL_LIFE, DAMAGE);

        super.getTm().addTimer(super.getNo_aggro(), new CustomTimerImpl(NO_AGGRO, () -> {if(this.isFull()){
                                                                                            if(super.getLife()+super.getLife()*0.1>FULL_LIFE){
                                                                                                super.setLife(FULL_LIFE);
                                                                                            }else{
                                                                                                super.addLife(super.getLife()*0.1);
                                                                                            }
                                                                                        }
                                                                                        super.getTm().restartTimer(super.getNo_aggro());}));
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
        return super.getLife() == FULL_LIFE;
    }

    /*
     * {@inheritDoc}
     */
    @Override
    protected void attack() {
        this.getCharacter().setDamagedLife(DAMAGE);
    }

    /*
     * {@inheritDoc}
     */
    @Override
    protected void move(final double deltaTime) {
        final Vector2 chara = this.getCharacter().getPosition();
        final double charX = this.getCharacter().getPosition().x();

        if(chara.distance(super.getPosition()) > 70){
            super.setPosition(super.getPosition().add((new Vector2(deltaTime * X_VEL * this.direction, super.getPosition().y()))));
        }else{
            if(charX - super.getPosition().x() > 0){
                if(this.direction > 0){
                    super.setPosition(super.getPosition().add((new Vector2(deltaTime * X_VEL, super.getPosition().y()))));
                }
            }else{
                if(this.direction > 0){
                    super.setPosition(super.getPosition().add((new Vector2(- deltaTime * X_VEL, super.getPosition().y()))));
                }
            }
        }
    }

}
