package it.unibo.falltohell.model.impl.ability.active;

import it.unibo.falltohell.model.api.ability.active.SpecialActiveAbility;
import it.unibo.falltohell.model.api.statistic.CharacterStatistics;
import it.unibo.falltohell.model.impl.gameobject.movable.entity.character.Caster;

/**
 * Class that represents the caster active ability to heal
 * himself with the use of mana.
 * @author Martina Malagoli
 */
public class HealAbility implements SpecialActiveAbility {

    private static final double AMOUNT_MANA_HEAL = 20;
    private static final double COST_MANA_HEAL = 10;
    private final Caster caster;

    /**
     * Initialization of the HealAbility class.
     * @param caster that uses this ability
     */
    public HealAbility(final Caster caster) {
        this.caster = caster;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void activate() {
        if (this.caster.subManaIfEnough(COST_MANA_HEAL)) {
            final CharacterStatistics statistics = (CharacterStatistics) this.caster.getStats();
            statistics.addLife(AMOUNT_MANA_HEAL);
        }
    }
}
