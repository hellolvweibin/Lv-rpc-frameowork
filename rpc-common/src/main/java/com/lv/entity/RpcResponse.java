package com.lv.entity;

import com.lv.enumeration.ResponseCode;
import lombok.Data;
import lombok.val;

import java.io.Serializable;

/**
 * @Project ：Lv-rpc-framework
 * @Author ：Levi_Bee
 * @Date ：2023/7/18 15:44
 * @description ：服务端处理完后，向客户端返回的对象
 */
@Data
public class RpcResponse<T> implements Serializable {
    private Integer statusCode;

    private String message;

    private T data;

    /**
     * @description 成功时服务端返回的对象
     */
    public static <T> RpcResponse<T> success(T data){
        RpcResponse<T> response = new RpcResponse<>();
        response.setStatusCode(ResponseCode.SUCCESS.getCode());
        response.setData(data);
        return response;

    }

    /**
     * @description 失败时服务端返回的对象
     */
    public static <T> RpcResponse<T> fail(T data){
        RpcResponse<T> response = new RpcResponse<>();
        response.setStatusCode(ResponseCode.FAIL.getCode());
        response.setData(data);
        return response;

    }

}
