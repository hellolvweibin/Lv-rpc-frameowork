package com.lv.test;

import com.lv.rpc.annotation.ServiceScan;
import com.lv.rpc.api.ByeService;
import com.lv.rpc.api.HelloObject;
import com.lv.rpc.api.HelloService;
import com.lv.rpc.loadbalancer.RoundRobinLoadBalancer;
import com.lv.rpc.serializer.CommonSerializer;
import com.lv.rpc.transport.RpcClientProxy;
import com.lv.rpc.transport.socket.client.SocketClient;

/**
 * @Project ：Lv-rpc-framework
 * @Author ：Levi_Bee
 * @Date ：2023/7/20 21:21
 * @description ：Socket测试用客户端
 */
@ServiceScan
public class SocketTestClient {

    public static void main(String[] args) {
        SocketClient client = new SocketClient(CommonSerializer.KRYO_SERIALIZER, new RoundRobinLoadBalancer());
        //接口与代理对象之间的中介对象
        RpcClientProxy proxy = new RpcClientProxy(client);
        //创建代理对象
        HelloService helloService = proxy.getProxy(HelloService.class);
        //接口方法的参数对象
        HelloObject object = new HelloObject(12, "This is test message");
        //由动态代理可知，代理对象调用hello()实际会执行invoke()
        String res = helloService.hello(object);
        System.out.println(res);
        ByeService byeService = proxy.getProxy(ByeService.class);
        System.out.println(byeService.bye("Netty"));
    }
}
