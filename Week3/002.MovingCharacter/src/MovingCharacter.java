import java.awt.*;
import java.awt.geom.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javafx.animation.AnimationTimer;
import javafx.application.Application;

import static javafx.application.Application.launch;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import javax.imageio.ImageIO;

import org.jfree.fx.FXGraphics2D;
import org.jfree.fx.ResizableCanvas;

public class MovingCharacter extends Application {
    private ResizableCanvas canvas;
    private BufferedImage[] tiles;

    double positionX = 0;

    boolean left = true;
    boolean running = true;

    @Override
    public void start(Stage stage) throws Exception {


        BorderPane mainPane = new BorderPane();
        canvas = new ResizableCanvas(g -> draw(g), mainPane);
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
                draw(g2d);
            }
        }.start();

        stage.setScene(new Scene(mainPane));
        stage.setTitle("Moving Character");
        stage.show();
        draw(g2d);
    }


    public void draw(FXGraphics2D graphics) {
        graphics.setTransform(new AffineTransform());
        graphics.setBackground(Color.white);
        graphics.clearRect(0, 0, (int) canvas.getWidth(), (int) canvas.getHeight());

        AffineTransform tx = new AffineTransform();
        tx.translate(positionX, canvas.getHeight() / 2);
        tx.translate(-tiles[0].getWidth()/2.0,0);
//        tx.translate(positionX, 0);

        if (left) {
            tx = new AffineTransform(-1, 0, 0, 1, 0, 0);
            tx.translate(-positionX, canvas.getHeight() / 2);
            tx.translate(-tiles[0].getWidth()/2.0,0);
        }



        //dit kiezen kan op basis van de positie, of op basis van een attribuut
        int frame = ((int) (positionX / 8)) % tiles.length;


        graphics.drawImage(tiles[frame], tx, null);
    }


    public void update(double deltaTime) {

        if (running) {
            if (left) {
                positionX -= 2;
            } else {
                positionX += 1;
            }

            if (positionX > canvas.getWidth() - tiles[0].getWidth()/2.0) {
                left = true;
            }
            if (positionX < tiles[0].getWidth()/2.0) {
                left = false;
            }
        }
    }

    public void init() {
        try {
            BufferedImage image = ImageIO.read(getClass().getResource("/images/sprite.png"));
            tiles = new BufferedImage[8];
            int alpha = 0;
            for (int i = 32; i < 40; i++) {
                tiles[alpha] = image.getSubimage(64 * (i % 8), 64 * (i / 8), 64, 64);
                alpha++;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        launch(MovingCharacter.class);
    }

}
