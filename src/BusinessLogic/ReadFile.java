package BusinessLogic;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class ReadFile {
    public static FileOutputStream out;
    public static File file;

    public static void readFile(LocalShop localShop) {

        try{
            File tmpDir = new File("files/" + localShop.getName() + ".txt");
            boolean exists = tmpDir.exists();

            System.out.println("reading file: "  + "files/" + localShop.getName() + ".txt");

            if(exists) {
                //do nothing
            }
            else {
                file = new File("files/" + localShop.getName() + ".txt");
                out = new FileOutputStream(file);
            }

            FileInputStream fstream = new FileInputStream("files/" + localShop.getName() + ".txt");

            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));

            String strLine;
            while ((strLine = br.readLine()) != null)   {
                String[] splited = strLine.split("\\s+");

                String code = splited[0];
                String name = splited[1];
                String price = splited[2];
                String quantity = splited[3];

                Item item = new Item(Integer.parseInt(code), name, Float.parseFloat(price), Integer.parseInt(quantity));
                localShop.addItem(item);
            }
            in.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
