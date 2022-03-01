package nettyServer.handler;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelPipeline;

/**
 * copyright (C), 2022, Altaria Studio
 *
 * @author Rich
 * @version 1.0.0
 * <author>                <time>                  <version>                   <description>
 * Rich                  2022/2/16 10:14
 * @program nettyDemo
 * @description
 * @create 2022/2/16 10:14
 */
@ChannelHandler.Sharable
public abstract class ServerHandlerContext extends ChannelInboundHandlerAdapter {
    /**
     * @description coder自定义的业务处理逻辑
     * @params
     * @return
     */

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        this.registerHandler(ctx.channel().pipeline());
        //注册结束后，把ServerHandlerContext这个引导注册的handler从pipeline中消除掉。
        ctx.channel().pipeline().remove(this);
    }

    /**
     *
     *
     * @description 注册处理客户端channel 的handler
     * @params ChannelPipeline pipeline
     * @return
     *
     */
    public abstract void registerHandler(ChannelPipeline pipeline);

}