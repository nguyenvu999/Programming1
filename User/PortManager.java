package User;
import Port.*;

public class PortManager extends User{
    private Port port;
    public PortManager(String userID, String username, String password, String role, Port port){
        super(userID, username, password, "Port Manager");
        this.port = port;
    }

    public Port getPort() {
        return port;
    }

    public void setPort(Port port) {
        this.port = port;
    }
}
