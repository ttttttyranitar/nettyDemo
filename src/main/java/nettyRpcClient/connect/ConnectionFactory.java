package nettyRpcClient.connect;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import nettyRpcClient.handler.ClientHandler;
import nettyRpcClient.handler.DecodeHandler;

import java.net.InetSocketAddress;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

/**
 * copyright (C), 2022, Altaria Studio
 *
 * @author Rich
 * @version 1.0.0
 * <author>                <time>                  <version>                   <description>
 * Rich                  2022/2/18 9:27
 * @program nettyDemo
 * @description
 * @create 2022/2/18 9:27
 */

public class ConnectionFactory {

    private static final ConnectionFactory connectionFactory;
    private static final int DEFAULT_POOL_SIZE =16;
    private static Random random=new Random();

    static {
        connectionFactory=new ConnectionFactory();
    }

    public static ConnectionFactory getConnectionFactory() {
        return connectionFactory;
    }

    protected static ConcurrentHashMap<String,ConnectPool> map=new ConcurrentHashMap<>();




    public static NioSocketChannel getClient(InetSocketAddress socket){
        String s = socket.toString();

        ConnectPool connectPool = map.get(s);
        if (connectPool==null){

            synchronized (map){
                if (connectPool==null){
                    map.putIfAbsent(s,new ConnectPool(DEFAULT_POOL_SIZE));
                    connectPool=map.get(s);
                }
            }
        }

        int clientIndex = getClientIndex();
        if(connectPool.getClient(clientIndex)!=null && connectPool.getClient(clientIndex).isActive()){
            return connectPool.getClient(clientIndex);
        }
        else{
            synchronized (connectPool.getLock(clientIndex)){
                NioSocketChannel client = createClient(socket);
                connectPool.setClient(client,clientIndex);
                return client;
            }
        }
    }

    private static NioSocketChannel createClient(InetSocketAddress socket) {
        NioEventLoopGroup group = new NioEventLoopGroup(1);
        Bootstrap bs=new Bootstrap();
        ChannelFuture bind = bs.group(group)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel nioSocketChannel) throws Exception {
                        //TODO:添加客户端解码以及响应处理handler
                        ChannelPipeline pipeline = nioSocketChannel.pipeline();
                        pipeline.addLast(new DecodeHandler())
                                .addLast(new ClientHandler());

                    }
                })
                .connect(socket);
        try {
            NioSocketChannel channel = (NioSocketChannel)bind.sync().channel();
            return channel;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;

    }

    private static int getClientIndex(){
        return random.nextInt(DEFAULT_POOL_SIZE);
    }





}