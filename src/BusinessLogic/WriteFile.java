package BusinessLogic;

import java.io.*;

public class WriteFile {

    public static void writeToFile(LocalShop localShop){
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("files/items.txt"));
            for (Item item : localShop.getAllItemList()) {
                String string = item.intToString(item.getCode()) + " " + item.getName() + " " + item.floatToString(item.getPrice()) + " " + item.intToString(item.getQuantity()) + "\n";
                writer.write(string);
            }
            writer.close();
        } catch (Exception e) {
            System.out.println("could not write to file");
        }
    }
}
