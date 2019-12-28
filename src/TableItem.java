public class TableItem {
    private int code;
    private String name;
    private int price;
    private int quantity;

    public TableItem(Item item) {
        code = item.getCode();
        name = item.getName();
        price = item.getPrice();
        quantity = item.getQuantity();
    }
}
