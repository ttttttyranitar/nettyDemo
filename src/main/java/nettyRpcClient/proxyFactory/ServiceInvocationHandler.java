package nettyRpcClient.proxyFactory;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoop;
import io.netty.channel.socket.nio.NioSocketChannel;
import nettyRpcClient.connect.ClientContextHolder;
import nettyRpcClient.connect.ConnectionFactory;
import nettyRpcClient.protocol.Content;
import nettyRpcClient.protocol.Header;
import nettyRpcClient.protocol.Package;
import nettyRpcClient.transport.ProtocolTransport;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.net.InetSocketAddress;
import java.util.concurrent.CompletableFuture;

/**
 * copyright (C), 2022, Altaria Studio
 *
 * @author Rich
 * @version 1.0.0
 * <author>                <time>                  <version>                   <description>
 * Rich                  2022/2/18 9:25
 * @program nettyDemo
 * @description
 * @create 2022/2/18 9:25
 */

public class ServiceInvocationHandler implements InvocationHandler {

    private Class<?> clazz;

    public ServiceInvocationHandler(Class<?> clazz) {
        this.clazz = clazz;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        Class<?>[] parameterTypes = method.getParameterTypes();
        String methodName = method.getName();
        String serviceName = clazz.getSimpleName();

        CompletableFuture<Object> resultCompletableFuture = new CompletableFuture<>();


        NioSocketChannel client = ConnectionFactory.getClient(new InetSocketAddress("localhost",9090));
        EventLoop executors = client.eventLoop().parent().next();
        executors.execute(()->{
            Content content = new Content();
            content.setServiceName(serviceName);
            content.setMethodName(methodName);
            content.setArgs(args);

            byte[] contentInBytes = ProtocolTransport.getHeaderInBytes(content);
            Header header = Header.createHeader(contentInBytes);
            byte[] headerInBytes = ProtocolTransport.getInBytes(header);


            //encapsulation request header and body in byteBuf,send it to channel synchronously.
            ByteBuf byteBuf = PooledByteBufAllocator.DEFAULT.directBuffer(headerInBytes.length + contentInBytes.length);
            byteBuf.writeBytes(headerInBytes);
            byteBuf.writeBytes(contentInBytes);
            ChannelFuture channelFuture = client.writeAndFlush(byteBuf);
            try {
                channelFuture.sync();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            ClientContextHolder.addRpcCall(new Package(header,content),resultCompletableFuture);

        });


        return resultCompletableFuture.get();
    }
}