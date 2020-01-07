package BusinessLogic;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class AnsweringComponent implements Runnable {
    private LocalShop localShop;
    private Socket socket;



    @Override
    public void run() { //ta metoda będzie nasłuchiwać na sockecie na polecienia od Huba (Hub ma je od clienta), potem będzie w
        writeToServer();
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
        try{
            PrintWriter printWriter = new PrintWriter(socket.getOutputStream());
            String inputString = readFromServer();
            String[] array =  inputString.split(";");
            String requestString = array[0];


            if("searchItems".equals(requestString)) {
                String searchInput = array[1];

                List<Item> searchResults = localShop.search(searchInput);

                String searchResultsToString = Utils.itemArrayToString(searchResults); //item1;item2;item3...   item -> code,name,price,quantity;

                printWriter.println(searchResultsToString);
                printWriter.flush();
            }

            else if("sendItem".equals(requestString)) {
                String itemCode = array[1];
                String quantity = array[2];

                localShop.decreaseItemQuantity(localShop.searchByCode(Integer.parseInt(itemCode)), Integer.parseInt(quantity)); //zmniejsza ilosc produktów w sklepeie odpowiadajacym ale nie zwieksza w pytajacym!!!
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
