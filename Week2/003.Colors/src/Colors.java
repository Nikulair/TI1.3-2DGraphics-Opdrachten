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

public class Colors extends Application {
    private ResizableCanvas canvas;

    @Override
    public void start(Stage primaryStage) throws Exception {
        BorderPane mainPane = new BorderPane();
        canvas = new ResizableCanvas(g -> draw(g), mainPane);
        mainPane.setCenter(canvas);
        draw(new FXGraphics2D(canvas.getGraphicsContext2D()));
        primaryStage.setScene(new Scene(mainPane));
        primaryStage.setTitle("Colors");
        primaryStage.show();
    }


    public void draw(FXGraphics2D graphics) {
        graphics.setTransform(new AffineTransform());
        graphics.setBackground(Color.white);
        graphics.clearRect(0, 0, (int) canvas.getWidth(), (int) canvas.getHeight());


        double x = canvas.getWidth() / 13.0;

        Rectangle2D squareBlack = new Rectangle2D.Double(x * 0, 0, x, x);
        Rectangle2D squareBlue = new Rectangle2D.Double(x * 1, 0, x, x);
        Rectangle2D squareCyan = new Rectangle2D.Double(x * 2, 0, x, x);
        Rectangle2D squareDarkGray = new Rectangle2D.Double(x * 3, 0, x, x);
        Rectangle2D squareGray = new Rectangle2D.Double(x * 4, 0, x, x);
        Rectangle2D squareGreen = new Rectangle2D.Double(x * 5, 0, x, x);
        Rectangle2D squareLightGray = new Rectangle2D.Double(x * 6, 0, x, x);
        Rectangle2D squareMagenta = new Rectangle2D.Double(x * 7, 0, x, x);
        Rectangle2D squareOrange = new Rectangle2D.Double(x * 8, 0, x, x);
        Rectangle2D squarePink = new Rectangle2D.Double(x * 9, 0, x, x);
        Rectangle2D squareRed = new Rectangle2D.Double(x * 10, 0, x, x);
        Rectangle2D squareWhite = new Rectangle2D.Double(x * 11, 0, x, x);
        Rectangle2D squareYellow = new Rectangle2D.Double(x * 12, 0, x, x);

        graphics.setColor(Color.black);
        graphics.fill(squareBlack);
        graphics.setColor(Color.BLUE);
        graphics.fill(squareBlue);
        graphics.setColor(Color.CYAN);
        graphics.fill(squareCyan);
        graphics.setColor(Color.darkGray);
        graphics.fill(squareDarkGray);
        graphics.setColor(Color.gray);
        graphics.fill(squareGray);
        graphics.setColor(Color.green);
        graphics.fill(squareGreen);
        graphics.setColor(Color.lightGray);
        graphics.fill(squareLightGray);
        graphics.setColor(Color.magenta);
        graphics.fill(squareMagenta);
        graphics.setColor(Color.orange);
        graphics.fill(squareOrange);
        graphics.setColor(Color.pink);
        graphics.fill(squarePink);
        graphics.setColor(Color.red);
        graphics.fill(squareRed);
        graphics.setColor(Color.white);
        graphics.fill(squareWhite);
        graphics.setColor(Color.yellow);
        graphics.fill(squareYellow);
    }


    public static void main(String[] args) {
        launch(Colors.class);
    }

}
