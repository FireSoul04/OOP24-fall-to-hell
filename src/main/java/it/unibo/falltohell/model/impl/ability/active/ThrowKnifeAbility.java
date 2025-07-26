package it.unibo.falltohell.model.impl.ability.active;

import it.unibo.falltohell.model.api.ability.active.SpecialActiveAbility;
import it.unibo.falltohell.model.impl.gameobject.movable.entity.character.Rogue;
import it.unibo.falltohell.model.impl.gameobject.movable.projectile.Knife;
import it.unibo.falltohell.util.Vector2;

import java.util.List;

public class ThrowKnifeAbility implements SpecialActiveAbility {

    private static final List<Vector2> KNIFES_VELOCITIES = List.of(
        new Vector2(3.0, 0.0),
        new Vector2(2.0, 1.0),
        new Vector2(2.0, -1.0)
    );

    private final Rogue rogue;

    public ThrowKnifeAbility(final Rogue rogue) {
        this.rogue = rogue;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void activate() {
        for (final Vector2 v : KNIFES_VELOCITIES) {
            new Knife(rogue.getLevel(), rogue.getPosition(), v);
        }
    }
}
