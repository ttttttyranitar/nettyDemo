package nettyRpcClient.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import nettyRpcClient.connect.ClientContextHolder;
import nettyRpcClient.protocol.Package;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisSentinelPool;

import java.util.HashSet;
import java.util.Set;

/**
 * copyright (C), 2022, Altaria Studio
 *
 * @author Rich
 * @version 1.0.0
 * <author>                <time>                  <version>                   <description>
 * Rich                  2022/2/28 13:45
 * @program nettyDemo
 * @description obtain and handle response package  as a downstream handler of DecodeHandler
 * @create 2022/2/28 13:45
 */

public class ClientHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        // message from the collection built by DecodeHandler
        Package responsePackage = (Package) msg;

        //to awake blocking client thread
        ClientContextHolder.finishRpcCall(responsePackage);


        //it's an attempt to use jedis as a java client to redis server.
//        Set<String> sentinelAddrs=new HashSet<>();
//        sentinelAddrs.add("127.0.0.1:16379");
//        JedisSentinelPool jedisSentinelPool = new JedisSentinelPool("master",sentinelAddrs);
//        Jedis jedis = jedisSentinelPool.getResource();


    }
}