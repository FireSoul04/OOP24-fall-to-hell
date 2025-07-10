package it.unibo.falltohell.model.api.gameobjects.movable.entity.statistic.builder;

import it.unibo.falltohell.model.api.gameobjects.movable.entity.Character;
import it.unibo.falltohell.model.api.gameobjects.movable.entity.statistic.BaseEnemyStatistics;
import it.unibo.falltohell.util.Vector2;

public interface GroundEnemyStatBuilder{
    public GroundEnemyStatBuilder withPosition(Vector2 position) ;

    public GroundEnemyStatBuilder withNoAggro(Integer noAggro);

    public GroundEnemyStatBuilder withRegen(Double regen);

    public GroundEnemyStatBuilder withSenseDistance(Double senseDistance);

    public GroundEnemyStatBuilder withCharacter(Character character);

    public GroundEnemyStatBuilder withPoints(long points);

    public BaseEnemyStatistics build();

}
