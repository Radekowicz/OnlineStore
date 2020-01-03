import java.util.ArrayList;
import java.util.List;

public class LocalShop implements ShopInterface {
    private String name;
    private List<Item> allItemList;

    public LocalShop(String name) {
        this.name = name;
        allItemList = new ArrayList<>();
    }


    public void addItem(Item item) {
        allItemList.add(item);
    }

    public void deleteItem(Item item) {
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



    public List<Item> search(String typedString) {
        List<Item> searchedItemList = new ArrayList<>();
        for(Item item : allItemList) {
            if((item.intToString(item.getCode()).toUpperCase()).contains(typedString.toUpperCase())) searchedItemList.add(item);
            else if(((item.getName().toUpperCase()).contains(typedString.toUpperCase()))) searchedItemList.add(item);
            else if((item.floatToString(item.getPrice()).toUpperCase()).contains(typedString.toUpperCase())) searchedItemList.add(item);
            else if((item.intToString(item.getQuantity()).toUpperCase()).contains(typedString.toUpperCase())) searchedItemList.add(item);
        }
        return searchedItemList;
    }

    @Override
    public Item searchByCode(int code) {
        for(Item item : allItemList) {
            if(item.getCode() == code) return item;
        }
        return null;
    }

    @Override
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
