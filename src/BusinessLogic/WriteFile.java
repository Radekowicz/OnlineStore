package BusinessLogic;

import java.io.*;

public class WriteFile {

    public static void writeToFile(LocalShop localShop, FileOutputStream out){
        try {

            for (Item item : localShop.getAllItemList()) {
                String string = item.intToString(item.getCode()) + " " + item.getName() + " " + item.floatToString(item.getPrice()) + " " + item.intToString(item.getQuantity()) + "\n";
                out.write(string.getBytes());

            }
            out.flush();
            out.close();
            System.out.println("data has been saved to file");
        } catch (Exception e) {
            System.out.println("could not write to file");
        }
    }
}
