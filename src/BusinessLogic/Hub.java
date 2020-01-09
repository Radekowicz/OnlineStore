package BusinessLogic;

import javax.rmi.CORBA.Util;
import java.net.*;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import GUI.LoginWindowController;


public class Hub {

    private static HashMap<String, Socket> clientNameToSocketMap;

    public static List<LocalShop> onlineShopList;

    public static void addShop(LocalShop shop) {
        onlineShopList.add(shop);
    }

    public static void deleteShop(LocalShop shop) {
        List<String> shopList = new ArrayList<>(clientNameToSocketMap.keySet());
        if(shopList.contains(shop))
            clientNameToSocketMap.remove(shop);
        else System.out.println("shop not found");
    }




    public static void main(String[] args) throws IOException {
        onlineShopList = new ArrayList<>();
        System.out.println("Online shop list created");
        ServerSocket ss = new ServerSocket(4999);
        clientNameToSocketMap = new HashMap<>();


        while (true) {
            Socket s = ss.accept();
            Thread t = new Thread(() -> handleClient(s));
            t.start();
        }
    }

    public static void handleClient(Socket s) {
        try {
            System.out.println("client connected");

            InputStreamReader in = new InputStreamReader(s.getInputStream());
            BufferedReader bf = new BufferedReader(in);

            String userName = bf.readLine();
            clientNameToSocketMap.put(userName, s);
            System.out.println(userName);

            while (true) {
                String str = bf.readLine();
                if (str == null)
                    break;

                System.out.println("request: " + str);

                String[] array =  str.split(";");
                String requestString = array[0];

                if("getShopList".equals(requestString)) {
                    List<String> shopList = new ArrayList<>(clientNameToSocketMap.keySet());
                    String shopListToString = Utils.arrayToString(shopList);

                    PrintWriter recipientPrintWriter = new PrintWriter(s.getOutputStream());
                    recipientPrintWriter.println(shopListToString);
                    recipientPrintWriter.flush();
                }

                else if("searchItems".equals(requestString)) {
                    String shopName = array[1];
                    String searchInput = array[2];

                    Socket recipientSocket = clientNameToSocketMap.get(shopName);

                    PrintWriter recipientPrintWriter = new PrintWriter(recipientSocket.getOutputStream());
                    recipientPrintWriter.println(requestString + ";" + searchInput);
                    recipientPrintWriter.flush();
                }

                else if("sendItem".equals(requestString)) {
                    String shopName = array[1];
                    String itemCode = array[2];
                    String quantity = array[3];

                    Socket recipientSocket = clientNameToSocketMap.get(shopName);

                    PrintWriter recipientPrintWriter = new PrintWriter(recipientSocket.getOutputStream());
                    recipientPrintWriter.println(requestString + ";" + itemCode + ";" + quantity);
                    recipientPrintWriter.flush();

                    //pewnie jakiś if by się przydał do czekania

                    //listen
                    InputStreamReader in2 = new InputStreamReader(recipientSocket.getInputStream());
                    BufferedReader bf2 = new BufferedReader(in2);
                    String answerFromShop = bf2.readLine();

                    //answer to source
                    PrintWriter pr = new PrintWriter(s.getOutputStream());
                    pr.println(answerFromShop);
                    pr.flush();


                }


            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
