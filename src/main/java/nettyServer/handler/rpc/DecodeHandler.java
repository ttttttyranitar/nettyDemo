package nettyServer.handler.rpc;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import nettyRpcClient.protocol.Content;
import nettyRpcClient.protocol.Header;
import nettyRpcClient.protocol.Package;
import nettyRpcClient.protocol.ProtocolConfig;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.util.List;

/**
 * copyright (C), 2022, Altaria Studio
 *
 * @author Rich
 * @version 1.0.0
 * <author>                <time>                  <version>                   <description>
 * Rich                  2022/2/28 10:46
 * @program nettyDemo
 * @description handler  implementing decode method to deserialized io package in specific protocol
 * @create 2022/2/28 10:46
 */

public class DecodeHandler extends ByteToMessageDecoder {


    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf byteBuf, List<Object> list) throws Exception {
        ByteArrayInputStream byteArrayInputStream =null;
        while (byteBuf.readableBytes()>= ProtocolConfig.DEFAULT_HEADER_SIZE.getValue()){

            byte[] headerBytes = new byte[ProtocolConfig.DEFAULT_HEADER_SIZE.getValue()];
            byteBuf.getBytes(byteBuf.readerIndex(),headerBytes);
            byteArrayInputStream=new ByteArrayInputStream(headerBytes);
            ObjectInputStream headerParser = new ObjectInputStream(byteArrayInputStream);
            Header header = (Header)headerParser.readObject();

            if(byteBuf.readableBytes()>=header.getDataLen()){
                byteBuf.readBytes(ProtocolConfig.DEFAULT_HEADER_SIZE.getValue());
                byte[] body=new byte[(int)header.getDataLen()];
                byteBuf.readBytes(body);
                byteArrayInputStream.reset();
                byteArrayInputStream.read(body);
                ObjectInputStream contentParser = new ObjectInputStream((byteArrayInputStream));
                Content content = (Content)contentParser.readObject();

                Package responsePackage = new Package(header, content);
                list.add(responsePackage);
            }else{
                //quit while loop, ByteToMessageDecoder would  cumulate incomplete  ByteBufs by add them to a CompositeByteBuf
                break;
            }



        }

    }
}