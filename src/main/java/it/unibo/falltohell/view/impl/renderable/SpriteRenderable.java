package it.unibo.falltohell.view.impl.renderable;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import it.unibo.falltohell.util.Priority;
import it.unibo.falltohell.util.Vector2;

import java.awt.Image;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

/**
 * Class that represents a sprite to be rendered.
 * @author Martina Malagoli
 */
public class SpriteRenderable extends BaseRenderable {

    private final Image sprite;
    private final Priority priority;

    /**
     * Initialization of the SpriteRenderable class.
     * @param visibility tells if the sprite to be rendered should be visible
     * @param position where the sprite should be rendered
     * @param sprite associated to the sprite renderable object
     * @param priority is the priority of the sprite associated with the sprite renderable object
     */
    @SuppressFBWarnings(
        value = "EI_EXPOSE_REP2",
        justification = "The sprite renderable object must know what image to render"
    )
    public SpriteRenderable(final boolean visibility, final Vector2 position,
                            final Image sprite, final Priority priority) {
        super(visibility, position);
        this.sprite = sprite;
        this.priority = priority;
    }

    /**
     * {@inheritDoc}
     * Renders the renderable sprite object according to its characteristic.
     */
    @Override
    public void render(final Graphics graphics) {
        if (this.isVisible()) {
            final AffineTransform transform = new AffineTransform();
            if (graphics instanceof Graphics2D graphics2D) {
                transform.translate(this.getPosition().x(), this.getPosition().y());
                transform.scale(this.isMirrored() ? -1.0 : 1.0, 1.0);
                transform.translate(-this.sprite.getWidth(null) / 2.0, -this.sprite.getHeight(null) / 2.0);
                graphics2D.drawImage(this.sprite, transform, null);
            } else {
                throw new IllegalArgumentException("The application needs Graphics2D to show images properly");
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Priority getPriority() {
        return this.priority;
    }
}
