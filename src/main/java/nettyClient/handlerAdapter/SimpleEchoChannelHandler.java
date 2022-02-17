package nettyClient.handlerAdapter;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.nio.charset.StandardCharsets;

/**
 * copyright (C), 2022, Altaria Studio
 *
 * @author Rich
 * @version 1.0.0
 * <author>                <time>                  <version>                   <description>
 * Rich                  2022/2/15 9:36
 * @program nettyDemo
 * @description netty客户端事件响应逻辑
 * @create 2022/2/15 9:36
 */

public class SimpleEchoChannelHandler extends ChannelInboundHandlerAdapter {

    
    /**
     *
     *
     * @description 一个简单的echo逻辑
     * @params 
     * @return 
     * @author Rich
     * @date 10:14 2022/2/16
     * 
     **/
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf buf = (ByteBuf) msg;
        String str = buf.getCharSequence(0, buf.readableBytes(), StandardCharsets.UTF_8).toString();
        buf.clear();
        buf.writeBytes(str.getBytes(StandardCharsets.UTF_8));
        ctx.channel().writeAndFlush(buf);
    }
}