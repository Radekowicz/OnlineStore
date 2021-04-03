package BusinessLogic;

import java.net.*;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class Hub {

    private static HashMap<String, Socket> shopNameToSocketGUIMap;
    private static HashMap<String, Socket> shopNameToSocketACMap;


    public static void main(String[] args) throws IOException {
        System.out.println("Hub launched");
        ServerSocket ss = new ServerSocket(4999);
        shopNameToSocketGUIMap = new HashMap<>();
        shopNameToSocketACMap = new HashMap<>();


        while (true) {
            Socket socketGUI = ss.accept();
            Socket socketAC = ss.accept();

            Thread t = new Thread(() -> handleClient(socketGUI, socketAC));
            t.start();
        }
    }

    public static void handleClient(Socket socketGUI, Socket socketAC) {
        try {

            InputStreamReader in = new InputStreamReader(socketGUI.getInputStream());
            BufferedReader bf = new BufferedReader(in);

            String userName = bf.readLine();
            shopNameToSocketGUIMap.put(userName, socketGUI);
            shopNameToSocketACMap.put(userName, socketAC);
            System.out.println("Shop connected: " + userName);

            while (true) {
                String str = bf.readLine();
                if (str == null)
                    break;

                String[] array =  str.split(";");
                String requestString = array[0];

                if("getShopList".equals(requestString)) {
                    List<String> shopList = new ArrayList<>(shopNameToSocketGUIMap.keySet());
                    String shopListToString = Utils.arrayToString(shopList);

                    PrintWriter recipientPrintWriter = new PrintWriter(socketGUI.getOutputStream());
                    recipientPrintWriter.println(shopListToString);
                    recipientPrintWriter.flush();
                }

                else if("searchItems".equals(requestString)) {
                    System.out.println("in searchItems");
                    String shopName = array[1];
                    String searchInput = array[2];

                    Socket recipientSocketAC = shopNameToSocketACMap.get(shopName);

                    PrintWriter recipientPrintWriter = new PrintWriter(recipientSocketAC.getOutputStream());
                    recipientPrintWriter.println(requestString + ";" + searchInput);
                    recipientPrintWriter.flush();

                    listenAndAnswer(recipientSocketAC, socketGUI);
                }

                else if("sendItem".equals(requestString)) {
                    System.out.println("in sendItem");
                    String shopName = array[1];
                    String itemCode = array[2];
                    String quantity = array[3];

                    Socket recipientSocketAC = shopNameToSocketACMap.get(shopName);

                    PrintWriter recipientPrintWriter = new PrintWriter(recipientSocketAC.getOutputStream());
                    recipientPrintWriter.println(requestString + ";" + itemCode + ";" + quantity);
                    recipientPrintWriter.flush();

                    listenAndAnswer(recipientSocketAC, socketGUI);

                }

                else if("removeShop".equals(requestString)) {
                    String shopName = array[1];

                    shopNameToSocketGUIMap.remove(shopName);
                    shopNameToSocketACMap.remove(shopName);

                    System.out.println("Shop closed: " + shopName);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void listenAndAnswer(Socket recipientSocketAC, Socket socketGUI) throws IOException{
        //listen
        InputStreamReader in2 = new InputStreamReader(recipientSocketAC.getInputStream());
        BufferedReader bf2 = new BufferedReader(in2);
        String answerFromShop = bf2.readLine();

        //answer to source
        PrintWriter pr = new PrintWriter(socketGUI.getOutputStream());
        pr.println(answerFromShop);
        pr.flush();
    }

}
