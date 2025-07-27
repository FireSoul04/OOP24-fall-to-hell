package it.unibo.falltohell.view.impl.renderable;

import it.unibo.falltohell.util.Priority;
import it.unibo.falltohell.util.Vector2;

import java.awt.Image;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

public class SpriteRenderable extends BaseRenderable {

    private final Image sprite;
    private final Priority priority;

    public SpriteRenderable(final boolean visibility, final Vector2 position,
                            final Image sprite, final Priority priority) {
        super(visibility, position);
        this.sprite = sprite;
        this.priority = priority;
    }

    @Override
    public void render(final Graphics graphics) {
        if (this.isVisible()) {
            final AffineTransform transform = new AffineTransform();
            final Graphics2D graphics2D = (Graphics2D) graphics;
            transform.translate(this.getPosition().x(), this.getPosition().y());
            transform.scale(this.isMirrored() ? -1.0 : 1.0, 1.0);
            transform.translate(-this.sprite.getWidth(null) / 2.0, -this.sprite.getHeight(null) / 2.0);
            graphics2D.drawImage(this.sprite, transform, null);
        }
    }

    @Override
    public Priority getPriority() {
        return this.priority;
    }
}
