package it.unibo.falltohell.model.impl.gameobjects.movable.entity;

import java.util.stream.Collector.Characteristics;

import it.unibo.falltohell.model.api.gameobjects.movable.entity.statistic.CharacterStatistics;
import it.unibo.falltohell.util.Dimensions;
import it.unibo.falltohell.util.Vector2;

public class TestStatsFactory {
    private TestStatsFactory() {
        
    }

    /**
     * Creates a basic statistics object with default values.
     *
     * @return a new instance of StatisticsImpl
     */
    public static CharacterStatistics createDefault() {
        return new StatisticFactoryImpl().createCharacterStatistic(100, 10, new Vector2(2, 2),
         new Dimensions(1.0,1.0), 0, 0.5);
    }
}
