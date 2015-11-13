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
        // Create a place to see the shapes
        Scene myScene = new Scene(root, 400, 650, Color.WHITE);
        
        Image i = new Image(getClass().getClassLoader().getResourceAsStream("truck.png"));
        Sprite s = new Sprite("sonicsprites.png", 87, 125);
        root.getChildren().add(s);
        s.limitRowColumns(1, 8); 
        s.play(1);
        primaryStage.setScene(myScene);
        primaryStage.show();
    }

    
    public static void main(String[] args) {
        launch(args);
    }
}
