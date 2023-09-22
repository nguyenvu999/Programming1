package menu;

import User.Admin;
import User.PortManager;

public interface menuFunctions {
    void start();
    void login();
    void loginAdmin(Admin admin);
    void loginPortManager(PortManager portManager);
}
