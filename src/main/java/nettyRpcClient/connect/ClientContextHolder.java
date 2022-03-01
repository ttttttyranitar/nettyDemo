package nettyRpcClient.connect;

import nettyRpcClient.protocol.Package;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

/**
 * copyright (C), 2022, Altaria Studio
 *
 * @author Rich
 * @version 1.0.0
 * <author>                <time>                  <version>                   <description>
 * Rich                  2022/2/28 13:59
 * @program nettyDemo
 * @description
 * @create 2022/2/28 13:59
 */

public class ClientContextHolder {
    private static ConcurrentHashMap<Long, CompletableFuture<Object>>map=new ConcurrentHashMap<>();


    public  static void addRpcCall(Package requestPackage,CompletableFuture<Object> resultFuture) {
        map.putIfAbsent(requestPackage.getHeader().getRequestID(),resultFuture);
    }

    public static void finishRpcCall(Package responsePackage){
        //define a request id for every rpc call ,it was encapsulated in header of protocol
        CompletableFuture<Object> completableFuture = map.remove(responsePackage.getHeader().getRequestID());
        completableFuture.complete(responsePackage.getContent().getResult());
    }

}