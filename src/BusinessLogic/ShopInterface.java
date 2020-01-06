package BusinessLogic;

public interface ShopInterface {
    Item searchByCode(int code);
    Item searchByName(String name);
}
