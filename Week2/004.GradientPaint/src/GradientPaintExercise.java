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

public class GradientPaintExercise extends Application {
    private ResizableCanvas canvas;

    @Override
    public void start(Stage primaryStage) throws Exception {
        BorderPane mainPane = new BorderPane();
        canvas = new ResizableCanvas(g -> draw(g, canvas.getWidth() / 2, canvas.getHeight() / 2), mainPane);
        mainPane.setCenter(canvas);
        primaryStage.setScene(new Scene(mainPane));
        primaryStage.setTitle("GradientPaint");
        primaryStage.show();

        canvas.setOnMouseMoved(event -> {
            draw(new FXGraphics2D(canvas.getGraphicsContext2D()), event.getX(), event.getY());
        });
    }


    public void draw(FXGraphics2D graphics, double x, double y) {
        graphics.setTransform(new AffineTransform());
        graphics.setBackground(Color.white);
        graphics.clearRect(0, 0, (int) canvas.getWidth(), (int) canvas.getHeight());

        float[] fractions = {0f, 1/3f, 2/3f};
        Color[] colors = {Color.MAGENTA, Color.black, Color.green};

        Paint paint = new RadialGradientPaint(
                (float) x,
                (float) y,
                250f,
                (float) canvas.getWidth() / 2,
                (float) canvas.getHeight() / 2,
                fractions,
                colors,
                MultipleGradientPaint.CycleMethod.REFLECT
        );
        graphics.setPaint(paint);
        graphics.fill(new Rectangle2D.Double(0, 0, canvas.getWidth(), canvas.getHeight()));
    }

    public static void main(String[] args) {
        launch(GradientPaintExercise.class);
    }

}
