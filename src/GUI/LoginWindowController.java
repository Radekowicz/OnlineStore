package GUI;

import BusinessLogic.AnsweringComponent;
import BusinessLogic.LocalShop;
import BusinessLogic.ReadFile;
import BusinessLogic.ShopMain;
import BusinessLogic.Utils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class LoginWindowController  {

    @FXML
    AnchorPane anchorPane;

    @FXML
    TextField shopNameTextField;


    public void submitButtonClicked() {
        String shopName = shopNameTextField.getText();
        sendShopName(shopName);

        Stage stage = (Stage) anchorPane.getScene().getWindow();
        stage.close();
        loadWindow("GUI/LocalPrimaryWindow.fxml", shopName);
    }

    public void loadWindow(String loc, String title) {
        try {
            Parent parent = FXMLLoader.load(getClass().getClassLoader().getResource(loc));
            Stage stage = new Stage(StageStyle.DECORATED);
            stage.setTitle(title);
            stage.setScene(new Scene(parent));
            stage.setOnCloseRequest(e -> {
                Controller.closeProgram();
                System.out.println("removeShop;" + Controller.getLocalShop().getName());
                Utils.sendRequest("removeShop;" + Controller.getLocalShop().getName());
                System.exit(0);
            }); //exit program
            stage.show();
        } catch (IOException ex) {
            System.out.println("exception");
        }
    }


    public void sendShopName(String shopName) {
        try {
            Socket socketGUI = new Socket("localhost", 4999);
            Socket socketAC = new Socket("localhost", 4999);
            ShopMain.setSocketGUI(socketGUI);
            ShopMain.setSocketAC(socketAC);

            PrintWriter printWriter = new PrintWriter(socketGUI.getOutputStream()); //wysła nazwe sklepu do gui
            printWriter.println(shopName);
            printWriter.flush();

            LocalShop localShop = new LocalShop(shopName);
            AnsweringComponent answeringComponent = new AnsweringComponent(localShop, socketAC); //tutaj inny socketAC pomiedzy AC a hubem
            Thread thread = new Thread(answeringComponent);
            thread.start();

            ReadFile.readFile(localShop);

            Controller.setLocalShop(localShop);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
