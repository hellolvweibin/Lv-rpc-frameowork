package com.lv.rpc.transport;

import com.lv.entity.RpcRequest;
import com.lv.entity.RpcResponse;
import io.protostuff.Rpc;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @Project ：Lv-rpc-framework
 * @Author ：Levi_Bee
 * @Date ：2023/7/18 16:44
 * @description ：Rpc客户端动态代理
 */
public class RpcClientProxy implements InvocationHandler {
    private String host;
    private int port;


    /**
     * 指明服务端的位置
     */
    public RpcClientProxy(String host, int port) {
        this.host = host;
        this.port = port;
    }
    /**
     *抑制编译器产生警告信息
     */
    @SuppressWarnings("unchecked")
    public <T> T getProxy(Class<T> clazz){
        //创建代理对象
        return (T) Proxy.newProxyInstance(clazz.getClassLoader(),new Class<?>[]{clazz},this);

    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        //客户端向服务端传输的对象
        RpcRequest request = RpcRequest.builder().interfaceName(method.getDeclaringClass().getName())
                .methodName(method.getName())
                .parameters(args)
                .paramTypes(method.getParameterTypes())
                .build();
        //进行远程调用的客户端
        RpcClient rpcClient = new RpcClient();
        return ((RpcResponse) rpcClient.sendRequest(request,host,port)).getData();


    }
}
