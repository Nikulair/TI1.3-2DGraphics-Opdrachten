import java.awt.*;
import java.awt.geom.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javafx.animation.AnimationTimer;
import javafx.application.Application;

import static javafx.application.Application.launch;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import javax.imageio.ImageIO;

import org.jfree.fx.FXGraphics2D;
import org.jfree.fx.ResizableCanvas;

public class Screensaver extends Application {
    private ResizableCanvas canvas;

    // changeable values
    private final int pointsTotal = 20;                 // --- changes the amount of lines ---
    private final int movingPoints = 10;// <=20         // --- changes the amount of moving points to a max of 20 ---
    private final float colourSpeed = 0.0003f;          // --- changes the speed of the colour ---
    private final double speed = 2.6;                   // --- changes the speed of the lines ---


//      // random values
//    private final int pointsTotal = (int) Math.ceil(Math.random() * 1000);           // --- changes the amount of lines ---
//    private final int movingPoints = (int) Math.ceil(Math.random() * 20);// <=20    // --- changes the amount of moving points to a max of 20 ---
//    private final float colourSpeed = (float) Math.random() / 5;                        // --- changes the speed of the colour ---
//    private final double speed = Math.random() * 6;                                 // --- changes the speed of the lines ---


    private final Point2D[][] points = new Point2D[pointsTotal][movingPoints];
    private final float[] colour = new float[pointsTotal];
    private float lastColour = 0f;
    private final double[] xAdder = {speed, speed, -speed, -speed, speed, speed, -speed, -speed, speed, speed, -speed, -speed, speed, speed, -speed, -speed, speed, speed, -speed, -speed};
    private final double[] yAdder = {speed, -speed, speed, -speed, speed, -speed, speed, -speed, speed, -speed, speed, -speed, speed, -speed, speed, -speed, speed, -speed, speed, -speed};

    @Override
    public void start(Stage stage) throws Exception {

        BorderPane mainPane = new BorderPane();
        canvas = new ResizableCanvas(g -> draw(g), mainPane);
        mainPane.setCenter(canvas);
        FXGraphics2D g2d = new FXGraphics2D(canvas.getGraphicsContext2D());

        init(canvas);

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

        stage.setScene(new Scene(mainPane));
        stage.setTitle("Screensaver");
//        stage.setFullScreen(true);
        stage.show();
        draw(g2d);
    }


    public void draw(FXGraphics2D graphics) {
        graphics.setTransform(new AffineTransform());
        graphics.setBackground(Color.white);
        graphics.clearRect(0, 0, (int) canvas.getWidth(), (int) canvas.getHeight());

        for (int i = points.length - 1; i >= 0; i--) {
            for (int j = 0; j < points[i].length; j++) {
                graphics.setColor(Color.getHSBColor(colour[i], 1f, 1f));
//                graphics.setStroke(new BasicStroke((float) (speed * 1.2), BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
                if (j == points[i].length - 1) {
                    graphics.draw(new Line2D.Double(points[i][j], points[i][0]));
                    break;
                }
                graphics.draw(new Line2D.Double(points[i][j], points[i][j + 1]));
            }
        }

    }

    public void init(ResizableCanvas canvas) {
        for (int i = 0; i < points.length; i++) {
            for (int j = 0; j < points[i].length; j++) {
                points[i][j] = new Point2D.Double(0, 0);
            }
        }
        for (int i = 0; i < points[0].length; i++) {
            points[0][i] = new Point2D.Double(Math.random() * canvas.getWidth(), Math.random() * canvas.getHeight());
        }
        for (int i = 0; i < colour.length; i++) {
//            colour[i] = (float) Math.random();
            colour[i] = lastColour + colourSpeed;
            lastColour = lastColour + colourSpeed;
        }
    }

    public void update(double deltaTime) {
        for (int i = points.length - 1; i > 0; i--) {
            for (int j = 0; j < points[i].length; j++) {
                points[i][j] = points[i - 1][j];
            }
            colour[i] = colour[i - 1];
        }

        for (int i = 0; i < points[0].length; i++) {
            double x = points[0][i].getX();
            double y = points[0][i].getY();
            if (x < 0) {
                xAdder[i] = speed;
            } else if (x > canvas.getWidth()) {
                xAdder[i] = -speed;
            }
            if (y < 0) {
                yAdder[i] = speed;
            } else if (y > canvas.getHeight()) {
                yAdder[i] = -speed;
            }
            points[0][i] = new Point2D.Double(x + xAdder[i], y + yAdder[i]);
        }
        if (lastColour > 1f) {
            lastColour = 0f;
        }
//        colour[0] = (float) Math.random();
        colour[0] = lastColour;
        lastColour = lastColour + colourSpeed;
    }

    public static void main(String[] args) {
        launch(Screensaver.class);
    }

}
