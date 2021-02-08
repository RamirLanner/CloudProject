package model;

import controller.MainViewController;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;

public class NettyNetwork {
    private EventLoopGroup workerGroup ;
    private ChannelFuture future;
    private String host;
    private int port;
    boolean connectedStatus;
    private NettyNetwork net;
    private MainViewController viewController;
    private ClientHandler handler;

    public ClientHandler getHandler() {
        return handler;
    }

    public void setHandler(ClientHandler handler) {
        this.handler = handler;
    }

    public NettyNetwork(String host, int port) {
        this.host = host;
        this.port = port;
        this.net = this;
    }

    public boolean isConnected() {
        return connectedStatus;
    }

    public void connect(){
        try {
            workerGroup = new NioEventLoopGroup();
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(workerGroup)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.SO_KEEPALIVE, true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast(
                                    new ObjectDecoder(ClassResolvers.cacheDisabled(null)),
                                    new ObjectEncoder(),
                                    new ClientHandler(net));
                        }
                    });
            // Start the client
            future = bootstrap.connect(host, port).sync();
            connectedStatus = true;
            System.out.println("Connect to server success!");
            future.channel().closeFuture().sync();

            // Wait until the connection is closed.
            future.channel().closeFuture().sync();
        }catch (InterruptedException e){
            e.printStackTrace();
        } finally {
            workerGroup.shutdownGracefully();
        }
    }

    public void close(){
        if(connectedStatus){
            try {
                future.channel().close();
                System.out.println("Connection is closed");
                future.channel().closeFuture().sync();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                workerGroup.shutdownGracefully();
            }
        }
    }

    public void setViewController(MainViewController viewController) {
        this.viewController = viewController;
    }

    public MainViewController getViewController() {
        return viewController;
    }
}
