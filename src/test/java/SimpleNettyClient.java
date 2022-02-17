import io.netty.buffer.ByteBuf;
import io.netty.buffer.UnpooledByteBufAllocator;
import io.netty.channel.ChannelFuture;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import nettyClient.handlerAdapter.SimpleEchoChannelHandler;
import org.junit.Test;

import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;

/**
 * copyright (C), 2022, Altaria Studio
 *
 * @author Rich
 * @version 1.0.0
 * <author>                <time>                  <version>                   <description>
 * Rich                  2022/2/15 9:14
 * @program nettyDemo
 * @description
 * @create 2022/2/15 9:14
 */

public class SimpleNettyClient {

    @Test
    public void testSimpleNettyClient() throws InterruptedException {
        NioEventLoopGroup eventExecutors = new NioEventLoopGroup();

        NioSocketChannel client = new NioSocketChannel();
        eventExecutors.register(client);
        client.pipeline().addLast(new SimpleEchoChannelHandler());

        ChannelFuture connectFuture = client.connect(new InetSocketAddress("192.168.111.130", 9090));
        connectFuture.sync();
        ByteBuf buffer = UnpooledByteBufAllocator.DEFAULT.buffer(16, 32);
        ChannelFuture writeAndFlush = client.writeAndFlush(buffer.writeBytes("hello netty".getBytes(StandardCharsets.UTF_8)));
        writeAndFlush.sync();

        client.closeFuture().sync();
    }





}