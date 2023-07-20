package com.lv.rpc.transport;

import com.lv.entity.RpcRequest;
import com.lv.rpc.serializer.CommonSerializer;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * @Project ：Lv-rpc-framework
 * @Author ：Levi_Bee
 * @Date ：2023/7/18 16:54
 * @description ：客户端类通用接口
 */

public interface RpcClient {
    Object sendRequest(RpcRequest rpcRequest);


    void setSerializer(CommonSerializer serializer);
}
