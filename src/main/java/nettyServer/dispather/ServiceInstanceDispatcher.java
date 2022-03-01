package nettyServer.dispather;

import java.util.concurrent.ConcurrentHashMap;

/**
 * copyright (C), 2022, Altaria Studio
 *
 * @author Rich
 * @version 1.0.0
 * <author>                <time>                  <version>                   <description>
 * Rich                  2022/3/1 9:20
 * @program nettyDemo
 * @description
 * @create 2022/3/1 9:20
 */

public class ServiceInstanceDispatcher {

    private static ServiceInstanceDispatcher dispatcher ;
    static {
        dispatcher = new ServiceInstanceDispatcher();
    }

    public static ServiceInstanceDispatcher getDispatcher(){
        return dispatcher;
    }

    private ConcurrentHashMap<String,Object> map=new ConcurrentHashMap<String,Object>();

    public void registerService(String name,Object o){
        map.putIfAbsent(name,o);
    }

    public Object getService(String name){
        return map.get(name);
    }



}