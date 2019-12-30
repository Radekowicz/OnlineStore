import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        LocalShop localShop = new LocalShop("Zara");

        localShop.addItem(new Item(12345, "baton", 3, 60));
        localShop.addItem(new Item(23456, "jajko", 2, 100));
        localShop.addItem(new Item(34567, "maslo", 6, 20));
        System.out.println("put input");


        Controller.setLocalShop(localShop);

        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("shop");
        primaryStage.setScene(new Scene(root, 800, 540));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}

