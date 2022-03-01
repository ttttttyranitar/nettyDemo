package nettyRpcClient.transport;

import nettyRpcClient.protocol.Content;
import nettyRpcClient.protocol.ProtocolConfig;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

/**
 * copyright (C), 2022, Altaria Studio
 *
 * @author Rich
 * @version 1.0.0
 * <author>                <time>                  <version>                   <description>
 * Rich                  2022/3/1 10:06
 * @program nettyDemo
 * @description
 * @create 2022/3/1 10:06
 */

public class ProtocolTransport {


    public static  byte[] getHeaderInBytes(Object o){
        ByteArrayOutputStream bytesParser=new ByteArrayOutputStream();
        bytesParser.reset();
        ObjectOutputStream ObjectOutputStream = null;
        byte[] bytes = null;

        try {
            ObjectOutputStream=new ObjectOutputStream(bytesParser);
            ObjectOutputStream.writeObject(o);
            bytes=bytesParser.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return bytes;


    }

    public static  byte[] getInBytes(Object o){
        ByteArrayOutputStream bytesParser=new ByteArrayOutputStream();
        bytesParser.reset();
        ObjectOutputStream ObjectOutputStream = null;
        byte[] bytes = new byte[ProtocolConfig.DEFAULT_HEADER_SIZE.getValue()];

        try {
            ObjectOutputStream=new ObjectOutputStream(bytesParser);
            ObjectOutputStream.writeObject(o);
            byte[] header = bytesParser.toByteArray();
            System.arraycopy(header,0,bytes,0,header.length);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return bytes;


    }
}