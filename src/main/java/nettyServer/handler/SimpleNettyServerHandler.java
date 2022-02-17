package nettyServer.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;


/**
 * copyright (C), 2022, Altaria Studio
 *
 * @author Rich
 * @version 1.0.0
 * <author>                <time>                  <version>                   <description>
 * Rich                  2022/2/16 10:16
 * @program nettyDemo
 * @description
 * @create 2022/2/16 10:16
 */

public class SimpleNettyServerHandler extends ChannelInboundHandlerAdapter {

    private NioEventLoopGroup eventLoopGroup;
    private ServerHandlerContext context;

    public SimpleNettyServerHandler(NioEventLoopGroup group, ServerHandlerContext context) {
        this.eventLoopGroup=group;
        this.context = context;
    }

    public SimpleNettyServerHandler(NioEventLoopGroup eventLoopGroup) {
        this.eventLoopGroup = eventLoopGroup;
        this.context=new DefaultServerHandlerContext();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        SocketChannel channel = (SocketChannel) msg;
        eventLoopGroup.register(channel);
        channel.pipeline().addLast(context);

    }
}