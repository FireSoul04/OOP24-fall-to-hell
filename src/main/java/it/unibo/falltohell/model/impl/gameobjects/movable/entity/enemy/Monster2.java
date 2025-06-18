package it.unibo.falltohell.model.impl.gameobjects.movable.entity.enemy;

import java.util.Optional;

import it.unibo.falltohell.model.api.GameObject;
import it.unibo.falltohell.model.api.gameobjects.Block;
import it.unibo.falltohell.model.api.gameobjects.movable.entity.Character;
import it.unibo.falltohell.model.impl.gameobjects.movable.entity.BaseEnemy;
import it.unibo.falltohell.model.util.Vector2;

/**
 * Implements abstract class Enemies, creates second type for enemies
 * @author Sara Visani
 */

public class Monster2 extends BaseEnemy{
    private static final double HEIGHT=10;
    private static final double WIDTH=10;
    private static final double HEIGHT_A=10;
    private static final double WIDTH_A=10;
    private static final double FULL_LIFE=10;
    private static final double DAMAGE=10; //Physical damage
    private static final double DAMAGE_A=10; //Damage of projectile
    private static final double X_VEL=1;
    private static final double Y_VEL=10;
    private static final double X_VEL_A=1;
    private static final double Y_VEL_A=10;
    private static final double DISTANCE=10;
    private static final double NO_AGGRO=10;
    private int direction = 1;
    private Optional<Vector2> collided = Optional.empty();

    public Monster2(final Vector2 initialCord,final Character character) {
        super(initialCord,WIDTH,HEIGHT,X_VEL,Y_VEL,character);
        super.setLife(FULL_LIFE);
        super.setDamage(DAMAGE);
        super.setSpeedX(X_VEL);
        super.setSpeedY(Y_VEL);
    }

    @Override
    public void update(final double deltaTime) {
        
        super.addTimeNoAggro(deltaTime);
        if(this.isFull() && super.getTimeNoAggro() > NO_AGGRO){
            super.addLife(this.getLife()*0.1);
        }
        //this.attack();       TODO when ranged attack implemented
        this.move(deltaTime);
    }

    @Override
    public void onCollision(final GameObject other) {
        //TODO ask for info
        this.collided = Optional.of(super.getPosition());
    }

    @Override
    public void onCollision(final GameObject other, final Vector2 direction) {
        if(other instanceof Block){
            if(direction.y() != 0){
                this.collided = Optional.of(super.getPosition());
            }
        }else if(other instanceof Character){
            this.getCharacter().setDamagedLife(DAMAGE);
        }
    }

    @Override
    protected boolean isFull() {
        return super.getLife() == FULL_LIFE;
    }

    @Override
    protected void attack() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'attack'");
    }

    protected void move(final double deltaTime) {

        double other_X = deltaTime*X_VEL;
        final double y = super.getPosition().y();
        final Vector2 chara = this.getCharacter().getPosition();

        while(other_X > 0){
            if(chara.distance(super.getPosition())>20){
                if(super.getInitialPos().distance(new Vector2(super.getPosition().x()+(other_X*this.direction),y))<=DISTANCE){
                    if(this.collided.isPresent() && super.getPosition().add(new Vector2(other_X*this.direction,0)).x()>this.collided.get().x()){
                        if(super.getPosition()!=this.collided.get()){
                            other_X -= super.getPosition().distance(this.collided.get());
                            super.setPosition(this.collided.get());
                        }
                        this.direction *= -1;
                    }
                    super.setPosition(super.getPosition().add(new Vector2(other_X*this.direction, 0)));
                    other_X=0;
                }
                else{
                    if(this.collided.isPresent() && super.getPosition().add(new Vector2(other_X*this.direction,0)).x()>this.collided.get().x()){
                        if(super.getPosition()!=this.collided.get()){
                            other_X -= super.getPosition().distance(this.collided.get());
                            super.setPosition(this.collided.get());
                        }
                        this.direction *= -1;
                    }
                    other_X -= Math.abs((super.getInitialPos().x()+DISTANCE*this.direction)-super.getPosition().x());
                    super.setPosition(new Vector2(super.getInitialPos().x()+DISTANCE*this.direction, y));
                    this.direction*= -1;
                }
            }else{
                if((chara.x() <= DISTANCE+super.getInitialPos().x()) && (chara.x() >= super.getInitialPos().x()-DISTANCE)){
                    if(chara.distance(super.getPosition())>super.getPosition().distance(new Vector2(super.getPosition().x()+other_X*this.direction,y))){
                        if(chara.x()-super.getPosition().x()>0 && !(this.collided.isPresent() && this.collided.get().x() < super.getPosition().add(new Vector2(other_X,0)).x())){
                            super.setPosition(super.getPosition().add(new Vector2(other_X, 0)));
                            other_X=0;
                        }else if (!(this.collided.isPresent() && this.collided.get().x() > super.getPosition().add(new Vector2(-other_X,0)).x())){
                            super.setPosition(super.getPosition().add(new Vector2(-other_X, 0)));
                            other_X=0;
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
                    if(chara.x()-super.getPosition().x()>0){
                        if(super.getInitialPos().distance(new Vector2(super.getPosition().x()+(other_X),y))<=DISTANCE && !(this.collided.isPresent() && super.getPosition().add(new Vector2(other_X, 0)).x() > this.collided.get().x())){
                            super.setPosition(super.getPosition().add(new Vector2(other_X, 0)));
                            other_X = 0;
                        }else if (!(this.collided.isPresent() && this.collided.get().x() < super.getInitialPos().x()+DISTANCE)){
                            super.setPosition(new Vector2(super.getInitialPos().x()+DISTANCE, y));
                            other_X = 0;
                            this.direction *= -1;
                        }else{
                            super.setPosition(this.collided.get());
                            other_X = 0;
                        }
                    }else{
                        if(super.getInitialPos().distance(new Vector2(super.getPosition().x()+(-other_X),y))<=DISTANCE && !(this.collided.isPresent() && super.getPosition().add(new Vector2(-other_X, 0)).x() > this.collided.get().x())){
                            super.setPosition(super.getPosition().add(new Vector2(-other_X, 0)));
                            other_X=0;
                        }else if (!(this.collided.isPresent() && this.collided.get().x() > super.getInitialPos().x()-DISTANCE)){
                            super.setPosition(new Vector2(super.getInitialPos().x()-DISTANCE, y));
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
