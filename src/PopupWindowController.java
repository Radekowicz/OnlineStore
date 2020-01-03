import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


public class PopupWindowController implements Initializable {

    @FXML
    static AnchorPane anchorPane;

    @FXML
    TextField codeTextField;

    @FXML
    TextField nameTextField;

    @FXML
    TextField priceTextField;

    @FXML
    TextField quantityTextField;

    @FXML
    Button addItemButton;

    public static AnchorPane getAnchorPane() {
        return anchorPane;
    }

    public static void setAnchorPane(AnchorPane anchorPane) {
        PopupWindowController.anchorPane = anchorPane;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("xd");
    }

    public void addItemButtonClicked2() {
        createItem(codeTextField, nameTextField, priceTextField, quantityTextField);
        Stage stage = (Stage) addItemButton.getScene().getWindow();
        stage.close();
    }

        public static void createItem(TextField codeInput, TextField nameInput, TextField priceInput, TextField quantityInput) {
        try {
            int code = Integer.parseInt(codeInput.getText());
            String name = nameInput.getText();
            float price = Float.parseFloat(priceInput.getText());
            int quantity = Integer.parseInt(quantityInput.getText());
            Controller.getLocalShop().addItem(new Item(code, name, price, quantity));
        } catch (NumberFormatException e) {
            System.out.println("Wrong input");
        }
    }

}


