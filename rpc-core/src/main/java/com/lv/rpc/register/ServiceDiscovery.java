package com.lv.rpc.register;

import java.net.InetSocketAddress;

/**
 * @Project ：Lv-rpc-framework
 * @Author ：Levi_Bee
 * @Date ：2023/7/20 16:47
 * @description ：服务发现接口
 */
public interface ServiceDiscovery {
    /**
     * @description 根据服务名称查找服务端地址
     * @param serviceName 服务名称
     * @return [java.net.InetSocketAddress]
     */
    InetSocketAddress lookupService(String serviceName);
}
