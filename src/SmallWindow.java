//import javafx.scene.Scene;
//import javafx.scene.control.TextField;
//import javafx.scene.layout.BorderPane;
//import javafx.scene.layout.HBox;
//import javafx.scene.layout.VBox;
//import javafx.stage.Modality;
//import javafx.stage.Stage;
//
//import javafx.scene.control.Button;
//
//
//public class SmallWindow extends Controller {
//
//
//    public static void display(String title) {
//        Stage window = new Stage();
//
//        window.initModality(Modality.APPLICATION_MODAL);
//        window.setTitle(title);
//        window.setMinWidth(250);
//        window.setMinHeight(250);
//
//        BorderPane borderPane = new BorderPane();
//
//        TextField codeInput = new TextField();
//        codeInput.setPromptText("Code");
//        TextField nameInput = new TextField();
//        nameInput.setPromptText("Name");
//        TextField priceInput = new TextField();
//        priceInput.setPromptText("Price");
//        TextField quantityInput = new TextField();
//        quantityInput.setPromptText("Quantity");
//
//        VBox vBox = new VBox();
//        vBox.getChildren().addAll(codeInput, nameInput, priceInput, quantityInput);
//
//        Button addItemButton = new Button("Add Item");
//        addItemButton.setOnAction(event -> {
//            createItem(codeInput, nameInput, priceInput, quantityInput);
//            window.close();
//        });
//
//        Button closeButton = new Button("Close");
//        closeButton.setOnAction(event -> window.close());
//
//        HBox hBox = new HBox();
//        hBox.getChildren().addAll(addItemButton, closeButton);
//
//        borderPane.setCenter(vBox);
//        borderPane.setBottom(hBox);
//
//
//         Scene scene = new Scene(borderPane);
//         window.setScene(scene);
//         window.showAndWait();
//
//
//
//    }
//
////    public static void createItem(TextField codeInput, TextField nameInput, TextField priceInput, TextField quantityInput) {
////        try {
////            int code = Integer.parseInt(codeInput.getText());
////            String name = nameInput.getText();
////            float price = Float.parseFloat(priceInput.getText());
////            int quantity = Integer.parseInt(quantityInput.getText());
////            Controller.getLocalShop().addItem(new Item(code, name, price, quantity));
////        } catch (NumberFormatException e) {
////            System.out.println("Wrong input");
////        }
////    }
//
//
//}
