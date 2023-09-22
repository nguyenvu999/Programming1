import menu.menu;

public class Main {
    public static void main(String[] args) {
//        List<Containers> lc1 = new ArrayList<Containers>();
//        Vehicles v1 = new Truck("a", "001", 100, 200, 50,lc1);
//        System.out.println(v1.getClass());
//        Containers c1 = new DryStorage(200, "002", v1);
//        Containers c2 = new Refrigerated(200, "002", v1);
//        System.out.println(c1.getClass());
//        Vehicles v2 = new ReeferTruck("a", "001", 100, 200, 50, lc1);
//        v2.addContainers(c2);
//        System.out.println(v2.getContainersList());
//        v2.removeContainers(c2);
//        System.out.println(v2.getContainersList());
        menu menu = new menu();
        menu.start();
    }
}
