package server;

import commands.Command;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.concurrent.ConcurrentLinkedDeque;

public class PacketHandler extends SimpleChannelInboundHandler<Command>{//
    private static final ConcurrentLinkedDeque<ChannelHandlerContext> clients = new ConcurrentLinkedDeque<>();
    private  static int cnt =0;
    private String name;

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        clients.add(ctx);
        cnt++;
        name = "Client "+ cnt;
        System.out.println("Клиентов "+cnt);
        ctx.writeAndFlush("dfdf");
        //super.channelActive(ctx);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Command com) throws Exception {

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

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        System.out.println("Read compleate");
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        cnt--;
        System.out.println("Клиентов "+cnt);
        clients.remove(ctx);
        //super.channelInactive(ctx);
    }
}
