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

import java.io.*;
import java.net.Socket;
import java.net.URL;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.ResourceBundle;


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
    ListView<String> listView;


    private static TableItem selectedItem;



    public static LocalShop getLocalShop() {
        return localShop;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tableView();
    }


    public void selectShop() {
        String selectedShopName = listView.getSelectionModel().getSelectedItem();

        loadWindow("GUI/OnlineSearchWindow.fxml", "Online search");
    }


    public static void closeProgram() {
        WriteFile.writeToFile(localShop, ReadFile.getOut());
        System.out.println("closebutton clicked");
//        Hub.deleteShop(localShop);
    }


    public void saveProgram() {
        WriteFile.writeToFile(localShop, ReadFile.getOut());
        System.out.println("closebutton clicked");
    }

    public void refreshButtonClicked() {
        String answer = sendRequestAndReturnAnswer("getShopList");
        String[] onlineShopArray =  answer.split(";");

        ObservableList<String> observableList = FXCollections.observableArrayList(onlineShopArray);
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
        if(!(selectedItem == null)) localShop.decreaseItemQuantity(item, Integer.valueOf(sellTextField.getText()));

        tableView();
    }

    @FXML
    public void addItemButtonClicked() throws Exception {
//        PopupWindowController.setAnchorPane( FXMLLoader.load(getClass().getResource("GUI/PopupWindow.fxml")));
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

    public static void setBorderPane(BorderPane borderPane) {
        Controller.borderPane = borderPane;
    }
}
