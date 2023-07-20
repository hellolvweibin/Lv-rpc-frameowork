package com.lv.test;

import com.lv.rpc.annotation.ServiceScan;
import com.lv.rpc.api.ByeService;
import com.lv.rpc.api.HelloObject;
import com.lv.rpc.api.HelloService;
import com.lv.rpc.serializer.CommonSerializer;
import com.lv.rpc.transport.RpcClient;
import com.lv.rpc.transport.RpcClientProxy;
import com.lv.rpc.transport.netty.client.NettyClient;

/**
 * @Project ：Lv-rpc-framework
 * @Author ：Levi_Bee
 * @Date ：2023/7/20 21:20
 * @description ：测试用Netty客户端
 */
@ServiceScan
public class NettyTestClient {
    public static void main(String[] args) {
        RpcClient client = new NettyClient(CommonSerializer.KRYO_SERIALIZER);
        RpcClientProxy rpcClientProxy = new RpcClientProxy(client);
        HelloService helloService = rpcClientProxy.getProxy(HelloService.class);
        HelloObject object = new HelloObject(12, "this is netty style");
        String res = helloService.hello(object);
        System.out.println(res);
        ByeService byeService = rpcClientProxy.getProxy(ByeService.class);
        System.out.println(byeService.bye("Netty"));
    }
}
