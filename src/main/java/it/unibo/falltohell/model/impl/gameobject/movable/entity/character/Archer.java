package it.unibo.falltohell.model.impl.gameobject.movable.entity.character;

import it.unibo.falltohell.model.api.level.Level;
import it.unibo.falltohell.model.impl.factory.AbilityFactoryImpl;
import it.unibo.falltohell.model.impl.factory.StatisticFactoryImpl;
import it.unibo.falltohell.model.impl.gameobject.weapons.BaseRangedWeapon;
import it.unibo.falltohell.util.Dimensions;
import it.unibo.falltohell.util.Vector2;
import it.unibo.falltohell.model.api.ability.active.SpecialActiveAbility;
import it.unibo.falltohell.model.api.ability.passive.StatisticPassiveAbility;
import it.unibo.falltohell.model.api.factory.AbilityFactory;
import it.unibo.falltohell.model.api.gameobject.movable.Projectile;
import java.util.Set;
import java.util.HashSet;

import it.unibo.falltohell.model.impl.gameobject.weapons.Bow;
import it.unibo.falltohell.model.impl.gameobject.movable.projectile.ReturnableArrow;

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

    private static final double LIFE = 50;
    private static final double ATTACK = 10;
    private static final double ATTACK_SPEED = 1000000;
    private static final Vector2 SPEED = new Vector2(2.5, 2.0);
    private static final double MANA = 0.5;
    private static final long COOLDOWN = 500;
    private static final Vector2 PROJECTILE_SPEED = new Vector2(5.0,0.0);
    private final Bow bow;
    private final Set<Projectile> shotedArrows = new HashSet<>();
    private final StatisticPassiveAbility bonusDamage;
    private final SpecialActiveAbility returnAbility;



    /**
     * Constructs a new ArcherCharacter.
     *
     * @param level    the game level
     * @param position the initial position
     * @param bow      the ranged weapon used to shoot arrows
     */
    public Archer(final Level level, final Vector2 position) {
        super(level, position, new StatisticFactoryImpl()
            .createCharacterStatistic(LIFE, ATTACK, SPEED, new Dimensions(20,25), MANA, ATTACK_SPEED)
            , "archer.png");
        this.bow = new Bow(this, 5, COOLDOWN, "bow.png", PROJECTILE_SPEED);
        final AbilityFactory factory = new AbilityFactoryImpl();
        bonusDamage = factory.createPassiveAbility
            (this, ch -> {
            final int arrowsInFlight = ((Archer) ch).getShotedArrows().size();
            final double baseDamage = ch.getStats().getInitialAttack();
            final double bonusPerArrow = 0.2;
            ch.getStats().setAttack(baseDamage + baseDamage * arrowsInFlight * bonusPerArrow);
        });
        this.equipWeapon(bow);
        this.returnAbility = factory.createSpecialActiveAbility(this);


    }

    /**
     * Shoots the arrow with the bow.
     */
    public void attack() {
        super.attack();
        bow.getShotProjectile().ifPresent(shotedArrows :: add);
        bonusDamage.carryOut();
    }

    /**
     * Gets the bow used by this archer.
     *
     * @return the bow
     */
    public BaseRangedWeapon getBow() {
        return bow;
    }

    /**
     * @return the list of arrows shot by this archer
     */
    public Set<Projectile> getShotedArrows() {
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
     * Returns an arrow to the archer's inventory.
     * This method is called when an arrow returns after being shot.
     *
     * @param arrow the arrow to return
     */
    public void returnArrow(final ReturnableArrow arrow) {
        this.bow.reload(1);
        this.shotedArrows.remove(arrow);
        this.bonusDamage.carryOut();
    }

    @Override
    public void update(double deltatime){
        super.update(deltatime);
        if(this.getLevel().checkCondition("ActiveAbility")){
            this.returnAbility.activate();
        }
    }
}
