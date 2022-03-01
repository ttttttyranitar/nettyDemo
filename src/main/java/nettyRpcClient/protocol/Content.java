package nettyRpcClient.protocol;

import java.io.Serializable;

/**
 * copyright (C), 2022, Altaria Studio
 *
 * @author Rich
 * @version 1.0.0
 * <author>                <time>                  <version>                   <description>
 * Rich                  2022/2/28 11:27
 * @program nettyDemo
 * @description customized rpc request body
 * @create 2022/2/28 11:27
 */

public class Content implements Serializable {

    private String serviceName;
    private String methodName;
    private Class<?>[] parametersTypes;
    private Object[] args;
    private Object result;


    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Class<?>[] getParametersTypes() {
        return parametersTypes;
    }

    public void setParametersTypes(Class<?>[] parametersTypes) {
        this.parametersTypes = parametersTypes;
    }

    public Object[] getArgs() {
        return args;
    }

    public void setArgs(Object[] args) {
        this.args = args;
    }
}