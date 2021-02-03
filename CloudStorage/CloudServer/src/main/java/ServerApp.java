import server.NettyServer;

public class ServerApp {

    static private NettyServer nettyServer;

    public static void main(String[] args) {
        nettyServer = new NettyServer();
    }
}
