package it.unibo.falltohell.model.impl.manager;

import java.util.ArrayList;
import java.util.List;
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
 */
public class StaticCollisionManager {

    private final List<GameObject> obstacles;

    /**
     * Constructs a new {@code StaticCollisionManager} with an empty obstacle list.
     */
    public StaticCollisionManager() {
        this.obstacles = new ArrayList<>();
    }

    /**
     * Adds an obstacle to the managed list.
     *
     * @param obstacle the {@link GameObject} to add as an obstacle
     */
    public void addObstacle(final GameObject obstacle) {
        this.obstacles.add(obstacle);
    }

    /**
     * Removes an obstacle from the managed list.
     *
     * @param obstacle the {@link GameObject} obstacle to remove
     */
    public void removeObstacle(final GameObject obstacle) {
        this.obstacles.remove(obstacle);
    }

    /**
     * Checks if the specified rectangular area at a given position
     * collides with any managed obstacle.
     * <p>
     * The rectangular area is defined by its center position and its width and height.
     * </p>
     *
     * @param position the center {@link Vector2} position of the area to check
     * @param width    the width of the rectangular area
     * @param height   the height of the rectangular area
     * @return {@code true} if the area collides with any obstacle, {@code false} otherwise
     */
    public boolean isBlocked(final Vector2 position, final double width, final double height) {
        final double left = position.x() - width / 2;
        final double right = position.x() + width / 2;
        final double top = position.y() - height / 2;
        final double bottom = position.y() + height / 2;

        return obstacles.stream()
                .anyMatch(o -> {
                    if (o.getCollider().isEmpty()) {
                        return false;
                    }
                    final var oPos = o.getPosition();
                    final var oWidth = o.getCollider().get().size().width();
                    final var oHeight = o.getCollider().get().size().height();

                    final var oLeft = oPos.x() - oWidth / 2;
                    final var oRight = oPos.x() + oWidth / 2;
                    final var oTop = oPos.y() - oHeight / 2;
                    final var oBottom = oPos.y() + oHeight / 2;

                    return rectsIntersect(left, right, top, bottom, oLeft, oRight, oTop, oBottom);
                });
    }

    /**
     * Helper method that checks if two rectangles intersect.
     * <p>
     * Each rectangle is defined by its left, right, top, and bottom edges.
     * </p>
     *
     * @param leftA   left edge of rectangle A
     * @param rightA  right edge of rectangle A
     * @param topA    top edge of rectangle A
     * @param bottomA bottom edge of rectangle A
     * @param leftB   left edge of rectangle B
     * @param rightB  right edge of rectangle B
     * @param topB    top edge of rectangle B
     * @param bottomB bottom edge of rectangle B
     * @return {@code true} if the rectangles intersect, {@code false} otherwise
     */
    private boolean rectsIntersect(final double leftA, final double rightA, final double topA, final double bottomA,
            final double leftB, final double rightB, final double topB, final double bottomB) {
        return leftA <= rightB && rightA >= leftB
                && topA <= bottomB && bottomA >= topB;
    }
}
