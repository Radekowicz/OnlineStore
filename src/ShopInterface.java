import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class ShopInterface{
    private String name;
    private List<Item> allItemList;

    public ShopInterface(String name) {
        this.name = name;
        allItemList = new ArrayList<>();
    }


    public void addItem(Item item) {
        allItemList.add(item);
    }

    public void removeItem(Item item) {
        allItemList.remove(item);
    }

    public void increaseItemQuantity(Item item, int quantity) {
        Item foundItem = findItem(item);
        foundItem.setQuantity(foundItem.getQuantity() + quantity);
    }

    public void decreaseItemQuantity(Item item, int quantity) {
        Item foundItem = findItem(item);
        foundItem.setQuantity(foundItem.getQuantity() - quantity);
    }


    public Item searchByCode(int code) {
        for(Item item : allItemList) {
            if(item.getCode() == code) return item;
        }
        return null;
    }

    public Item searchByName(String name) {
        for(Item item : allItemList) {
            if(item.getName().equals(name)) return item;
        }
        return null;
    }

    public Item findItem(Item item) {
        for(Item i : allItemList) {
            if(item.equals(i)) return i;
        }
        return null;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Item> getAllItemList() {
        return allItemList;
    }

}
