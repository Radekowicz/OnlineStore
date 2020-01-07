package GUI;

import BusinessLogic.ShopMain;
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

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginWindowController  {

    @FXML
    AnchorPane anchorPane;

    @FXML
    TextField shopNameTextField;



    public void submitButtonClicked() {
        String shopName = shopNameTextField.getText();
        ShopMain.setShopName(shopName);

        Stage stage = (Stage) anchorPane.getScene().getWindow();
        stage.close();
        loadWindow("GUI/LocalPrimaryWindow.fxml", "Login");
    }

    public void loadWindow(String loc, String title) {


        try {
            Parent parent = FXMLLoader.load(getClass().getClassLoader().getResource(loc));
            Stage stage = new Stage(StageStyle.DECORATED);
            stage.setTitle(title);
            stage.setScene(new Scene(parent));
            stage.show();
        } catch (IOException ex) {
            System.out.println("exception");
        }
    }

}
