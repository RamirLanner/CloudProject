package model;

import commands.Command;
import commands.assembly.AuthOkCommandData;
import controller.MainViewController;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class ClientHandler extends SimpleChannelInboundHandler<Command> {

    NettyNetwork net;
    ChannelHandlerContext user;
    MainViewController viewController;

    public ClientHandler(NettyNetwork net) {
        this.net = net;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        user = ctx;
        net.setHandler(this);
        //super.channelActive(ctx);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Command command) throws Exception {
        switch (command.getType()){
            case AUTH_OK:
                AuthOkCommandData data = (AuthOkCommandData) command.getData();
                System.out.println(data.getUserName());
                viewController.setUsernameLabel(data.getUserName());
                break;
            case AUTH_ERROR:
                viewController.setStatusLabel("Auth ERROR! Try again!");
                break;
        }

    }

    public MainViewController getViewController() {
        return viewController;
    }

    public void setViewController(MainViewController viewController) {
        this.viewController = viewController;
    }

    public void sendMessageToServer(Command command){
        user.writeAndFlush(command);
    }
}
