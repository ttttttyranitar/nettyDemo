package nettyServer.service;



/**
 * copyright (C), 2022, Altaria Studio
 *
 * @author Rich
 * @version 1.0.0
 * <author>                <time>                  <version>                   <description>
 * Rich                  2022/3/1 11:11
 * @program nettyDemo
 * @description
 * @create 2022/3/1 11:11
 */

public class SimpleRpcService implements EntityService {

    @Override
    public String getMetadata() {
        return "hello netty rpc ,it's a meta";
    }
}