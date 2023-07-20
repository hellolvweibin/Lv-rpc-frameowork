package com.lv.rpc.transport;

import com.lv.rpc.serializer.CommonSerializer;

/**
 * @Project ：Lv-rpc-framework
 * @Author ：Levi_Bee
 * @Date ：2023/7/19 14:19
 * @description ：服务端通用类接口
 */
public interface RpcServer {

    int DEFAULT_SERIALIZER = CommonSerializer.KRYO_SERIALIZER;

    void start();

    /**
     * @description 向Nacos注册服务
     * @param [service, serviceClass]
     * @return [void]
     * @date [2021-03-13 15:56]
     */
    <T> void publishService(T service, Class<T> serviceClass);
}
