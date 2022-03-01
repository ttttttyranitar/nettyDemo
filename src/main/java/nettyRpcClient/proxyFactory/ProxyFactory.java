package nettyRpcClient.proxyFactory;

import java.lang.reflect.Proxy;

/**
 * copyright (C), 2022, Altaria Studio
 * @program nettyDemo
 * @description 
 * @author  Rich 
 * @create 2022/2/18 9:28
 * @version 1.0.0
 *  <author>                <time>                  <version>                   <description>
 *  Rich                  2022/2/18 9:28                                      
 */
    
    public class ProxyFactory {



        public static <T> T getProxy(Class<T> proxyClass) {
            ClassLoader classLoader = proxyClass.getClassLoader();
            Class<?>[] classInfo={proxyClass};
            return (T)Proxy.newProxyInstance(classLoader,classInfo,new ServiceInvocationHandler(proxyClass));
        }
}