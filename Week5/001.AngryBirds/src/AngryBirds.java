import java.awt.*;
import java.awt.geom.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javafx.animation.AnimationTimer;
import javafx.application.Application;

import static javafx.application.Application.launch;

import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.dyn4j.dynamics.Body;
import org.dyn4j.dynamics.World;
import org.dyn4j.geometry.Geometry;
import org.dyn4j.geometry.MassType;
import org.dyn4j.geometry.Vector2;
import org.jfree.fx.FXGraphics2D;
import org.jfree.fx.ResizableCanvas;

import javax.imageio.ImageIO;

public class AngryBirds extends Application {

    private ResizableCanvas canvas;
    private World world;
    private MousePicker mousePicker;
    private Camera camera;
    private boolean debugSelected = false;
    private ArrayList<GameObject> gameObjects = new ArrayList<>();
    private BufferedImage background;


    /**
     * Heb geprobeerd om de slingsot te maken, maar het is me niet gelukt.
     * +
     * Ik ben er een beetje laat mee begonnen.
     */

    @Override
    public void start(Stage stage) throws Exception {

        BorderPane mainPane = new BorderPane();

        // Add debug button
        javafx.scene.control.CheckBox showDebug = new CheckBox("Show debug");
        showDebug.setOnAction(e -> {
            debugSelected = showDebug.isSelected();
        });
        mainPane.setTop(showDebug);

        canvas = new ResizableCanvas(g -> draw(g), mainPane);
        mainPane.setCenter(canvas);
        FXGraphics2D g2d = new FXGraphics2D(canvas.getGraphicsContext2D());

        camera = new Camera(canvas, g -> draw(g), g2d);
        mousePicker = new MousePicker(canvas);

        new AnimationTimer() {
            long last = -1;

            @Override
            public void handle(long now) {
                if (last == -1) {
                    last = now;
                }
                update((now - last) / 1000000000.0);
                last = now;
                draw(g2d);
            }
        }.start();


        stage.setScene(new Scene(mainPane, 1900, 1000));
        stage.setTitle("Angry Birds");
        stage.show();
        draw(g2d);
    }

    public void init() throws IOException {
        world = new World();
        world.setGravity(new Vector2(0, -9.8));

        background = ImageIO.read(getClass().getResource("/background.png"));

        Body floor = new Body();
        floor.addFixture(Geometry.createRectangle(40, 1));
        floor.getTransform().setTranslation(0, -2);
        floor.setMass(MassType.INFINITE);
        world.addBody(floor);

        Body rightWall = new Body();
        rightWall.addFixture(Geometry.createRectangle(.15, 20));
        rightWall.getTransform().setTranslation(20, 5);
        rightWall.setMass(MassType.INFINITE);
        world.addBody(rightWall);

        Body leftWall = new Body();
        leftWall.addFixture(Geometry.createRectangle(.15, 20));
        leftWall.getTransform().setTranslation(-20, 5);
        leftWall.setMass(MassType.INFINITE);
        world.addBody(leftWall);

        Body roof = new Body();
        roof.addFixture(Geometry.createRectangle(40, .15));
        roof.getTransform().setTranslation(0, 15);
        roof.setMass(MassType.INFINITE);
        world.addBody(roof);

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 3; j++) {
                createBlocks(i, j);
            }
        }

        Body catapult = new Body();
        catapult.addFixture(Geometry.createRectangle(.001, .001));
        catapult.getTransform().setTranslation(-6.5, -.75);
        catapult.setMass(MassType.INFINITE);
        world.addBody(catapult);
        gameObjects.add(new GameObject("/catapult.png", catapult, new Vector2(0, 200), .2));

        Body birb = new Body();
        birb.addFixture(Geometry.createCircle(0.2));
        birb.getTransform().setTranslation(-6.5, -.75);
        birb.setMass(MassType.NORMAL);
        birb.getFixture(0).setRestitution(0.75);
        world.addBody(birb);
        gameObjects.add(new GameObject("/birb.png", birb, new Vector2(0, 0), .15));

        Body bomb = new Body();
        bomb.addFixture(Geometry.createCircle(0.2));
        bomb.getTransform().setTranslation(-7.5, -.75);
        bomb.setMass(MassType.NORMAL);
        bomb.getFixture(0).setRestitution(0.75);
        world.addBody(bomb);
        gameObjects.add(new GameObject("/bomb.png", bomb, new Vector2(0, 0), .15));
    }

    private void createBlocks(double i, double j) {
        Body block = new Body();
        block.addFixture(Geometry.createRectangle(.5, .5));
        block.getTransform().setTranslation(3 + i * .25, -1 - j * .25);
        block.setMass(MassType.NORMAL);
        block.getFixture(0).setRestitution(.5);
        world.addBody(block);
        gameObjects.add(new GameObject("/block.png", block, new Vector2(0, 0), .14));
    }

    public void draw(FXGraphics2D graphics) {
        graphics.setTransform(new AffineTransform());
        graphics.setPaint(new TexturePaint(background, new Rectangle2D.Double(0, -canvas.getHeight()/3, canvas.getWidth(), canvas.getHeight())));
        graphics.clearRect(0, 0, (int) canvas.getWidth(), (int) canvas.getHeight());

        AffineTransform originalTransform = graphics.getTransform();

        graphics.setTransform(camera.getTransform((int) canvas.getWidth(), (int) canvas.getHeight()));
        graphics.scale(1, -1);

        for (GameObject go : gameObjects) {
            go.draw(graphics);
        }

        if (debugSelected) {
            graphics.setColor(Color.getHSBColor(.85f, 1f, .3f));
            DebugDraw.draw(graphics, world, 100);
        }

        graphics.setTransform(originalTransform);
    }

    public void update(double deltaTime) {
        mousePicker.update(world, camera.getTransform((int) canvas.getWidth(), (int) canvas.getHeight()), 100);
        world.update(deltaTime);
    }

    public static void main(String[] args) {
        launch(AngryBirds.class);
    }

}