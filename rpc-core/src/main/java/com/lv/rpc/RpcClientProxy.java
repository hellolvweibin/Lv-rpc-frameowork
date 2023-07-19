package com.lv.rpc;

import com.lv.entity.RpcRequest;
import com.lv.entity.RpcResponse;
import com.lv.rpc.RpcClient;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @Project ：Lv-rpc-framework
 * @Author ：Levi_Bee
 * @Date ：2023/7/18 16:44
 * @description ：Rpc客户端动态代理
 */
@Slf4j
public class RpcClientProxy implements InvocationHandler {

    private final RpcClient client;


    /**
     * 指明服务端的位置
     */
    public RpcClientProxy(RpcClient client) {
        this.client = client;
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
        log.info("调用方法：{}#{}", method.getDeclaringClass().getName(), method.getName());
        RpcRequest rpcRequest = new RpcRequest(method.getDeclaringClass().getName(),
                method.getName(), args, method.getParameterTypes());
        return client.sendRequest(rpcRequest);

    }
}
