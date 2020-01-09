package BusinessLogic;

import GUI.Controller;
import GUI.ControllerMain;
import GUI.LoginMain;
import GUI.LoginWindowController;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ShopMain extends LoginMain{

    public static LocalShop localShop;

    public static Socket socket;

    public static String shopName;



    public static void main(String[] args) {

//        Scanner scanner = new Scanner(System.in);
//        System.out.println("Gimme shop name");
//        String shopName = scanner.next();

//        String shopName = "dfgdfg";


//            localShop = new LocalShop(shopName);



//        try {
//            socket = new Socket("localhost", 4999);
//            PrintWriter printWriter = new PrintWriter(socket.getOutputStream());
//            printWriter.println(shopName);
//            printWriter.flush();
//
//            localShop = new LocalShop(shopName);
//
//            AnsweringComponent answeringComponent = new AnsweringComponent(localShop, socket);
//
//            Thread thread = new Thread(answeringComponent);
//            thread.start();
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }

//        ReadFile.readFile(localShop);
//
//        Controller.setLocalShop(localShop);

        LoginMain.launch();
//        ControllerMain.launch(args);

    }

    public static void setShopName(String shopName) {
        ShopMain.shopName = shopName;
    }


    public static void setSocket(Socket socket) {
        ShopMain.socket = socket;
    }
}
