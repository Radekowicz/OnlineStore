package BusinessLogic;

import GUI.Controller;
import GUI.ControllerMain;
import GUI.LoginMain;
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


    public static final Object monitor = new Object();
    public static boolean monitorState = false;

    public static void main(String[] args) {

//        Scanner scanner = new Scanner(System.in);
//        System.out.println("Gimme shop name");
//        String shopName = scanner.next();

        String shopName = "dfgdfg";
//
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//            }
//        }).start();
//        waitForThread();


        localShop = new LocalShop(shopName);
        try {
            socket = new Socket("localhost", 4999);
            PrintWriter printWriter = new PrintWriter(socket.getOutputStream());
            printWriter.println(shopName);
            printWriter.flush();

            AnsweringComponent answeringComponent = new AnsweringComponent(localShop, socket);

            Thread thread = new Thread(answeringComponent);
            thread.start();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        ReadFile.readFile(localShop);

        Controller.setLocalShop(localShop);

        LoginMain.launch();
//        ControllerMain.launch(args);

    }

    public static void setShopName(String shopName) {
        ShopMain.shopName = shopName;
    }

    public static void waitForThread() {
        monitorState = true;
        while (monitorState) {
            synchronized (monitor) {
                try {
                    monitor.wait(); // wait until notified
                } catch (Exception e) {
                }
            }
        }
    }

    public static void unlockWaiter() {
        synchronized (monitor) {
            monitorState = false;
            monitor.notifyAll(); // unlock again
        }
    }

}
