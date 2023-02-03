import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.stage.Stage;
import org.jfree.fx.FXGraphics2D;

import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;

public class House extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Canvas canvas = new Canvas(1920 / 2, 1080 / 2);
        draw(new FXGraphics2D(canvas.getGraphicsContext2D()));
        primaryStage.setScene(new Scene(new Group(canvas)));
        primaryStage.setTitle("House");
        primaryStage.show();
    }


    public void draw(FXGraphics2D graphics) {
        graphics.draw(new Line2D.Double(100,230,100,400));
        graphics.draw(new Line2D.Double(400,230,400,400));
        graphics.draw(new Line2D.Double(100,400,400,400));
        graphics.draw(new Line2D.Double(250,100,100,230));
        graphics.draw(new Line2D.Double(250,100,400,230));

        graphics.draw(new Line2D.Double(120,400,120,300));
        graphics.draw(new Line2D.Double(120,300,170,300));
        graphics.draw(new Line2D.Double(170,300,170,400));

        graphics.draw(new Line2D.Double(220,360,220,280));
        graphics.draw(new Line2D.Double(380,360,380,280));
        graphics.draw(new Line2D.Double(220,360,380,360));
        graphics.draw(new Line2D.Double(220,280,380,280));
    }


    public static void main(String[] args) {
        launch(House.class);
    }

}
