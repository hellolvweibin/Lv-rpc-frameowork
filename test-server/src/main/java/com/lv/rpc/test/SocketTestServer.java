package com.lv.rpc.test;

import com.lv.rpc.annotation.ServiceScan;
import com.lv.rpc.serializer.CommonSerializer;
import com.lv.rpc.transport.RpcServer;
import com.lv.rpc.transport.socket.server.SocketServer;

/**
 * @Project ：Lv-rpc-framework
 * @Author ：Levi_Bee
 * @Date ：2023/7/20 21:23
 * @description ：Socket 测试服务端
 */
@ServiceScan
public class SocketTestServer {
    public static void main(String[] args) {
        RpcServer server = new SocketServer("127.0.0.1", 9998, CommonSerializer.KRYO_SERIALIZER);
        server.start();
    }
}
