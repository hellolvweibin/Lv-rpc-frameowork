package com.lv.rpc.handler;

import com.lv.entity.RpcRequest;
import com.lv.rpc.serializer.CommonSerializer;
import com.lv.rpc.transport.socket.util.ObjectReader;
import com.lv.rpc.transport.socket.util.ObjectWriter;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.net.Socket;

/**
 * @Project ：Lv-rpc-framework
 * @Author ：Levi_Bee
 * @Date ：2023/7/18 22:24
 * @description ：IO传输模式|处理客户端RpcRequest的工作线程
 */
@Slf4j
public class RequestHandlerThread implements Runnable{
    private Socket socket;
    private RequestHandler requestHandler;
    private CommonSerializer serializer;

    public RequestHandlerThread(Socket socket, RequestHandler requestHandler,CommonSerializer serializer) {
        this.socket = socket;
        this.requestHandler = requestHandler;
        this.serializer = serializer;
    }

    @Override
    public void run() {

        try(InputStream inputStream = socket.getInputStream();
            OutputStream outputStream = socket.getOutputStream()) {
            RpcRequest rpcRequest = (RpcRequest) ObjectReader.readObject(inputStream);
            Object response = requestHandler.handle(rpcRequest);
            ObjectWriter.writeObject(outputStream, response, serializer);

        } catch (IOException e) {
            log.info("调用或发送时发生错误：" + e);
        }
    }
}
