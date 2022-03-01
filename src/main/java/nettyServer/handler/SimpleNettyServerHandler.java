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
        //这里需要serverSocketChannel接收的的socketChannel注册到eventLoopGroup中，并且埋下处理serverSocketChannel的handler
        //这样也就有能力实现一个eventLoopGroup专门从serverSocketChannel中接收socketChannel，另外一个group真正处理socketChannel。
        SocketChannel channel = (SocketChannel) msg;
        channel.pipeline().addLast(context);
        eventLoopGroup.register(channel);


    }
}