package com.lv.rpc.register;

import java.net.InetSocketAddress;

/**
 * @Project ：Lv-rpc-framework
 * @Author ：Levi_Bee
 * @Date ：2023/7/20 14:41
 * @description ：服务注册接口
 */
public interface ServiceRegistry {

    /**
     * @description 将一个服务注册到注册表
     * @param [ServiceName, inetSocketAddress] 服务名称，提供服务的地址
     * @return [void]
     * @date [2021-03-13 14:44]
     */
    void register(String serviceName, InetSocketAddress inetSocketAddress);

    /**
     * @description 根据服务名查找服务端地址
     * @param [serviceName]
     * @return [java.net.InetSocketAddress] 服务端地址
     * @date [2021-03-13 14:45]
     */
    InetSocketAddress lookupService(String serviceName);

}
