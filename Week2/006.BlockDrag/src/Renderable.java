import org.jfree.fx.FXGraphics2D;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

public class Renderable {
    private final Rectangle2D shape;
    private Point2D position;
    private final float scale;
    private final Color color;

    public Renderable(Rectangle2D shape, Point2D position, float scale, Color color) {
        this.shape = shape;
        this.position = position;
        this.scale = scale;
        this.color = color;
    }


    public Shape getTransformedShape() {
        return getTransform().createTransformedShape(shape);
    }

    public AffineTransform getTransform() {
        AffineTransform tx = new AffineTransform();
        tx.translate(position.getX(), position.getY());
        tx.scale(scale, scale);
        return tx;
    }

    public Color getColor() {
        return color;
    }

    public Point2D getPosition() {
        return this.position;
    }

    public void setPosition(Point2D position) {
        this.position = position;
    }

}