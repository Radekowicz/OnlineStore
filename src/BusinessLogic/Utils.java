package BusinessLogic;

import java.util.List;

public class Utils {
    public static String arrayToString(List<String> array) {
        String arrayToStr = "";
        for(String str : array) {
            arrayToStr = arrayToStr + str + ";";
        }
        return arrayToStr;
    }

    public static String itemArrayToString(List<Item> itemArray) {
        String arrayToStr = "";
        for(Item item : itemArray) {
            arrayToStr = arrayToStr + item.toString() + ";";
        }
        return arrayToStr;
    }



}
