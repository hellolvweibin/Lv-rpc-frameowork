package com.lv.rpc.test;

import com.lv.rpc.annotation.ServiceScan;
import com.lv.rpc.serializer.CommonSerializer;
import com.lv.rpc.transport.RpcServer;
import com.lv.rpc.transport.netty.server.NettyServer;

/**
 * @Project ：Lv-rpc-framework
 * @Author ：Levi_Bee
 * @Date ：2023/7/20 21:21
 * @description ：Netty测试服务端
 */
@ServiceScan
public class NettyTestServer {
    public static void main(String[] args) {
        RpcServer server = new NettyServer("127.0.0.1", 9999, CommonSerializer.KRYO_SERIALIZER);
        server.start();
    }
}
