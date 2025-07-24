package it.unibo.falltohell.model.impl.gameobjects.movable.entity.character;
import it.unibo.falltohell.model.api.Level;
import it.unibo.falltohell.model.api.gameobjects.movable.entity.statistic.CharacterStatistics;
import it.unibo.falltohell.model.api.physics.Collider;
import it.unibo.falltohell.model.impl.gameobjects.movable.entity.BaseCharacter;
import it.unibo.falltohell.model.impl.gameobjects.movable.entity.weapons.BaseRangedWeapon;
import it.unibo.falltohell.util.Vector2;
import it.unibo.falltohell.model.api.gameobjects.movable.Projectile;
import java.util.List;
import java.util.ArrayList;
import it.unibo.falltohell.model.impl.gameobjects.movable.entity.weapons.Bow;
import it.unibo.falltohell.model.impl.gameobjects.movable.projectile.ReturnableArrow;
public class Archer extends BaseCharacter {

    private final Bow bow;
    private List<Projectile> shotedArrows = new ArrayList<>();

    /**
     * Constructs a new ArcherCharacter.
     *
     * @param level the game level
     * @param position the initial position
     * @param stats the character statistics<
     * @param bow the ranged weapon used to shoot arrows
     */
    public Archer(final Level level, final Vector2 position, final CharacterStatistics stats, final Bow bow) {
        super(level, position, stats);
        this.bow = bow;

    }
    /**
     * Shoots an arrow in the given direction if possible.
     *
     * @param direction the direction to shoot (normalized vector)
     * @param speed the speed of the arrow
     * @param width the width of the arrow
     * @param height the height of the arrow
     * @param collider the collider for the arrow
     */
    public void shootArrow(Vector2 direction, double speed, Collider collider) {
        Vector2 velocity = direction.multiply(speed);
        Projectile arrow = bow.attack(getLevel(), getPosition(), velocity, collider);
        if (arrow != null) {
            shotedArrows.add(arrow);
        }
        super.initDrawable();
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
    public void returnArrow(ReturnableArrow arrow) {
        this.bow.reload(1);
        this.shotedArrows.remove(arrow);
    }

}
