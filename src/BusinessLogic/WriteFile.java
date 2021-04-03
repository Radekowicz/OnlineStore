package BusinessLogic;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class WriteFile {

    public static void writeToFile(LocalShop localShop){
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
            System.out.println("data have been saved to file");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
