package it.unibo.falltohell.model.api.gameobjects.movable.entity.statistic.builder;

import it.unibo.falltohell.model.api.gameobjects.movable.entity.statistic.BaseEnemyStatistics;
import it.unibo.falltohell.util.Vector2;

public interface GroundEnemyStatBuilder<T extends GroundEnemyStatBuilder<T>> extends StatisticBuilder<T>{
    public T withPosition(Vector2 position) ;

    public T withNoAggro(Integer noAggro);

    public T withRegen(Double regen);

    public T withSenseDistance(Double senseDistance);

    public T withCharacter(Character character);

    public T withPoints(long points);

    public BaseEnemyStatistics build();

}
