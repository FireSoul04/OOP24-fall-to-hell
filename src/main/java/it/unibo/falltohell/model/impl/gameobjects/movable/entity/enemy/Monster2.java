package it.unibo.falltohell.model.impl.gameobjects.movable.entity.enemy;

import it.unibo.falltohell.model.api.GameObject;
import it.unibo.falltohell.model.api.gameobjects.Block;
import it.unibo.falltohell.model.api.gameobjects.Merchant;
import it.unibo.falltohell.model.api.gameobjects.movable.entity.Character;
import it.unibo.falltohell.model.impl.gameobjects.movable.entity.BaseEnemy;
import it.unibo.falltohell.model.impl.physics.colliders.BoxCollider;
import it.unibo.falltohell.model.util.Dimensions;
import it.unibo.falltohell.model.util.Vector2;

/**
 * Implements abstract class Enemies, creates second type for enemies
 * @author Sara Visani
 */

public class Monster2 extends BaseEnemy{
    private static final double HEIGHT=10;
    private static final double WIDTH=10;
    private static final double FULL_LIFE=10;
    private static final double DAMAGE=10;
    private static final double X_VEL=1;
    private static final double Y_VEL=10;
    private static final double DISTANCE=10;
    private static final double NO_AGGRO=10;
    private int direction = 1;

    public Monster2(final Vector2 initialCord,final Character character) {
        super(initialCord, character);
        super.setLife(FULL_LIFE);
        super.setHeight(HEIGHT);
        super.setWidth(WIDTH);
        super.setDamage(DAMAGE);
        super.setSpeedX(X_VEL);
        super.setSpeedY(Y_VEL);
        super.setCollider(new BoxCollider(Vector2.zero(),new Dimensions(WIDTH, HEIGHT)));
    }

    @Override
    public void update(final double deltaTime) {
        
        super.addTimeNoAggro(deltaTime);
        if(this.isFull() && super.getTimeNoAggro() > NO_AGGRO){
            super.addLife(this.getLife()*0.1);
        }
        this.move(deltaTime);
    }

    @Override
    public void onCollision(final GameObject other) {
        this.direction*=-1;
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

        /*while(other_X > 0){
            if(super.getInitialPos().distance(super.getPosition().add(new Vector2(other_X*this.direction, y)))<=(DISTANCE)){
                super.setPosition(super.getPosition().add(new Vector2(other_X*this.direction, y)));
                other_X=0;
            }
            else{
                other_X=other_X-super.getPosition().distance(new Vector2((super.getInitialPos().x()+DISTANCE)*this.direction,y));
                super.setPosition(new Vector2((super.getInitialPos().x()+DISTANCE)*this.direction, y));
                this.direction*= -1;
            }
        }*/

        while(other_X > 0){
            if(chara.distance(super.getPosition())>20){
                if(super.getInitialPos().distance(new Vector2(super.getPosition().x()+(other_X*this.direction),y))<=DISTANCE){
                    super.setPosition(super.getPosition().add(new Vector2(other_X*this.direction, 0)));
                    other_X=0;
                }
                else{
                    other_X -= Math.abs((super.getInitialPos().x()+DISTANCE*this.direction)-super.getPosition().x());
                    super.setPosition(new Vector2(super.getInitialPos().x()+DISTANCE*this.direction, y));
                    this.direction*= -1;
                }
            }else{
                if((chara.x()<=DISTANCE+super.getInitialPos().x())&&(chara.x()>=super.getInitialPos().x()-DISTANCE)){    
                    if(chara.distance(super.getPosition())>super.getPosition().distance(new Vector2(super.getPosition().x()+other_X*this.direction,y))){
                        if(chara.x()-super.getPosition().x()>0){
                            if(super.getInitialPos().distance(new Vector2(super.getPosition().x()+(other_X),y))<=DISTANCE){
                                super.setPosition(super.getPosition().add(new Vector2(other_X, 0)));
                            }else{
                                super.setPosition(new Vector2(super.getInitialPos().x()+DISTANCE, y));
                            }
                            other_X=0;
                        }else{
                            if(super.getInitialPos().distance(new Vector2(super.getPosition().x()+(-other_X),y))<=DISTANCE){
                                super.setPosition(super.getPosition().add(new Vector2(-other_X, 0)));
                            }else{
                                super.setPosition(new Vector2(super.getInitialPos().x()-DISTANCE, y));
                            }
                            other_X=0;
                        }
                    }else{
                        super.setPosition(chara);
                        other_X = 0;
                    }
                }else{
                    if(chara.x()-super.getPosition().x()>0){
                        if(super.getInitialPos().distance(new Vector2(super.getPosition().x()+(other_X),y))<=DISTANCE){
                        super.setPosition(super.getPosition().add(new Vector2(other_X, 0)));
                        other_X=0;
                        }else{
                        super.setPosition(new Vector2(super.getInitialPos().x()+DISTANCE, y));
                        other_X = 0;
                        this.direction*= -1;
                        }
                    }else{
                        if(super.getInitialPos().distance(new Vector2(super.getPosition().x()+(-other_X),y))<=DISTANCE){
                        super.setPosition(super.getPosition().add(new Vector2(-other_X, 0)));
                        other_X=0;
                        }else{
                        super.setPosition(new Vector2(super.getInitialPos().x()-DISTANCE, y));
                        other_X = 0;
                        this.direction*= -1;
                        }
                    }
                }
            }
        }
    }
}
