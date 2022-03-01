import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import nettyRpcClient.handler.DecodeHandler;
import nettyRpcClient.proxyFactory.ProxyFactory;
import nettyServer.dispather.ServiceInstanceDispatcher;
import nettyServer.handler.rpc.RpcServerHandler;
import nettyServer.service.EntityService;
import nettyServer.service.SimpleRpcService;
import org.junit.Test;

import java.net.InetSocketAddress;


/**
 * copyright (C), 2022, Altaria Studio
 *
 * @author Rich
 * @version 1.0.0
 * <author>                <time>                  <version>                   <description>
 * Rich                  2022/3/1 11:02
 * @program nettyDemo
 * @description
 * @create 2022/3/1 11:02
 */

public class NettyRpcServer {

    @Test
    public void testNettyServer() throws InterruptedException {
        EntityService service = new SimpleRpcService();
        ServiceInstanceDispatcher dispatcher = ServiceInstanceDispatcher.getDispatcher();
        dispatcher.registerService(service.getClass().getInterfaces()[0].getSimpleName(),service);

        NioEventLoopGroup eventExecutors = new NioEventLoopGroup(8);
        ServerBootstrap bs = new ServerBootstrap();
        ChannelFuture channelFuture = bs.group(eventExecutors, eventExecutors)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel channel) throws Exception {
                        ChannelPipeline pipeline = channel.pipeline();
                        pipeline.addLast(new DecodeHandler());
                        pipeline.addLast(new RpcServerHandler());
                    }
                }).bind(new InetSocketAddress("localhost",9090));
        channelFuture.sync();
        channelFuture.channel().closeFuture().sync();
    }

    @Test
    public void rpcClient(){
        EntityService service = ProxyFactory.getProxy(EntityService.class);
        String result = service.getMetadata();
        System.out.println(result);

    }
}