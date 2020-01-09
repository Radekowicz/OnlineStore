package GUI;

import BusinessLogic.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;

import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.*;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

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
                System.exit(0);
            }); //exit program
            stage.show();
        } catch (IOException ex) {
            System.out.println("exception");
        }
    }


    public void sendShopName(String shopName) {
        try {
            Socket socket = new Socket("localhost", 4999);
            ShopMain.setSocket(socket);
            PrintWriter printWriter = new PrintWriter(socket.getOutputStream());
            printWriter.println(shopName);
            printWriter.flush();
            LocalShop localShop = new LocalShop(shopName);
            AnsweringComponent answeringComponent = new AnsweringComponent(localShop, socket);
            Thread thread = new Thread(answeringComponent);
            thread.start();

            Socket socket2 = new Socket("localhost", 4999);


            ReadFile.readFile(localShop);

            Controller.setLocalShop(localShop);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
