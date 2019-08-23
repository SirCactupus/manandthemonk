package main.java;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    private static Parent root;
    private static Scene scene;
    private static Stage stage;
    @Override
    public void start(Stage primaryStage) throws Exception{
        root = FXMLLoader.load(getClass().getResource("/main/fxml/startScreen.fxml"));

        scene = new Scene(root, 800, 450);
        stage = primaryStage;
        stage.setTitle("Man and the Monk");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public static void reload(FXMLLoader loader) throws Exception{
        root = loader.load();
        scene = new Scene(root, 800, 450);
        stage.setScene(scene);
    }


    public static void main(String[] args) {
        launch(args);
    }
}
