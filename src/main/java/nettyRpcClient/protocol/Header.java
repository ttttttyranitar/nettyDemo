package nettyRpcClient.protocol;

import java.io.Serializable;
import java.util.UUID;

/**
 * copyright (C), 2022, Altaria Studio
 *
 * @author Rich
 * @version 1.0.0
 * <author>                <time>                  <version>                   <description>
 * Rich                  2022/2/28 11:23
 * @program nettyDemo
 * @description customized rpc request header
 * @create 2022/2/28 11:23
 */

public class Header implements Serializable {

    int flag;
    long requestID;
    long dataLen;


    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public long getRequestID() {
        return requestID;
    }

    public void setRequestID(long requestID) {
        this.requestID = requestID;
    }

    public long getDataLen() {
        return dataLen;
    }

    public void setDataLen(long dataLen) {
        this.dataLen = dataLen;
    }

    public static Header createHeader(byte[] msg){
         Header header = new Header();
        int size = msg.length;
        int f = 0x14141414;
        long requestID =  Math.abs(UUID.randomUUID().getLeastSignificantBits());
        header.setFlag(f);
        header.setDataLen(size);
        header.setRequestID(requestID);
        return header;
    }

}