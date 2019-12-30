public class ShopMain extends ControllerMain{
    public static void main(String[] args) {

        LocalShop localShop = new LocalShop("Zara");

        localShop.addItem(new Item(12345, "baton", 3, 60));
        localShop.addItem(new Item(23456, "jajko", 2, 100));
        localShop.addItem(new Item(34567, "maslo", 6, 20));
        System.out.println("put input");

        Controller.setLocalShop(localShop);
        ControllerMain.launch(args);

    }
}
