import java.awt.*;
import java.awt.geom.*;

import javafx.application.Application;

import static javafx.application.Application.launch;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.stage.Stage;
import org.jfree.fx.FXGraphics2D;

public class Graph extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Canvas canvas = new Canvas(960, 540);
        draw(new FXGraphics2D(canvas.getGraphicsContext2D()));
        primaryStage.setScene(new Scene(new Group(canvas)));
        primaryStage.setTitle("Graph");
        primaryStage.show();
    }


    public void draw(FXGraphics2D graphics) {
        graphics.translate(960 / 2, 540 / 2);
        graphics.scale(1, -1);

        graphics.setColor(Color.red);
        graphics.drawLine(0, 0, 1000, 0);
        graphics.setColor(Color.green);
        graphics.drawLine(0, 0, 0, 1000);
        graphics.setColor(Color.black);

        double resolution = 0.1;
        double scale = 100.0;
        double lastY = Math.sin(-10);

        for (double x = -10; x < 10; x += resolution) {
            double y = Math.pow(x, 3);
            graphics.draw(new Line2D.Double(x * scale, y * scale, (x - resolution) * scale, lastY * scale));
            lastY = y;
        }
    }


    public static void main(String[] args) {
        launch(Graph.class);
    }

}
