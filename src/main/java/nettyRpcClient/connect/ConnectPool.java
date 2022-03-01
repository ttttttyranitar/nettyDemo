package nettyRpcClient.connect;

import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * copyright (C), 2022, Altaria Studio
 *
 * @author Rich
 * @version 1.0.0
 * <author>                <time>                  <version>                   <description>
 * Rich                  2022/2/18 9:28
 * @program nettyDemo
 * @description
 * @create 2022/2/18 9:28
 */

public class ConnectPool {
    protected NioSocketChannel[] clients;
    protected Object[]locks;

    public ConnectPool(int size) {
        clients=new NioSocketChannel[size];
        locks=new Object[size];
        for (int i = 0; i < size; i++){
            locks[i] = new Object();
        }
    }

    public NioSocketChannel[] getClients() {
        return clients;
    }


    public NioSocketChannel getClient(int idx){
        return clients[idx];
    }

    public void setClient(NioSocketChannel channel,int index){
        clients[index]=channel;
    }


    public Object[] getLocks() {
        return locks;
    }

    public  Object getLock(int index){
        return locks[index];
    }


}