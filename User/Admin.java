package User;

import Port.Port;

import java.util.List;

public class Admin extends User{
    private List<Port> portList;
    public Admin(String userID, String username, String password, String role){
        super(userID, username, password, "Admin");
    }

}
