import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class ControllerMain extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        Parent root = FXMLLoader.load(getClass().getResource("LocalPrimaryWindow.fxml"));
        primaryStage.setTitle("shop");
        primaryStage.setScene(new Scene(root, 800, 540));
        primaryStage.setOnCloseRequest(event -> {
            Controller.closeProgram();
            primaryStage.close();
        });
        primaryStage.show();

    }
}

