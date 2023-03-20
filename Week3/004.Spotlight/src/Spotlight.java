import java.awt.*;
import java.awt.geom.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javafx.animation.AnimationTimer;
import javafx.application.Application;

import static javafx.application.Application.launch;


import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import org.jfree.fx.FXGraphics2D;
import org.jfree.fx.ResizableCanvas;

import javax.imageio.ImageIO;

public class Spotlight extends Application {
    private ResizableCanvas canvas;

    private final ArrayList<Line2D> line2DArrayList = new ArrayList<>();
    private final ArrayList<Color> line2DColorArrayList = new ArrayList<>();
    private final int canvasHeight = 700;
    private final int canvasWidth = 1200;

    private Point2D clipPosition = new Point2D.Double(200, 200);

    @Override
    public void start(Stage stage) throws Exception {
        BorderPane mainPane = new BorderPane();
        canvas = new ResizableCanvas(g -> draw(g), mainPane);
        mainPane.setCenter(canvas);
        FXGraphics2D g2d = new FXGraphics2D(canvas.getGraphicsContext2D());
        new AnimationTimer() {
            long last = -1;

            @Override
            public void handle(long now) {
                if (last == -1)
                    last = now;
                update((now - last) / 1000000000.0);
                last = now;
                draw(g2d);
            }
        }.start();

        canvas.setOnMouseMoved(e -> {
            clipPosition = new Point2D.Double(e.getX(), e.getY());
            draw(g2d);
        });

        stage.setScene(new Scene(mainPane));
        stage.setTitle("Spotlight");
        stage.setHeight(canvasHeight);
        stage.setWidth(canvasWidth);
        stage.setResizable(false);
        stage.show();
        draw(g2d);
    }


    public void draw(FXGraphics2D graphics) {
        graphics.setTransform(new AffineTransform());
        graphics.setBackground(Color.white);
        graphics.clearRect(0, 0, (int) canvas.getWidth(), (int) canvas.getHeight());

        Shape shape = new Rectangle2D.Double(0, 0, 1000, 1000);
//        Shape cursonShape = new Rectangle2D.Double(clipPosition.getX()-250, clipPosition.getY()-250, 500, 500);
        Shape cursonShape = new Ellipse2D.Double(clipPosition.getX()-75, clipPosition.getY()-75, 150, 150);
//        Shape cursonShape = new Rectangle2D.Double(100, 100, 10, 10);
        graphics.setColor(Color.white);
        graphics.fill(shape);
        graphics.clip(cursonShape);

        for (int i = 0; i < line2DArrayList.size(); i++) {
            graphics.setColor(line2DColorArrayList.get(i));
            graphics.draw(line2DArrayList.get(i));
        }

        graphics.setClip(null);
    }

    public void init() {
        for (int i = 0; i < 6000; i++) {
            line2DColorArrayList.add(Color.getHSBColor((float) Math.random(), 1f, .94f));
            if (i % 4 == 0) {
                line2DArrayList.add(new Line2D.Double(0, Math.random() * canvasHeight, canvasWidth, Math.random() * canvasHeight));
                continue;
            } else if (i % 3 == 0) {
                line2DArrayList.add(new Line2D.Double(Math.random() * canvasWidth, 0, canvasWidth, Math.random() * canvasHeight));
                continue;
            } else if (i % 2 == 0) {
                line2DArrayList.add(new Line2D.Double(0, Math.random() * canvasHeight, Math.random() * canvasWidth, canvasHeight));
                continue;
            }
            line2DArrayList.add(new Line2D.Double(Math.random() * canvasWidth, 0, Math.random() * canvasWidth, canvasHeight));
        }
    }

    public void update(double deltaTime) {

    }

    public static void main(String[] args) {
        launch(Spotlight.class);
    }

}
