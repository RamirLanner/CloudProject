package server;

import ServerUtil.auth.MSSqlAuthService;
import UserData.User;
import commands.Command;
import commands.CommandType;
import commands.assembly.AuthCommandData;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.stream.Collectors;

public class PacketHandler extends SimpleChannelInboundHandler<Command> {//
    private static final ConcurrentLinkedDeque<ChannelHandlerContext> clients = new ConcurrentLinkedDeque<>();
    private static int cnt = 0;
    private String name;
    private int clientID;
    private boolean authStatus;
    ChannelHandlerContext userCTX;


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        clients.add(ctx);
        cnt++;
        userCTX = ctx;

        name = "Client " + cnt;
        System.out.println("Клиентов " + cnt);
        Path serverDir = Path.of("_ServerDir");
        //Files.createDirectory(Path.of("TEstDir"));
        System.out.println("Catalog is exist = " + Files.exists(serverDir));
        String collect = Files.list(serverDir)
                .map(path -> path.getFileName().toString())
                .collect(Collectors.joining(System.lineSeparator()));
        System.out.println(collect);
        //ctx.writeAndFlush(collect);
        //super.channelActive(ctx);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Command com) throws Exception {
        if (!authStatus) {
            if (com.getType() == CommandType.AUTH) {
                if (com.getType() == CommandType.AUTH) {
                    AuthCommandData data = (AuthCommandData) com.getData();
                    User user = MSSqlAuthService.getInstance()
                            .getUserByLoginAndPassword(data.getLogin(), data.getPassword());
                    if (user != null) {
                        userCTX.writeAndFlush(Command.authOkCommand(user.getUsername()));
                        authStatus = true;
                    }
                    userCTX.writeAndFlush(Command.authErrorCommand());
                }
            } else {
                return;
            }
        } else {
            switch (com.getType()) {
                case CREATE_DIR:

                    break;
                case AUTH_ERROR:

                    break;
                case UPLOAD_FILE:

                    break;
                case DOWNLOAD_FILE:

                    break;
                case STATUS_COMMAND:

                    break;
                case UPDATE_CATALOG_TREE:

                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + com.getType());
            }

        }
    }


//    @Override
//    public void channelRead(ChannelHandlerContext ctx, Object o) throws Exception {
//        ByteBuf buf = (ByteBuf) o;
//        StringBuilder s = new StringBuilder();
//        while (buf.isReadable()) {
//            s.append((char) buf.readByte());
//        }
//        String message = s.toString();
//        System.out.println(message);
//
//        ctx.fireChannelRead(message);
//        System.out.println((o.toString()));
//        //channelHandlerContext.writeAndFlush("1");
//        System.out.println("hhh");
//    }

//    @Override
//    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
//        System.out.println("Read complete");
//    }

        @Override
        public void channelInactive (ChannelHandlerContext ctx) throws Exception {
            cnt--;
            System.out.println("Клиентов " + cnt);
            clients.remove(ctx);
            //super.channelInactive(ctx);
        }
    }
