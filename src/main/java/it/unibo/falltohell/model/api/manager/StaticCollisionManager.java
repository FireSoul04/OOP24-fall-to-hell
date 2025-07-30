package it.unibo.falltohell.model.api.manager;

import it.unibo.falltohell.model.api.gameobject.GameObject;
import it.unibo.falltohell.util.Vector2;

/**
 * Manages static obstacles in the game world and provides collision detection
 * for given positions and dimensions.
 * <p>
 * This manager keeps track of {@link GameObject} instances that represent
 * obstacles and checks if a given rectangular area collides with any of them.
 * </p>
 *
 * @see GameObject
 * @see Vector2
 * @author Sara Visani
 */
public interface StaticCollisionManager {

    /**
     * Adds an obstacle to the managed list.
     *
     * @param obstacle the {@link GameObject} to add as an obstacle
     */
    void addObstacle(GameObject obstacle);

    /**
     * Removes an obstacle from the managed list.
     *
     * @param obstacle the {@link GameObject} obstacle to remove
     */
    void removeObstacle(GameObject obstacle);

    /**
     * Checks if the specified rectangular area at a given position
     * collides with any managed obstacle.
     * <p>
     * The rectangular area is defined by its center position and its width and
     * height.
     * </p>
     *
     * @param position the center {@link Vector2} position of the area to check
     * @param width    the width of the rectangular area
     * @param height   the height of the rectangular area
     * @return {@code true} if the area collides with any obstacle, {@code false}
     *         otherwise
     */
    boolean isBlocked(Vector2 position, double width, double height);

}
