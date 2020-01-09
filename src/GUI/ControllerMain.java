package GUI;

import GUI.Controller;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import BusinessLogic.*;



public class ControllerMain extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("GUI/LocalPrimaryWindow.fxml"));
        primaryStage.setTitle(ShopMain.localShop.getName());
        primaryStage.setScene(new Scene(root));
        primaryStage.setOnCloseRequest(e -> System.exit(0));
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }
}

