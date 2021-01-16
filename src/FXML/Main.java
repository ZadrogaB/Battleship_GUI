package FXML;

import company.GameController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    GameController gameController = new GameController();

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("BattleShip!");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
//        gameController.placeShips();
    }


    public static void main(String[] args) {
        launch(args);
    }

}
