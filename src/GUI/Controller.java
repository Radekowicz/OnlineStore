package GUI;

import BusinessLogic.Item;
import BusinessLogic.LocalShop;
import BusinessLogic.TableItem;
import BusinessLogic.Utils;
import BusinessLogic.WriteFile;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import java.io.IOException;
import java.net.URL;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;


public class Controller implements Initializable{

    private static LocalShop localShop;

    @FXML
    public static BorderPane borderPane;

    @FXML
    private TableView<TableItem> tableItemTableView;

    private static ObservableList<TableItem> observableTableItemList;

    @FXML
    TableColumn<TableItem, Integer> codeColumn;

    @FXML
    TableColumn<TableItem, String> nameColumn;

    @FXML
    TableColumn<TableItem, Integer> priceColumn;

    @FXML
    TableColumn<TableItem, Integer> quantityColumn;

    @FXML
    TextField searchTextField;

    @FXML
    TextField sellTextField;

    @FXML
    Slider sellSlider;

    @FXML
    Button sellButton;

    @FXML
    ListView<String> listView;

    @FXML
    Button selectButton;

     private static Controller fooController;

    public static Controller getFooController() {
        return fooController;
    }

    public static void setFooController(Controller fooController) {
        Controller.fooController = fooController;
    }

    public static ObservableList<TableItem> getObservableTableItemList() {
        return observableTableItemList;
    }

    private static TableItem selectedItem;

    public static LocalShop getLocalShop() {
        return localShop;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        selectButton.setDisable(true);
        refreshButtonClicked();
        tableView();

        listView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
               selectButton.setDisable(false);
            }
        });
    }

//    public static void increaseQuantity(LocalShop shop, TableItem tableItem, int quantity) {
//        Item item = new Item(tableItem.getCode(), tableItem.getName(), Float.parseFloat(tableItem.getPrice()), tableItem.getQuantity() + quantity);
//
//        shop.getAllItemList().remove(shop.searchByCode(selectedItem.getCode()));
//        observableTableItemList.remove(tableItem);
//
//        shop.getAllItemList().add(item);
//        observableTableItemList.add(new TableItem(item));
//    }

    public static void increaseItemQuantityTI(TableItem tableItem, int quantity) {
        tableItem.setQuantity(tableItem.getQuantity() + quantity);
    }

    public void selectShop() {
        String selectedShopName = listView.getSelectionModel().getSelectedItem();
        OnlineController.setShopName(selectedShopName);
        listView.getSelectionModel().clearSelection();
        loadOnlineWindow("GUI/OnlineSearchWindow.fxml", "Online search");
    }

    public static void closeProgram() {
        WriteFile.writeToFile(localShop);
    }

    public void saveProgram() {
        WriteFile.writeToFile(localShop);
    }

    public void refreshButtonClicked() {
        String answer = Utils.sendRequestAndReturnAnswer("getShopList");
        String[] onlineShopArray =  answer.split(";");

        List<String> list = new ArrayList<>(Arrays.asList(onlineShopArray));
        list.remove(localShop.getName());

        ObservableList<String> observableList = FXCollections.observableArrayList(list);
        listView.setItems(observableList);
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
        if(!(selectedItem == null)) localShop.decreaseItemQuantity(item, Integer.valueOf(sellTextField.getText()));

        tableView();
    }

    @FXML
    public void addItemButtonClicked() {

        loadWindow("GUI/PopupWindow.fxml", "Add item");
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
        tableItemTableView.setItems(Utils.getTableItemObservableList(itemList));
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

        observableTableItemList = Utils.getTableItemObservableList(localShop.getAllItemList());
        tableItemTableView.setItems(observableTableItemList);
    }



    public static void setLocalShop(LocalShop localShop) {
        Controller.localShop = localShop;
    }

    public void loadWindow(String loc, String title) {
        try {
            Parent parent = FXMLLoader.load(getClass().getClassLoader().getResource(loc));
            Stage stage = new Stage(StageStyle.DECORATED);
            stage.setTitle(title);
            stage.setScene(new Scene(parent));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
        } catch (IOException ex) {
            System.out.println("exception");
        }
    }

    public void loadOnlineWindow(String loc, String title) {
        try {
            Parent parent = FXMLLoader.load(getClass().getClassLoader().getResource(loc));
            Stage stage = new Stage(StageStyle.DECORATED);
            stage.setTitle(title);
            stage.setScene(new Scene(parent));
            stage.setOnCloseRequest(event -> tableView());

            stage.show();
        } catch (IOException ex) {
            System.out.println("exception");
        }
    }
}
