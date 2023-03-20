import java.awt.*;
import java.awt.geom.*;

import javafx.application.Application;

import static javafx.application.Application.launch;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.jfree.fx.FXGraphics2D;

public class Spirograph extends Application {
    private TextField v1;
    private TextField v2;
    private TextField v3;
    private TextField v4;
    private Button reset;
    private Button draww;
    private boolean translated = false;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Canvas canvas = new Canvas(960, 540);

        VBox mainBox = new VBox();
        HBox topBar = new HBox();
        mainBox.getChildren().add(topBar);
        mainBox.getChildren().add(new Group(canvas));

        topBar.getChildren().add(v1 = new TextField("300"));
        topBar.getChildren().add(v2 = new TextField("1"));
        topBar.getChildren().add(v3 = new TextField("300"));
        topBar.getChildren().add(v4 = new TextField("10"));
        topBar.getChildren().add(reset = new Button("reset"));
        topBar.getChildren().add(draww = new Button("draw"));

        FXGraphics2D fxGraphics2D = new FXGraphics2D(canvas.getGraphicsContext2D());

//        v1.textProperty().addListener(e -> draw(new FXGraphics2D(canvas.getGraphicsContext2D())));
//        v2.textProperty().addListener(e -> draw(new FXGraphics2D(canvas.getGraphicsContext2D())));
//        v3.textProperty().addListener(e -> draw(new FXGraphics2D(canvas.getGraphicsContext2D())));
//        v4.textProperty().addListener(e -> draw(new FXGraphics2D(canvas.getGraphicsContext2D())));

//        v1.textProperty().addListener(e -> draw(fxGraphics2D));
//        v2.textProperty().addListener(e -> draw(fxGraphics2D));
//        v3.textProperty().addListener(e -> draw(fxGraphics2D));
//        v4.textProperty().addListener(e -> draw(fxGraphics2D));

        fxGraphics2D.setColor(Color.black);
        fxGraphics2D.fillRect(-20000, -20000, 40000, 40000);

        reset.setOnAction(e -> {
            fxGraphics2D.setColor(Color.black);
            fxGraphics2D.fillRect(-20000, -20000, 40000, 40000);
        });
        draww.setOnAction(e -> {
            draw(fxGraphics2D);
        });



        primaryStage.setScene(new Scene(mainBox));
        primaryStage.setTitle("Spirograph");
        primaryStage.show();
    }


    public void draw(FXGraphics2D graphics) {
        //you can use Double.parseDouble(v1.getText()) to get a double value from the first textfield
        //feel free to add more textfields or other controls if needed, but beware that swing components might clash in naming
        if (!translated) {
            graphics.translate(960 / 2, 540 / 2);
            graphics.scale(.4, .4);
            translated = true;
        }

        double x1, y1;

        double a = Double.parseDouble(v1.getText());
        double b = Double.parseDouble(v2.getText());
        double c = Double.parseDouble(v3.getText());
        double d = Double.parseDouble(v4.getText());

        double x2 = (a * Math.cos(b * 0)) + (c * Math.cos(d * 0));
        double y2 = (a * Math.sin(b * 0)) + (c * Math.sin(d * 0));

        graphics.setStroke(new BasicStroke(20));
        graphics.setColor(Color.getHSBColor((float) Math.random(), 1f, 1f));
        for (double i = 0; i < Math.PI * 2; i += Math.PI / 200) {
            double x21 = (a * Math.cos(b * i)) + (c * Math.cos(d * i));
            double y21 = (a * Math.sin(b * i)) + (c * Math.sin(d * i));
            x1 = x21;
            y1 = y21;
            graphics.draw(new Line2D.Double(x1, y1, x2, y2));
            x2 = x21;
            y2 = y21;
        }

    }


    public static void main(String[] args) {
        launch(Spirograph.class);
    }

}
