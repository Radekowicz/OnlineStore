package BusinessLogic;

public class TableItem {
    private int code;
    private String name;
    private String price;
    private int quantity;

    public TableItem(Item item) {
        code = item.getCode();
        name = item.getName();
        price = String.format("%.2f", item.getPrice());
        quantity = item.getQuantity();
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
