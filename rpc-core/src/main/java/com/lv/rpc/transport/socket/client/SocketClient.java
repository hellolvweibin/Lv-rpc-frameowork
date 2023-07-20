package com.lv.rpc.transport.socket.client;

import com.lv.entity.RpcRequest;
import com.lv.entity.RpcResponse;
import com.lv.enumeration.RpcError;
import com.lv.exception.RpcException;
import com.lv.rpc.transport.RpcClient;
import com.lv.rpc.register.NacosServiceRegistry;
import com.lv.rpc.register.ServiceRegistry;
import com.lv.rpc.serializer.CommonSerializer;
import com.lv.rpc.transport.socket.util.ObjectReader;
import com.lv.rpc.transport.socket.util.ObjectWriter;
import com.lv.util.RpcMessageChecker;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * @Project ：Lv-rpc-framework
 * @Author ：Levi_Bee
 * @Date ：2023/7/19 14:22
 * @description ：Socket方式进行远程调用的客户端
 */
@Slf4j
public class SocketClient implements RpcClient {
    private final ServiceRegistry serviceRegistry;
    private CommonSerializer serializer;


    public SocketClient() {
        serviceRegistry = new NacosServiceRegistry();

    }
    @Override
    public Object sendRequest(RpcRequest rpcRequest) {
        if (serializer == null) {
            log.error("未设置序列化器");
            throw new RpcException(RpcError.SERIALIZER_NOT_FOUND);
        }
        //从Nacos获取提供对应服务的服务端地址
        InetSocketAddress inetSocketAddress = serviceRegistry.lookupService(rpcRequest.getInterfaceName());
        /**
         * socket套接字实现TCP网络传输
         * try()中一般放对资源的申请，若{}出现异常，()资源会自动关闭
         */
        try (Socket socket = new Socket()) {
            socket.connect(inetSocketAddress);
            OutputStream outputStream = socket.getOutputStream();
            InputStream inputStream = socket.getInputStream();
            ObjectWriter.writeObject(outputStream, rpcRequest, serializer);
            Object obj = ObjectReader.readObject(inputStream);
            RpcResponse rpcResponse = (RpcResponse) obj;
            RpcMessageChecker.check(rpcRequest, rpcResponse);
            return rpcResponse.getData();
        } catch (IOException e) {
            log.error("调用时有错误发生：" + e);
            throw new RpcException("服务调用失败：", e);
        }

    }
    @Override
    public void setSerializer(CommonSerializer serializer) {
        this.serializer = serializer;
    }
}
