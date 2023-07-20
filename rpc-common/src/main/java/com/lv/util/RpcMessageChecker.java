package com.lv.util;

import com.lv.entity.RpcRequest;
import com.lv.entity.RpcResponse;
import com.lv.enumeration.ResponseCode;
import com.lv.enumeration.RpcError;
import com.lv.exception.RpcException;
import lombok.extern.slf4j.Slf4j;

/**
 * @Project ：Lv-rpc-framework
 * @Author ：Levi_Bee
 * @Date ：2023/7/20 13:11
 * @description ：检查响应和请求
 */
@Slf4j
public class RpcMessageChecker {
    private static final String INTERFACE_NAME = "interfaceName";

    public RpcMessageChecker() {
    }
    public static void check(RpcRequest rpcRequest, RpcResponse rpcResponse){
        if(rpcResponse == null){
            log.error("调用服务失败，serviceName:{}", rpcRequest.getInterfaceName());
            throw new RpcException(RpcError.SERVICE_INVOCATION_FAILURE, INTERFACE_NAME + ":" + rpcRequest.getInterfaceName());
        }
        //响应和请求号不同
        if(!rpcRequest.getRequestId().equals(rpcResponse.getRequestId())){
            throw new RpcException(RpcError.RESPONSE_NOT_MATCH, INTERFACE_NAME + ":" + rpcRequest.getInterfaceName());
        }
        //调用失败
        if(rpcResponse.getStatusCode() == null || !rpcResponse.getStatusCode().equals(ResponseCode.SUCCESS.getCode())){
            log.error("调用服务失败，serviceName:{}，RpcResponse:{}", rpcRequest.getInterfaceName(), rpcResponse);
            throw new RpcException(RpcError.SERVICE_INVOCATION_FAILURE, INTERFACE_NAME + ":" + rpcRequest.getInterfaceName());
        }
    }

}
