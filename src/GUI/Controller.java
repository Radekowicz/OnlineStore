package GUI;

import BusinessLogic.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.URL;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import static BusinessLogic.WriteFile.*;

public class Controller implements Initializable{

    private static LocalShop localShop;

    private Stage window;
    private static double windowWidth = 800;
    private double windowHeight = 540;

    @FXML
    public static BorderPane borderPane;

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

    @FXML
    ListView listView;




    private static TableItem selectedItem;

    public static LocalShop getLocalShop() {
        return localShop;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
//        try {
//            loadLoginWIndow();
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
        tableView();
    }

    public void loadLoginWIndow() throws IOException{
        PopupWindowController.setAnchorPane( FXMLLoader.load(getClass().getResource("GUI/LoginWindow.fxml")));
        loadWindow("GUI/LoginWindow.fxml", "Login");
    }


    public static void closeProgram() {

        writeToFile(localShop);
    }

    public void refreshButtonClicked() {
        String answer = sendRequestAndReturnAnswer("getShopList");
        String[] array =  answer.split(";");

        ObservableList<String> observableList = FXCollections.observableArrayList(array);
        listView.setItems(observableList);


    }


    public String sendRequestAndReturnAnswer(String request) {
        String answerString;
        try {
            Socket socket = ShopMain.socket;
            PrintWriter printWriter = new PrintWriter(socket.getOutputStream());

            printWriter.println(request);
            printWriter.flush();


            //waiting for answer
            InputStreamReader inputStreamReader = new InputStreamReader(socket.getInputStream());
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            answerString = bufferedReader.readLine();

        } catch (IOException e) {
                throw new RuntimeException(e);
        }
        return answerString;
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
//        if(!(selectedItem == null)) item.setQuantity(item.getQuantity() - Integer.valueOf(sellTextField.getText()));
        if(!(selectedItem == null)) localShop.decreaseItemQuantity(item, Integer.valueOf(sellTextField.getText()));

        tableView();
    }

    @FXML
    public void addItemButtonClicked() throws Exception {
        PopupWindowController.setAnchorPane( FXMLLoader.load(getClass().getResource("GUI/PopupWindow.fxml")));
        loadWindow("GUI/PopupWindow.fxml", "Add item");
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

    public void loadWindow(String loc, String title) {
        try {
            Parent parent = FXMLLoader.load(getClass().getResource(loc));
            Stage stage = new Stage(StageStyle.DECORATED);
            stage.setTitle(title);
            stage.setScene(new Scene(parent));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
        } catch (IOException ex) {
            System.out.println("exception");
        }
    }

    public static void setBorderPane(BorderPane borderPane) {
        Controller.borderPane = borderPane;
    }
}
