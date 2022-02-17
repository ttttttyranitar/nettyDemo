import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import nettyServer.handler.ServerHandlerContext;
import nettyServer.handler.SimpleNettyServerHandler;
import org.junit.Test;

import java.net.InetSocketAddress;

/**
 * copyright (C), 2022, Altaria Studio
 *
 * @author Rich
 * @version 1.0.0
 * <author>                <time>                  <version>                   <description>
 * Rich                  2022/2/16 10:40
 * @program nettyDemo
 * @description
 * @create 2022/2/16 10:40
 */

public class SimpleNettyServer {
    @Test
    public void testNettyServer() throws Exception {
        NioEventLoopGroup eventExecutors = new NioEventLoopGroup();

        NioServerSocketChannel nioServerSocketChannel = new NioServerSocketChannel();

        //绑定事件处理逻辑
        nioServerSocketChannel.pipeline().addLast(new SimpleNettyServerHandler(eventExecutors, new ServerHandlerContext() {
            @Override
            public void registerHandler(ChannelPipeline pipeline) {
                pipeline.addLast(new ChannelInboundHandlerAdapter());
            }
        }));
        ChannelFuture registerFuture = eventExecutors.register(nioServerSocketChannel);
        registerFuture.sync();

        //异步给serverSocketChannel绑定端口，完成前阻塞
        ChannelFuture bindFuture = nioServerSocketChannel.bind(new InetSocketAddress(9090));
        bindFuture.sync().channel().closeFuture().sync();
    }
}