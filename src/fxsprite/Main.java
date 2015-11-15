package fxsprite;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Main extends Application {
	

    public void start(Stage primaryStage) {
        primaryStage.setTitle("Sprite Test");
        
        Group root = new Group();
        Scene myScene = new Scene(root, 400, 650, Color.WHITE);
        
        Sprite s = new Sprite("megaman.png", 50, 49);
        Sprite q = new Sprite("megaman.png", 50, 49);
        q.setX(200);
        q.setY(200);;
        root.getChildren().add(q);
        root.getChildren().add(s);
        s.label(4, "powerup");
        s.playTimes("powerup", 10);
        s.pause();
        s.replay();
        q.setFPS(4);
        q.limitRowColumns(2, 9);
        q.play(2);
        primaryStage.setScene(myScene);
        primaryStage.show();
    }

    
    public static void main(String[] args) {
        launch(args);
    }
}
