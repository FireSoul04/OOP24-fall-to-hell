package it.unibo.falltohell.model.api.gameobjects.movable.entity.character;

public interface Buff extends BuffDecorator {

    enum BuffType {
        SPEED,
        DAMAGE,
        HEALTH
    }

    /**
     * @return multiplier relative to the buff
     */
    double getMultiplier();

    /**
     * @return statistic boosted by the buff
     */
    BuffType getType();
}
