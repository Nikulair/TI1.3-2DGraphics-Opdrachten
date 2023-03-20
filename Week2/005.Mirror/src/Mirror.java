import java.awt.*;
import java.awt.geom.*;

import javafx.application.Application;

import static javafx.application.Application.launch;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.jfree.fx.FXGraphics2D;
import org.jfree.fx.ResizableCanvas;

public class Mirror extends Application {
    ResizableCanvas canvas;

    @Override
    public void start(Stage primaryStage) throws Exception {
        BorderPane mainPane = new BorderPane();
        canvas = new ResizableCanvas(g -> draw(g), mainPane);
        mainPane.setCenter(canvas);
        primaryStage.setScene(new Scene(mainPane));
        primaryStage.setTitle("Mirror");
        primaryStage.show();
        draw(new FXGraphics2D(canvas.getGraphicsContext2D()));
    }


    public void draw(FXGraphics2D graphics) {
        graphics.setTransform(new AffineTransform());
        graphics.setBackground(Color.white);
        graphics.clearRect(0, 0, (int) canvas.getWidth(), (int) canvas.getHeight());

        AffineTransform affineTransform = new AffineTransform();

        // make assenstelsel
        affineTransform.translate(canvas.getWidth() / 2, canvas.getHeight() / 2);
        graphics.draw(affineTransform.createTransformedShape(new Line2D.Double(0, -canvas.getHeight() / 2, 0, canvas.getHeight() / 2)));
        graphics.draw(affineTransform.createTransformedShape(new Line2D.Double(-canvas.getWidth() / 2, 0, canvas.getWidth() / 2, 0)));

        // create y = 2.5x line
        double scale = canvas.getWidth() / 2;
        graphics.draw(affineTransform.createTransformedShape(new Line2D.Double(
                1 * scale,
                -2.5 * scale,
                -1 * scale,
                2.5 * scale
        )));

        // create shape
        Rectangle2D box = new Rectangle2D.Double(-50, -50, 100, 100);

        // set to (0, 150)
        affineTransform.translate(0, -150);

        // draw box
        graphics.draw(affineTransform.createTransformedShape(box));

        // set back to (0, 0)
        affineTransform.translate(0, 150);

        // reflect with line y = 2.5x
        affineTransform.concatenate(new AffineTransform(
                2 / (1 + Math.pow(2.5, 2)) - 1,
                2 * -2.5 / (1 + Math.pow(2.5, 2)),
                2 * -2.5 / (1 + Math.pow(2.5, 2)),
                (2 * Math.pow(2.5, 2)) / (1 + Math.pow(2.5, 2)) - 1,
                0,
                0
        ));

        // set to (0, 150)
        affineTransform.translate(0, -150);

        // draw box
        graphics.draw(affineTransform.createTransformedShape(box));


    }


    public static void main(String[] args) {
        launch(Mirror.class);
    }

}
