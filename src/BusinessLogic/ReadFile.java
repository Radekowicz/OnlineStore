package BusinessLogic;

import java.io.*;
import java.util.Scanner;


public class ReadFile {
    private Scanner items;

    public void openFile() {
        try {
            this.items = new Scanner(new File("files/items.txt"));

        } catch (FileNotFoundException e) {
            System.out.println("could not find file... \n");
        }
    }

//    public void readFile(School school) {
//        while (items != null && items.hasNext()) {
//            String subjectName = items.next();
//            school.getSubjectList().add(new Subject(subjectName));
//        }
//    }

    public static void readFile(LocalShop localShop) {

        try{
            // Open the file that is the first
            // command line parameter
            FileInputStream fstream = new FileInputStream("files/items.txt");
            // Get the object of DataInputStream
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


    public void closeFile() {
        if (items != null)
            items.close();
    }
}
