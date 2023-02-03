import java.awt.*;
import java.awt.geom.*;

import javafx.application.Application;

import static javafx.application.Application.launch;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.stage.Stage;
import org.jfree.fx.FXGraphics2D;

public class Rainbow extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Canvas canvas = new Canvas(960, 540);
        draw(new FXGraphics2D(canvas.getGraphicsContext2D()));
        primaryStage.setScene(new Scene(new Group(canvas)));
        primaryStage.setTitle("Rainbow");
        primaryStage.show();
    }


    public void draw(FXGraphics2D graphics) {
        graphics.translate(960 / 2, (540 / 2) + (540 / 3));
        graphics.rotate(Math.toRadians(180));

        double distance = 180;
        for (double i = 235; i > 0; i -= 1) {
            double x = Math.cos(0);
            double y = Math.sin(0);
            for (double j = 0; j < Math.PI; j += Math.PI / 20000) {

                graphics.setColor(
                        Color.getHSBColor(
                                (float) (i - 5) / 255,
                                1f,
                                1f
                        )
                );

                graphics.draw(
                        new Line2D.Double(
                                x * distance,
                                y * distance,
                                Math.cos(j) * distance,
                                Math.sin(j) * distance
                        )
                );

                x = Math.cos(j);
                y = Math.sin(j);
            }
            distance++;
        }
    }


    public static void main(String[] args) {
        launch(Rainbow.class);
    }

}
