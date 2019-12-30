import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class PopupWindowController implements Initializable {

    @FXML
    static AnchorPane anchorPane;

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
}


