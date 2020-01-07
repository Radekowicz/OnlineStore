package GUI;

import GUI.Controller;
import javafx.application.Application;
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
        primaryStage.setScene(new Scene(root, 800, 540));
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

