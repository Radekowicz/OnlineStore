package GUI;

import GUI.Controller;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import BusinessLogic.*;



public class LoginMain extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("GUI/LoginWindow.fxml"));
        primaryStage.setTitle("Login window");
        primaryStage.setScene(new Scene(root, 500, 300));
        primaryStage.setOnCloseRequest(event -> {
            Controller.closeProgram();
            primaryStage.close();
        });
        primaryStage.show();

    }
    public static void main(String[] args) {
        launch(args);
    }
}

