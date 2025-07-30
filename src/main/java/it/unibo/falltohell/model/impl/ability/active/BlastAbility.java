package it.unibo.falltohell.model.impl.ability.active;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import it.unibo.falltohell.model.api.ability.active.SpecialActiveAbility;
import it.unibo.falltohell.model.api.gameobject.GameObject;
import it.unibo.falltohell.model.api.level.Level;
import it.unibo.falltohell.model.api.manager.TimerManager;
import it.unibo.falltohell.model.api.statistic.CharacterStatistics;
import it.unibo.falltohell.model.impl.gameobject.Blast;
import it.unibo.falltohell.model.impl.gameobject.movable.entity.character.Caster;
import it.unibo.falltohell.model.impl.timer.CustomTimerImpl;

/**
 * Class that represents the caster's active ability to generate a
 * powerful blast around himself that will hit near enemies.
 * @author Martina Malagoli
 */
public class BlastAbility implements SpecialActiveAbility {

    private static final double COST_MANA_BLAST = 20;
    private static final long DURATION = 1500;

    private final Caster caster;

    /**
     * Initialization of the BlastAbility class.
     * @param caster who uses this ability
     */
    @SuppressFBWarnings(
        value = "EI_EXPOSE_REP2",
        justification = "The ability must know the caster statistics to subtract mana"
    )
    public BlastAbility(final Caster caster) {
        this.caster = caster;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void activate() {
        final Level level = this.caster.getLevel();
        final TimerManager timerManager = level.getTimerManager();
        final String timerName = "blast_timer";
        if (this.caster.hasEnoughMana(COST_MANA_BLAST)) {
            final GameObject blast = new Blast(this.caster);
            final CharacterStatistics statistics = (CharacterStatistics) this.caster.getStats();
            statistics.subMana(COST_MANA_BLAST);
            timerManager.restartIfPresent(timerName, new CustomTimerImpl(DURATION,
                () -> {
                    level.removeGameObject(blast);
                    timerManager.removeTimer(timerName);
                })
            );
        }
    }
}
