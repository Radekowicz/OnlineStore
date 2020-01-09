package GUI;

import BusinessLogic.Item;
import BusinessLogic.TableItem;
import BusinessLogic.Utils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class OnlineController implements Initializable {

    @FXML
    private TableView<TableItem> tableItemTableView;

    @FXML
    private TableColumn<TableItem, Integer> codeColumn;

    @FXML
    private TableColumn<TableItem, String> nameColumn;

    @FXML
    private TableColumn<TableItem, Integer> priceColumn;

    @FXML
    private TableColumn<TableItem, Integer> quantityColumn;

    @FXML
    private TextField orderTextField;

    @FXML
    private Slider orderSlider;

    @FXML
    private Button orderButton;

    @FXML
    private TextField searchTextField;

    private ObservableList<TableItem> observableTableItemList;

    public static String shopName;

    public static void setShopName(String shopName) {
        OnlineController.shopName = shopName;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }


    public void searchButtonClicked() {
        String answer = Utils.sendRequestAndReturnAnswer("searchItems;" + shopName + ";" + searchTextField.getText());
        String[] searchedItems =  answer.split(";");
        List<TableItem> tableItemList = new ArrayList<>();
        for(int i = 0; i < searchedItems.length; i++) {
            String[] itemFields = searchedItems[i].split(",");
            int code = Integer.parseInt(itemFields[0]);
            String name = itemFields[1];
            float price = Float.parseFloat(itemFields[2]);
            int quantity = Integer.parseInt(itemFields[3]);
            tableItemList.add(new TableItem(new Item(code,name,price,quantity)));
        }
        observableTableItemList = FXCollections.observableArrayList(tableItemList);
        tableView(observableTableItemList);
        System.out.println(answer);
    }








    private static final int INIT_VALUE = 1;
    public void setOrderSlider() {
        orderSlider.setValue(INIT_VALUE);
        TableItem selectedItem = tableItemTableView.getSelectionModel().getSelectedItem();
        if(!(selectedItem == null)) orderSlider.setMax(selectedItem.getQuantity());
        orderTextField.setText(Integer.toString(INIT_VALUE));
        orderTextField.textProperty().bindBidirectional(orderSlider.valueProperty(), NumberFormat.getNumberInstance());
        orderSlider.valueProperty().addListener((obs, oldval, newVal) -> orderSlider.setValue(newVal.intValue()));


    }

    public void orderButtonClicked() {
//        TableItem selectedItem = tableItemTableView.getSelectionModel().getSelectedItem();
//        Item item = localShop.searchByCode(selectedItem.getCode());
//        if(!(selectedItem == null)) localShop.decreaseItemQuantity(item, Integer.valueOf(orderTextField.getText()));
//
//        tableView();
    }


    public void tableView(ObservableList<TableItem> observableTableItemList) {
        // code
        codeColumn.setCellValueFactory(new PropertyValueFactory<>("code"));

        //name
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        //price
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        //quantity
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));

        tableItemTableView.setItems(observableTableItemList);


    }






}
