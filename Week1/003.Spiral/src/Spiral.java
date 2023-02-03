import java.awt.*;
import java.awt.geom.*;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.stage.Stage;
import org.jfree.fx.FXGraphics2D;

public class Spiral extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Canvas canvas = new Canvas(960, 540);
        draw(new FXGraphics2D(canvas.getGraphicsContext2D()));
        primaryStage.setScene(new Scene(new Group(canvas)));
        primaryStage.setTitle("Spiral");
        primaryStage.setResizable(false);
        primaryStage.show();
    }
    
    
    public void draw(FXGraphics2D graphics) {
        graphics.translate(960/2, 540/2);
        graphics.scale(5,5);
        double x = 0;
        double y = 0;
        for (double i = 0; i < Math.PI*70; i+=Math.PI/40000) {
            graphics.draw(new Line2D.Double(x*i,y*i,Math.cos(i)*i,Math.sin(i)*i));
            x = Math.cos(i);
            y = Math.sin(i);
        }
    }

    public static void main(String[] args) {
        launch(Spiral.class);
    }

}
