import java.awt.*;
import java.awt.font.GlyphVector;
import java.awt.geom.*;

import javafx.application.Application;

import static javafx.application.Application.launch;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.jfree.fx.FXGraphics2D;
import org.jfree.fx.ResizableCanvas;

public class Rainbow extends Application {
    private ResizableCanvas canvas;
    private Boolean tr = false;

    @Override
    public void start(Stage stage) throws Exception {
        BorderPane mainPane = new BorderPane();
        canvas = new ResizableCanvas(g -> draw(g), mainPane);
        mainPane.setCenter(canvas);
        stage.setScene(new Scene(mainPane));
        stage.setTitle("Rainbow");
        stage.show();
        draw(new FXGraphics2D(canvas.getGraphicsContext2D()));
    }


    public void draw(FXGraphics2D graphics) {

        graphics.setTransform(new AffineTransform());
        graphics.setBackground(Color.white);
        graphics.clearRect(0, 0, (int) canvas.getWidth(), (int) canvas.getHeight());

        Font font = new Font("Arial", Font.PLAIN, 100);
        String str = "Regenboog";
        float colour = 0;

        for (int i = 0; i < str.length(); i++) {
            AffineTransform affineTransform = new AffineTransform();
            affineTransform.translate(canvas.getWidth() / 2, canvas.getHeight() / 2);
            Shape shape = font.createGlyphVector(graphics.getFontRenderContext(), str.substring(i, i + 1)).getOutline();

            double graden = -90 + (180 * (i / (str.length()-1.0)));

            affineTransform.rotate(Math.toRadians(graden));
            affineTransform.translate(-shape.getBounds().getWidth()/2, -100);

            graphics.setColor(Color.black);
            graphics.draw(affineTransform.createTransformedShape(shape));
            graphics.setColor(Color.getHSBColor(colour, 1f, 1f));
            graphics.fill(affineTransform.createTransformedShape(shape));
            colour += (1f / str.length());
        }
    }
    public static void main(String[] args) {
        launch(Rainbow.class);
    }

}
