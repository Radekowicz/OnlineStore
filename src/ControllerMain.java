import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class ControllerMain extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("shop");
        primaryStage.setScene(new Scene(root, 800, 540));
        primaryStage.show();

//        Parent root2 = FXMLLoader.load(getClass().getResource("PopupWindow.fxml"));


    }
}

