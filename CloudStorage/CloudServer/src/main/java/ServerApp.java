import ServerUtil.auth.AuthService;
import ServerUtil.auth.MSSqlAuthService;
import UserData.User;
import server.NettyServer;

import java.sql.SQLException;

public class ServerApp {

    static private NettyServer nettyServer;

    public static void main(String[] args) {
        try {
            MSSqlAuthService.getInstance().start();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        nettyServer = new NettyServer();
    }
}
