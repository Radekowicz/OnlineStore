import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.net.URL;
import java.rmi.activation.ActivationGroup_Stub;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    private static LocalShop localShop;

    private Stage window;
    private static double windowWidth = 800;
    private double windowHeight = 540;

    @FXML
    private BorderPane borderPane;

    @FXML
    private TableView<TableItem> tableItemTableView;

    @FXML
    TableColumn<TableItem, Integer> codeColumn;

    @FXML
    TableColumn<TableItem, String> nameColumn;

    @FXML
    TableColumn<TableItem, Integer> priceColumn;

    @FXML
    TableColumn<TableItem, Integer> quantityColumn;

    @FXML
    private TextField searchTextField;

    @FXML
    TextField sellTextField;

    @FXML
    Slider sellSlider;



    private static TableItem selectedItem;

    public static LocalShop getLocalShop() {
        return localShop;
    }



    private static final int INIT_VALUE = 1;
    public void setSellSlider() {
        sellSlider.setValue(INIT_VALUE);
        selectedItem = tableItemTableView.getSelectionModel().getSelectedItem();
        if(!(selectedItem == null)) sellSlider.setMax(selectedItem.getQuantity());
        sellTextField.setText(Integer.toString(INIT_VALUE));
        sellTextField.textProperty().bindBidirectional(sellSlider.valueProperty(), NumberFormat.getNumberInstance());
        sellSlider.valueProperty().addListener((obs, oldval, newVal) -> sellSlider.setValue(newVal.intValue()));


    }

    public void sellButtonClicked() {
        selectedItem = tableItemTableView.getSelectionModel().getSelectedItem();
        Item item = localShop.searchByCode(selectedItem.getCode());
        if(!(selectedItem == null)) item.setQuantity(item.getQuantity() - Integer.valueOf(sellTextField.getText()));
        tableView();
    }




    public void addItemButtonClicked() {
        SmallWindow.display("Blabla");
        tableView();
    }

    public void deleteItemButtonClicked() {
        selectedItem = tableItemTableView.getSelectionModel().getSelectedItem();
        localShop.deleteItem(localShop.searchByCode(selectedItem.getCode()));
        tableView();
    }


    public void searchField() {
        String typedString = searchTextField.getText();
        List<Item> itemList = localShop.search(typedString);
        tableItemTableView.setItems(getTableItemObservableList(itemList));
    }


    public void tableView() {
        // code
        codeColumn.setCellValueFactory(new PropertyValueFactory<>("code"));

        //name
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        //price
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        //quantity
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));

//        codeColumn.prefWidthProperty().bind(tableItemTableView.widthProperty().divide(2.5)); // w * 1/4
//        nameColumn.prefWidthProperty().bind(tableItemTableView.widthProperty().divide(2.5)); // w * 1/4
//        priceColumn.prefWidthProperty().bind(tableItemTableView.widthProperty().divide(2.5)); // w * 1/4
//        quantityColumn.prefWidthProperty().bind(tableItemTableView.widthProperty().divide(2.5)); // w * 1/4


        ObservableList<TableItem> observableTableItemList = getTableItemObservableList(localShop.getAllItemList());
        tableItemTableView.setItems(observableTableItemList);
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tableView();
    }

    public static List<TableItem> getTableItemList(List<Item> itemList) {
        List<TableItem> tableItemList = new ArrayList<>();
        for (Item item : itemList) {
            tableItemList.add(new TableItem(item));
        }
        return tableItemList;
    }


    public static ObservableList<TableItem> getTableItemObservableList(List<Item> itemList) {

        List<TableItem> tableItemList = getTableItemList(itemList);

        ObservableList<TableItem> observableTableItemList = FXCollections.observableArrayList(tableItemList);
        return observableTableItemList;
    }

    public static void setLocalShop(LocalShop localShop) {
        Controller.localShop = localShop;
    }
}
