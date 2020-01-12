package BusinessLogic;

import java.io.*;
import java.text.DecimalFormat;

public class WriteFile {

    public static void writeToFile(LocalShop localShop, FileOutputStream out){
        try(FileWriter fw = new FileWriter("files/" + localShop.getName() + ".txt");
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter outt = new PrintWriter(bw))
        {
            for (Item item : localShop.getAllItemList()) {

                String string = (item.getCode()) + " " + item.getName() + " " + String.format("%.2f", item.getPrice()) + " " + item.getQuantity();
                outt.println(string);
            }
            outt.flush();
            outt.close();
            System.out.println("data has been saved to file");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
