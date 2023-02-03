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

public class Moon extends Application {
    private ResizableCanvas canvas;

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        BorderPane mainPane = new BorderPane();
        canvas = new ResizableCanvas(g -> draw(g), mainPane);
        mainPane.setCenter(canvas);
        primaryStage.setScene(new Scene(mainPane));
        primaryStage.setTitle("Moon");
        primaryStage.show();
        draw(new FXGraphics2D(canvas.getGraphicsContext2D()));
    }


    public void draw(FXGraphics2D graphics)
    {
        graphics.setTransform(new AffineTransform());
        graphics.setBackground(Color.white);
        graphics.clearRect(0, 0, (int) canvas.getWidth(), (int) canvas.getHeight());
        graphics.translate(canvas.getWidth()/2,canvas.getHeight()/2);
        GeneralPath path = new GeneralPath();
//        path.moveTo(100,100);
//        path.moveTo(100,100);
//        path.moveTo(100,100);
        path.moveTo(
                -canvas.getWidth()/12,
                -canvas.getHeight()/4
        );
        path.curveTo(
                canvas.getWidth()/16,
                -canvas.getHeight()/4,

                canvas.getWidth()/16,
                canvas.getHeight()/4,

                -canvas.getWidth()/7,
                canvas.getHeight()/4
        );
        path.curveTo(
                canvas.getWidth()/5,
                canvas.getHeight()/3,

                canvas.getWidth()/5,
                -canvas.getHeight()/3,

                -canvas.getWidth()/12,
                -canvas.getHeight()/4
        );
        path.closePath();

        graphics.fill(path);
    }


    public static void main(String[] args)
    {
        launch(Moon.class);
    }

}
