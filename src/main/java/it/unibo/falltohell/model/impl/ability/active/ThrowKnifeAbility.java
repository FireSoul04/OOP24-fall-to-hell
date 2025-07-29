package it.unibo.falltohell.model.impl.ability.active;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import it.unibo.falltohell.model.api.ability.active.SpecialActiveAbility;
import it.unibo.falltohell.model.api.manager.TimerManager;
import it.unibo.falltohell.model.api.timer.CustomTimer;
import it.unibo.falltohell.model.impl.gameobject.movable.entity.character.Rogue;
import it.unibo.falltohell.model.impl.gameobject.movable.projectile.Knife;
import it.unibo.falltohell.model.impl.timer.CustomTimerImpl;
import it.unibo.falltohell.util.Vector2;

import java.util.List;

/**
 * Class representing an active ability that throw many knives from the rogue position.
 * @author Davide Mancini
 */
public class ThrowKnifeAbility implements SpecialActiveAbility {

    private static final double ABILITY_COST = 3;
    private static final long COOLDOWN_TIME = 3000;
    private static final List<Vector2> KNIFES_VELOCITIES = List.of(
        new Vector2(4.0, 0.0),
        new Vector2(3.0, 1.0),
        new Vector2(3.0, -1.0)
    );

    private final Rogue rogue;
    private final CustomTimer cooldownTimer;
    private final TimerManager tm;
    private boolean canActivate;

    /**
     * Create the ability attached to the rogue.
     * @param rogue that use this ability
     */
    @SuppressFBWarnings(
        value = "EI_EXPOSE_REP2",
        justification = "The ability have to remove mana to the owner when used"
    )
    public ThrowKnifeAbility(final Rogue rogue) {
        this.rogue = rogue;
        this.canActivate = true;
        this.cooldownTimer = new CustomTimerImpl(COOLDOWN_TIME, () -> this.canActivate = true);
        this.tm = this.rogue.getLevel().getTimerManager();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void activate() {
        if (this.canActivate && this.rogue.subManaIfEnough(ABILITY_COST)) {
            final String timerName = "knife-ability-cooldown";
            this.tm.restartIfPresent(timerName, this.cooldownTimer);
            final double direction = this.rogue.isFacingRight() ? 1.0 : -1.0;
            for (final Vector2 v : KNIFES_VELOCITIES) {
                new Knife(rogue.getLevel(), rogue.getPosition(), new Vector2(v.x() * direction, v.y()));
            }
            this.canActivate = false;
        }
    }
}
