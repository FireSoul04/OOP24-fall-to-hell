package it.unibo.falltohell.model.impl.ability.active;

import it.unibo.falltohell.model.api.ability.active.SpecialActiveAbility;
import it.unibo.falltohell.model.api.manager.TimerManager;
import it.unibo.falltohell.model.api.timer.CustomTimer;
import it.unibo.falltohell.model.impl.gameobject.movable.entity.character.Rogue;
import it.unibo.falltohell.model.impl.gameobject.movable.projectile.Knife;
import it.unibo.falltohell.model.impl.timer.CustomTimerImpl;
import it.unibo.falltohell.util.Vector2;

import java.util.List;

public class ThrowKnifeAbility implements SpecialActiveAbility {

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
        if (this.canActivate) {
            final String timerName = "knife-ability-cooldown";
            if (!this.tm.searchTimer(timerName)) {
                this.tm.addTimer(timerName, this.cooldownTimer);
            } else {
                this.tm.restartTimer(timerName);
            }
            final double direction = this.rogue.isFacingRight() ? 1.0 : -1.0;
            for (final Vector2 v : KNIFES_VELOCITIES) {
                new Knife(rogue.getLevel(), rogue.getPosition(), new Vector2(v.x() * direction, v.y()));
            }
            this.canActivate = false;
        }
    }
}
