package nettyServer.handler;

import io.netty.channel.ChannelPipeline;
import nettyClient.handlerAdapter.SimpleEchoChannelHandler;

/**
 * copyright (C), 2022, Altaria Studio
 *
 * @author Rich
 * @version 1.0.0
 * <author>                <time>                  <version>                   <description>
 * Rich                  2022/2/16 10:44
 * @program nettyDemo
 * @description
 * @create 2022/2/16 10:44
 */

public class DefaultServerHandlerContext extends ServerHandlerContext{



    @Override
    public void registerHandler(ChannelPipeline pipeline){
        pipeline.addLast(new SimpleEchoChannelHandler());
    }
}