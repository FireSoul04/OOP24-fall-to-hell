package it.unibo.falltohell.model.impl.gameobjects.movable.character.entity.enemy;

import it.unibo.falltohell.model.api.Collider;
import it.unibo.falltohell.model.api.GameObject;
import it.unibo.falltohell.model.api.gameobjects.movable.entity.Enemy;
import it.unibo.falltohell.model.util.Vector2;

public class Monster2 extends Enemy{
    private static final double HEIGHT=10;
    private static final double WIDTH=10;
    private static final float FULL_LIFE=10;
    private static final float DAMAGE=10;
    private static final double X_VEL=1;
    private static final double Y_VEL=10;
    private static final double DISTANCE=10;

    public Monster2(Vector2 initialCord) {
        super(initialCord);
        super.setLife(FULL_LIFE);
        super.height=HEIGHT;
        super.width=WIDTH;
        super.setDamage(DAMAGE);
        super.setSpeedX(X_VEL);
        super.setSpeedY(Y_VEL);
    }

    @Override
    public Collider getCollider() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getCollider'");
    }

    @Override
    public void update(double deltaTime) {
        this.move(deltaTime);
    }

    @Override
    public void onCollision(GameObject other) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'onCollide'");
    }

    @Override
    protected boolean isFull() {
        return this.life == FULL_LIFE;
    }

    @Override
    protected void attack() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'attack'");
    }

    @Override
    protected void move(double deltaTime) {

        double direction = 1;

        double other_X = deltaTime*X_VEL;
        final double y = super.getPosition().y();

        while(other_X > 0){
            if(initialPos.distance(super.getPosition().add(new Vector2(other_X*direction, y)))<=(DISTANCE)){
                super.setPosition(super.getPosition().add(new Vector2(other_X*direction, y)));
                other_X=0;
            }
            else{
                other_X=other_X-super.getPosition().distance(new Vector2(DISTANCE*direction,y));
                super.setPosition(new Vector2((DISTANCE)*direction, y));
                direction=direction * -1;
            }
        }
    }

    
    
}
