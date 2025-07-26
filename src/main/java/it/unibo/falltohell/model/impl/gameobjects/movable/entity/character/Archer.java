package it.unibo.falltohell.model.impl.gameobjects.movable.entity.character;

import it.unibo.falltohell.model.api.Level;
import it.unibo.falltohell.model.api.gameobjects.movable.entity.statistic.CharacterStatistics;
import it.unibo.falltohell.model.api.physics.Collider;
import it.unibo.falltohell.model.impl.gameobjects.movable.entity.BaseCharacter;
import it.unibo.falltohell.model.impl.gameobjects.movable.entity.StatisticFactoryImpl;
import it.unibo.falltohell.model.impl.gameobjects.movable.entity.weapons.BaseRangedWeapon;
import it.unibo.falltohell.util.Dimensions;
import it.unibo.falltohell.util.Vector2;
import it.unibo.falltohell.model.api.gameobjects.movable.Projectile;
import java.util.List;
import java.util.Optional;
import java.util.ArrayList;
import it.unibo.falltohell.model.impl.gameobjects.movable.entity.weapons.Bow;
import it.unibo.falltohell.model.impl.gameobjects.movable.projectile.ReturnableArrow;
import it.unibo.falltohell.model.impl.physics.colliders.BoxCollider;
import java.util.Optional;

/**
 * Represents an Archer character in the game.
 * The Archer can shoot arrows using a Bow and has the ability to return arrows.
 *
 * This class extends BaseCharacter and implements the shooting mechanism with a Bow.
 * It manages the arrows shot by the archer and allows for returning arrows to the inventory.
 *
 * @author Casadei Lorenzo
 */
public class Archer extends BaseCharacter {

    private final Bow bow;
    private final List<Projectile> shotedArrows = new ArrayList<>();
    private static final double LIFE = 0;
    private static final double ATTACK = 0;
    private static final double ATTACK_SPEED = 0;
    private static final Vector2 SPEED = new Vector2(3.0, 3.0);
    private static final double MANA = 0;
    private static final long COOLDONW = 500;
    private static final Vector2 PROJECTILE_SPEED = new Vector2(3.5,3.5);
    private static final CharacterStatistics STATISTICS = new StatisticFactoryImpl()
            .createCharacterStatistic(LIFE, ATTACK, SPEED, new Dimensions(20,20), MANA, ATTACK_SPEED);


    /**
     * Constructs a new ArcherCharacter.
     *
     * @param level    the game level
     * @param position the initial position
     * @param bow      the ranged weapon used to shoot arrows
     */
    public Archer(final Level level, final Vector2 position) {
        super(level, position, STATISTICS, "archer.png");
        this.bow = new Bow(this, 5,Optional.empty(), COOLDONW, "bow.png", PROJECTILE_SPEED);

    }

    /**
     * Shoots an arrow in the given direction if possible.
     *
     * @param direction the direction to shoot (normalized vector)
     * @param speed     the speed of the arrow
     * @param collider  the collider for the arrow
     */
    public void shootArrow(final Vector2 direction, final double speed, final Collider collider) {
        bow.attack();
        Projectile arrow = bow.getShotProjectile().get();
        
        if (arrow != null) {
            shotedArrows.add(arrow);
        }
    }

    /**
     * Gets the ranged weapon used by this archer.
     *
     * @return the bow
     */
    public BaseRangedWeapon getBow() {
        return bow;
    }

    /**
     * @return the list of arrows shot by this archer
     */
    public List<Projectile> getShotedArrows() {
        return this.shotedArrows;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CharacterID getCharacterID() {
        return CharacterID.ARCHER;
    }

    /**
     * Gets the list of arrows shot by this archer.
     *
     * @return the list of shot arrows
     */
    public List<Projectile> getShotArrows() {
        return shotedArrows;
    }

    /**
     * Returns an arrow to the archer's inventory.
     * This method is called when an arrow returns after being shot.
     *
     * @param arrow the arrow to return
     */
    public void returnArrow(final ReturnableArrow arrow) {
        this.bow.reload(1);
        this.shotedArrows.remove(arrow);
    }

}
