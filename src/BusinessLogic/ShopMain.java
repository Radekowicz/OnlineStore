package BusinessLogic;


import GUI.LoginMain;
import java.net.Socket;


public class ShopMain extends LoginMain{

    public static Socket socketGUI;
    public static Socket socketAC;


    public static void main(String[] args) {
        LoginMain.launch();
    }

    public static void setSocketGUI(Socket socketGUI) {
        ShopMain.socketGUI = socketGUI;
    }

    public static void setSocketAC(Socket socketAC) {
        ShopMain.socketAC = socketAC;
    }
}
