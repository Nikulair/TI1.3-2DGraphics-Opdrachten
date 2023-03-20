import java.awt.*;
import java.awt.geom.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javafx.animation.AnimationTimer;
import javafx.application.Application;

import static javafx.application.Application.launch;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import javax.imageio.ImageIO;

import org.jfree.fx.FXGraphics2D;
import org.jfree.fx.ResizableCanvas;

public class FadingImage extends Application {
    private ResizableCanvas canvas;
    private BufferedImage[] images;

    private int currentImage = 0;
    private int timer = 0;

    private float blendingProcess = 0f;

    @Override
    public void start(Stage stage) throws Exception {

        BorderPane mainPane = new BorderPane();
        canvas = new ResizableCanvas(g -> draw(g, stage), mainPane);
        mainPane.setCenter(canvas);
        FXGraphics2D g2d = new FXGraphics2D(canvas.getGraphicsContext2D());
        new AnimationTimer() {
            long last = -1;

            @Override
            public void handle(long now) {
                if (last == -1)
                    last = now;
                update((now - last) / 1000000000.0);
                last = now;
                draw(g2d, stage);
            }
        }.start();

        stage.setScene(new Scene(mainPane));
        stage.setTitle("Fading image");
        stage.show();
        draw(g2d, stage);
    }


    public void draw(FXGraphics2D graphics, Stage stage) {
        graphics.setTransform(new AffineTransform());
        graphics.setBackground(Color.white);
        graphics.clearRect(0, 0, (int) canvas.getWidth(), (int) canvas.getHeight());

        AffineTransform tx = new AffineTransform();

        graphics.drawImage(images[currentImage], tx, null);

        graphics.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, blendingProcess));

        if (currentImage == 0) {
            graphics.drawImage(images[images.length - 1], tx, null);
        } else {
            graphics.drawImage(images[currentImage - 1], tx, null);
        }



        stage.setWidth(images[currentImage].getWidth());
        stage.setHeight(images[currentImage].getHeight());
    }

    public void init() {
        try {
            images = new BufferedImage[10];
            for (int i = 0; i < images.length; i++) {
                images[i] = ImageIO.read(getClass().getResource("/images/image" + i + ".jpg"));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void update(double deltaTime) {
            blendingProcess -= 0.006f;
            if (blendingProcess <= 0f) {
                blendingProcess = 0;
                if (timer > 400) {
                    blendingProcess = .8f;
                    currentImage++;
                    if (currentImage == images.length) {
                        currentImage = 0;
                    }
                    timer = 0;
                }
                timer++;
            }
    }

    public static void main(String[] args) {
        launch(FadingImage.class);
    }

}
