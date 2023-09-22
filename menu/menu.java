package menu;
import Port.Port;
import User.Admin;
import User.PortManager;

import java.util.Scanner;

public class menu implements menuFunctions{
    @Override
    public void start() {
        Scanner scanner = new Scanner(System.in);
        int option;
        do {
            System.out.println("---------- Main Menu ----------");
            System.out.println("1. Login");
            System.out.println("2. Exit");
            System.out.print("Choose an action (1-2): ");
            option = scanner.nextInt();
            if (option == 1) {
                login();
            }
        } while (option != 2);
    }
    @Override
    public void login() {
        Scanner scanner = new Scanner(System.in);
        int option;
        do {
            System.out.println("---------- Main Menu ----------");
            System.out.println("1. Admin");
            System.out.println("2. Port Manager");
            System.out.println("3. Exit");
            System.out.print("Choose an action (1-3): ");
            option = scanner.nextInt();
            if (option == 1) {
                Admin admin = new Admin("a123", "123", "123", "admin");
                loginAdmin(admin);
            } else if (option == 2) {
                Port port = new Port();
                PortManager portManager = new PortManager("pm123", "123", "123", "admin", port);
                loginPortManager(portManager);
            } else {
                System.out.println("Invalid input! You should enter 1 or 2!");
            }
        } while (option != 3);
    }

    @Override
    public void loginAdmin(Admin admin) {
        String username;
        String password;
        Scanner scanner = new Scanner(System.in);
        System.out.print("Username: ");
        username = scanner.nextLine();
        System.out.print("Password: ");
        password = scanner.nextLine();
        if (admin.getUsername().equals(username) && admin.getPassword().equals(password)){
            AdminMenu adminMenu = new AdminMenu();
            adminMenu.startAdmin();
        } else {
            System.out.println("Wrong password or username");
        }
    }

    @Override
    public void loginPortManager(PortManager portManager) {
        String username;
        String password;
        Scanner scanner = new Scanner(System.in);
        System.out.print("Username: ");
        username = scanner.nextLine();
        System.out.print("Password: ");
        password = scanner.nextLine();
        if (portManager.getUsername().equals(username) && portManager.getPassword().equals(password)){
            System.out.println("Login successfully");
        } else {
            System.out.println("Wrong password or username");
        }
    }
}
