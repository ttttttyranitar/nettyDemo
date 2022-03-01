
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import nettyClient.handlerAdapter.SimpleEchoChannelHandler;
import org.junit.Test;

/**
 * copyright (C), 2022, Altaria Studio
 *
 * @author Rich
 * @version 1.0.0
 * <author>                <time>                  <version>                   <description>
 * Rich                  2022/2/17 9:13
 * @program nettyDemo
 * @description
 * @create 2022/2/17 9:13
 */

public class SimpleNettyServerWithBootstrap {

    @Test
    public void testSimpleNettyServer() throws InterruptedException {
        NioEventLoopGroup eventExecutors = new NioEventLoopGroup(8);
        ServerBootstrap bootstrap = new ServerBootstrap();
        ChannelFuture channelFuture = bootstrap
                .group(eventExecutors,eventExecutors)
                .channel(NioServerSocketChannel.class)
                //加载从serverSocketChannel中获取的socketChannel的处理逻辑。
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel nioServerSocketChannel) throws Exception {
                        nioServerSocketChannel.pipeline().addLast(new SimpleEchoChannelHandler());
                    }
                })
                .bind(9090);

        channelFuture.sync();
        channelFuture.channel().closeFuture().sync();
    }

}