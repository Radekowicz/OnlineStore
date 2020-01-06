package BusinessLogic;

import GUI.Controller;
import GUI.ControllerMain;

import java.io.IOException;
import java.net.Socket;

public class ShopMain  extends ControllerMain  {
    public static void main(String[] args) throws IOException {

        LocalShop localShop = new LocalShop("Zara");

        Socket socket = new Socket("localhost", 4999);

        AnsweringComponent answeringComponent = new AnsweringComponent(localShop, socket);

        Thread thread = new Thread(answeringComponent);
        thread.start();


        ReadFile.readFile(localShop);

        Controller.setLocalShop(localShop);
        ControllerMain.launch(args);

    }
}
