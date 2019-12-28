public class ShopMain extends GUI {
    public static void main(String[] args) {

        ShopInterface shop = new ShopInterface("Zara");

        shop.addItem(new Item(12345, "baton", 3, 60));
        shop.addItem(new Item(23456, "jajko", 2, 100));
        shop.addItem(new Item(34567, "maslo", 6, 20));
        System.out.println("put input");

        GUI.setShop(shop);


//        Scanner scanner = new Scanner(System.in);
//        String input = scanner.nextLine();

        GUI.launch(args);


    }
}
