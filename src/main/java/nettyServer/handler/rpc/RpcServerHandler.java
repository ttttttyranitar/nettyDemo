package nettyServer.handler.rpc;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import nettyRpcClient.protocol.Content;
import nettyRpcClient.protocol.Header;
import nettyRpcClient.protocol.Package;
import nettyRpcClient.transport.ProtocolTransport;
import nettyServer.dispather.ServiceInstanceDispatcher;
import nettyServer.service.SimpleRpcService;

import java.lang.reflect.Method;

/**
 * copyright (C), 2022, Altaria Studio
 *
 * @author Rich
 * @version 1.0.0
 * <author>                <time>                  <version>                   <description>
 * Rich                  2022/3/1 11:07
 * @program nettyDemo
 * @description
 * @create 2022/3/1 11:07
 */

public class RpcServerHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        Package clientPackage = (Package) msg;
        Content content = clientPackage.getContent();

        ServiceInstanceDispatcher dispatcher = ServiceInstanceDispatcher.getDispatcher();
        SimpleRpcService service =(SimpleRpcService) dispatcher.getService(content.getServiceName());
        Method method = service.getClass().getMethod(content.getMethodName(), content.getParametersTypes());
        Object result = method.invoke(service, content.getArgs());

        content.setResult(result);

        //we should compose a  new response header.
        byte[] bodyInBytes = ProtocolTransport.getHeaderInBytes(content);
        Header header = Header.createHeader(bodyInBytes);
        //protocol header should maintain requestId
        header.setRequestID(clientPackage.getHeader().getRequestID());
        byte[] headerInBytes = ProtocolTransport.getInBytes(header);

        ByteBuf byteBuf = PooledByteBufAllocator.DEFAULT.directBuffer(headerInBytes.length+bodyInBytes.length);
        byteBuf.writeBytes(headerInBytes);
        byteBuf.writeBytes(bodyInBytes);

        //send result to client
        ctx.writeAndFlush(byteBuf);
    }
}