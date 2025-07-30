package it.unibo.falltohell.model.impl.gameobject;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import it.unibo.falltohell.model.api.gameobject.GameObject;
import it.unibo.falltohell.model.api.gameobject.movable.entity.enemy.Enemy;
import it.unibo.falltohell.model.impl.gameobject.movable.entity.character.Caster;
import it.unibo.falltohell.model.impl.physics.BoxCollider;
import it.unibo.falltohell.util.Dimensions;
import it.unibo.falltohell.util.Priority;
import it.unibo.falltohell.util.Vector2;

/**
 * Class that represents a powerful blast that will be emanated from a caster.
 * @author Martina Malagoli
 */
public class Blast extends GameObjectImpl {

    private static final Dimensions DIMENSIONS = new Dimensions(TILE_SIZE * 3, TILE_SIZE * 3);
    private static final double AMOUNT_DAMAGE = 50;

    private final Caster caster;

    /**
     * Initialization of the Blast class.
     * @param caster that creates the blast
     */
    @SuppressFBWarnings(
        value = "EI_EXPOSE_REP2",
        justification = "The blast has to know the current position of the caster"
    )
    public Blast(final Caster caster) {
        super(caster.getLevel(), caster.getPosition(), new BoxCollider(DIMENSIONS));
        this.caster = caster;
        this.initDrawable(Priority.VERY_LOW, "blast.png");
    }

    /**
     * {@inheritDoc}
     * It will make the blast follow the caster during all the blast existence.
     */
    @Override
    public void update() {
        this.setPosition(this.caster.getPosition());
    }

    /**
     *{@inheritDoc}
     */
    @Override
    public void onCollision(final GameObject other, final Vector2 direction) {
        if (other instanceof Enemy enemy) {
            enemy.setDamagedLife(AMOUNT_DAMAGE);
        }
    }
}
