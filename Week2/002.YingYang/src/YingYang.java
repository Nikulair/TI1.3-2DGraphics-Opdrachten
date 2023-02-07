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

public class YingYang extends Application {
    private ResizableCanvas canvas;

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        BorderPane mainPane = new BorderPane();
        canvas = new ResizableCanvas(g -> draw(g), mainPane);
        mainPane.setCenter(canvas);
        primaryStage.setScene(new Scene(mainPane));
        primaryStage.setTitle("Ying Yang");
        primaryStage.show();
        draw(new FXGraphics2D(canvas.getGraphicsContext2D()));
    }

    public void draw(FXGraphics2D graphics)
    {

        graphics.setTransform(new AffineTransform());
        graphics.setBackground(Color.lightGray);
        graphics.clearRect(0, 0, (int) canvas.getWidth(), (int) canvas.getHeight());

        Area yingYangSide = new Area();


        double scale;
        if (canvas.getHeight()>canvas.getWidth()){
            graphics.translate(0,(canvas.getHeight()-canvas.getWidth())/2);
            scale = canvas.getWidth()/2;
        } else {
            graphics.translate((canvas.getWidth()-canvas.getHeight())/2,0);
            scale = canvas.getHeight()/2;
        }
        graphics.scale(2,2);

        // circle
        yingYangSide.add(new Area(new Ellipse2D.Double(0,0,scale, scale)));

        // remove right side
        yingYangSide.subtract(new Area(new Rectangle2D.Double(.5*scale,0,.5*scale,scale)));

        // add upper halfcircle
        yingYangSide.add(new Area(new Ellipse2D.Double(.25*scale,0,.5*scale,.5*scale)));

        // remove lower halfcircle
        yingYangSide.subtract(new Area(new Ellipse2D.Double(.25*scale,.5*scale,.5*scale,.5*scale)));

        // add smol circle
        yingYangSide.add(new Area(new Ellipse2D.Double(.4375*scale,.6875*scale,.125*scale,.125*scale)));

        // remove smol circle
        yingYangSide.subtract(new Area(new Ellipse2D.Double(.4375*scale,.1875*scale,.125*scale,.125*scale)));



        graphics.setColor(Color.white);
        graphics.fill(yingYangSide);
        graphics.setColor(Color.black);
        graphics.draw(yingYangSide);
        graphics.rotate(Math.toRadians(180), scale/2,scale/2);
        graphics.draw(yingYangSide);
        graphics.fill(yingYangSide);

    }

    public static void main(String[] args)
    {
        launch(YingYang.class);
    }

}
