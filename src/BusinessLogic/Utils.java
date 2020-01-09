package BusinessLogic;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Utils {
    public static String arrayToString(List<String> array) {
        String arrayToStr = "";
        for(String str : array) {
            arrayToStr = arrayToStr + str + ";";
        }
        return arrayToStr;
    }

    public static String itemArrayToString(List<Item> itemArray) {
        String arrayToStr = "";
        for(Item item : itemArray) {
            arrayToStr = arrayToStr + item.toString() + ";";
        }
        return arrayToStr;
    }

    public static String sendRequestAndReturnAnswer(String request) {
        String answerString;
        try {
            Socket socket = ShopMain.socketGUI;
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

    public static ObservableList<TableItem> getTableItemObservableList(List<Item> itemList) {

        List<TableItem> tableItemList = getTableItemList(itemList);

        ObservableList<TableItem> observableTableItemList = FXCollections.observableArrayList(tableItemList);
        return observableTableItemList;
    }


    public static List<TableItem> getTableItemList(List<Item> itemList) {
        List<TableItem> tableItemList = new ArrayList<>();
        for (Item item : itemList) {
            tableItemList.add(new TableItem(item));
        }
        return tableItemList;
    }

}
