public class ShopMain extends ControllerMain{
    public static void main(String[] args) {

        LocalShop localShop = new LocalShop("Zara");

        ReadFile.readFile(localShop);
        System.out.println("put input");

        Controller.setLocalShop(localShop);
        ControllerMain.launch(args);

    }
}
