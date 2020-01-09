package BusinessLogic;

import GUI.Controller;

import java.io.*;
import java.util.Scanner;


public class ReadFile {
    public static FileOutputStream out;

    public static File file;

    public static void readFile(LocalShop localShop) {

        try{
            System.out.println("i am reading file: "  + "files/" + localShop.getName() + ".txt");
            if(file != null && file.exists()) {
            }
            else {
                file = new File("files/" + localShop.getName() + ".txt");
                out = new FileOutputStream(file);
            }


//            out = new FileOutputStream(file);

            FileInputStream fstream = new FileInputStream("files/" + localShop.getName() + ".txt");

            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));

            String strLine;
            //Read File Line By Line
            while ((strLine = br.readLine()) != null)   {
                String[] splited = strLine.split("\\s+");

                String code = splited[0];
                String name = splited[1];
                String price = splited[2];
                String quantity = splited[3];

                Item item = new Item(Integer.parseInt(code), name, Float.parseFloat(price), Integer.parseInt(quantity));
                localShop.addItem(item);
            }
            //Close the input stream
            in.close();
        }catch (Exception e){//Catch exception if any
            System.err.println("Error: " + e.getMessage());
        }
    }

    public static FileOutputStream getOut() {
        return out;
    }
}
