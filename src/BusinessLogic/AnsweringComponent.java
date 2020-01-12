package BusinessLogic;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class AnsweringComponent implements Runnable {
    private LocalShop localShop;
    private Socket socket;



    @Override
    public void run() {
        while (true) writeToServer();
    }

    public String readFromServer() {
        String inputString;
        try {
            InputStreamReader inputStreamReader = new InputStreamReader(socket.getInputStream());
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            inputString = bufferedReader.readLine();


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return inputString;
    }

    public void writeToServer() {
            try {
                PrintWriter printWriter = new PrintWriter(socket.getOutputStream());
                String inputString = readFromServer();
                String[] array = inputString.split(";");
                String requestString = array[0];
                System.out.println("input:" + inputString);


                if ("searchItems".equals(requestString)) {
                    String searchInput = array[1];

                    List<Item> searchResults = localShop.search(searchInput);

                    String searchResultsToString = Utils.itemArrayToString(searchResults); //item1;item2;item3...   item -> code,name,price,quantity;
                    System.out.println("output:" + searchResultsToString);


                    printWriter.println(searchResultsToString);
                    printWriter.flush();
                }

                else if("sendItem".equals(requestString)) {
                    System.out.println("i am in AC");
                    String itemCode = array[1];
                    String quantity = array[2];
                    Item item = localShop.searchByCode(Integer.parseInt(itemCode));
                    System.out.println("Decreased item:" +  item + ", by  " + quantity);
                    localShop.decreaseItemQuantity(item, Integer.parseInt(quantity)); //zmniejsza ilosc produktów w sklepeie odpowiadajacym ale nie zwieksza w pytajacym!!!
                }

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
    }

    public AnsweringComponent(LocalShop localShop, Socket socket) {
        this.localShop = localShop;
        this.socket = socket;
    }
}
